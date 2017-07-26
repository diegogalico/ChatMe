package com.schibsted.chatme.data;

import android.content.SharedPreferences;

import com.schibsted.chatme.data.cache.DiskCache;
import com.schibsted.chatme.data.exceptions.InvalidLoginException;
import com.schibsted.chatme.presentation.app.ChatmeApplication;
import com.schibsted.chatme.presentation.app.Constants;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.observers.TestSubscriber;

import static com.schibsted.chatme.data.cache.DiskCache.KEY_DATA;
import static com.schibsted.chatme.data.cache.DiskCache.KEY_FIRST_TIME;
import static edu.emory.mathcs.backport.java.util.Collections.singletonList;
import static java.lang.Boolean.TRUE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by diego.galico
 */

@RunWith(RobolectricTestRunner.class)
public class DiskCacheTests {
    @Mock
    SharedPreferences prefs;

    @Mock
    ChatmeApplication app;

    private DiskCache cache;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        cache = new DiskCache(app, prefs);
    }

    @Test
    public void testEmptyMessageWrapper(){
        when(prefs.getString(KEY_DATA, null)).thenReturn(null);

        TestSubscriber<MessageWrapperEntity> testSubscriber = TestSubscriber.create();
        cache.getMessageWrapper().subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertReceivedOnNext(singletonList(null));
    }

    @Test
    public void testNonEmptyMessageWrapper(){
        when(prefs.getString(KEY_DATA, null)).thenReturn("{\"chats\":[{\"username\":\"Valerie\",\"content\":\"Exactly\"}]}");

        TestSubscriber<MessageWrapperEntity> testSubscriber = TestSubscriber.create();
        cache.getMessageWrapper().subscribe(testSubscriber);
        testSubscriber.assertNoErrors();

        MessageWrapperEntity expectedResult = new MessageWrapperEntity();
        List<MessageEntity> listMessageEntity = new ArrayList<>();
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setUsername("Valerie");
        messageEntity.setContent("Exactly");
        listMessageEntity.add(messageEntity);
        expectedResult.setListMessages(listMessageEntity);

        List<MessageWrapperEntity> actualResult = testSubscriber.getOnNextEvents();

        Assert.assertEquals(expectedResult, actualResult.get(0));

        messageEntity.setTime("15:30h");
        Assert.assertNotEquals(expectedResult, actualResult.get(0));
    }

    @Test
    public void testSaveMessageWrapper(){
        MessageWrapperEntity expectedResult = new MessageWrapperEntity();
        List<MessageEntity> listMessageEntity = new ArrayList<>();
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setUsername("Valerie");
        messageEntity.setContent("Exactly");
        listMessageEntity.add(messageEntity);
        expectedResult.setListMessages(listMessageEntity);

        SharedPreferences.Editor editorMock = mock(SharedPreferences.Editor.class);
        when(prefs.edit()).thenReturn(editorMock);

        class IncrementCountAnswer implements Answer {
            private int count = 0;
            @Override public Object answer(InvocationOnMock invocation) throws Throwable {
                count++;
                return TRUE;
            }
        }

        IncrementCountAnswer incrementCount = new IncrementCountAnswer();
        doAnswer(incrementCount).when(editorMock).commit();
        doAnswer(incrementCount).when(editorMock).apply();

        when(editorMock.putString(any(String.class), any(String.class))).thenReturn(editorMock);

        TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
        cache.saveMessageWrapper(expectedResult).subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertReceivedOnNext(Collections.singletonList(TRUE));

        verify(editorMock).putString(KEY_DATA, "{\"chats\":[{\"username\":\"Valerie\",\"content\":\"Exactly\",\"isMyMessage\":false}]}");

        assertEquals(1, incrementCount.count);
    }

    @Test
    public void testLoggedUser(){
        when(prefs.getString(anyString(), anyString())).thenReturn("Diego");
        assertEquals("Diego", cache.getLoggedUser());
    }

    @Test
    public void testSaveLoggedUser(){
        String expectedResult = "Diego";

        SharedPreferences.Editor editorMock = mock(SharedPreferences.Editor.class);
        when(prefs.edit()).thenReturn(editorMock);

        class IncrementCountAnswer implements Answer {
            private int count = 0;
            @Override public Object answer(InvocationOnMock invocation) throws Throwable {
                count++;
                return TRUE;
            }
        }

        IncrementCountAnswer incrementCount = new IncrementCountAnswer();
        doAnswer(incrementCount).when(editorMock).commit();
        doAnswer(incrementCount).when(editorMock).apply();

        when(editorMock.putString(any(String.class), any(String.class))).thenReturn(editorMock);

        cache.saveLoggedUser(expectedResult);

        verify(editorMock).putString(KEY_FIRST_TIME, "Diego");

        assertEquals(1, incrementCount.count);
    }

    @Test
    public void testRemoveLoggedUser(){
        SharedPreferences.Editor editorMock = mock(SharedPreferences.Editor.class);
        when(prefs.edit()).thenReturn(editorMock);
        when(editorMock.remove(any(String.class))).thenReturn(editorMock);

        class IncrementCountAnswer implements Answer {
            private int count = 0;
            @Override public Object answer(InvocationOnMock invocation) throws Throwable {
                count++;
                return TRUE;
            }
        }
        IncrementCountAnswer incrementCount = new IncrementCountAnswer();
        doAnswer(incrementCount).when(editorMock).commit();
        doAnswer(incrementCount).when(editorMock).apply();

        cache.removeLoggedUser();

        verify(editorMock).remove(KEY_FIRST_TIME);

        assertEquals(1, incrementCount.count);
    }


    @Test
    public void testIsLoggedIn(){
        when(prefs.getString(anyString(), anyString())).thenReturn("Diego");
        assertEquals(true, cache.isLoggedIn());

        when(prefs.getString(anyString(), anyString())).thenReturn(null);
        assertEquals(false, cache.isLoggedIn());
    }

    @Test
    public void testUsernameIsValid(){
        SharedPreferences.Editor editorMock = mock(SharedPreferences.Editor.class);
        when(prefs.edit()).thenReturn(editorMock);
        TestSubscriber<Boolean> testSubscriber = TestSubscriber.create();
        cache.isUsernameValid("Diego").subscribe(testSubscriber);
        testSubscriber.assertNoErrors();

        Boolean expectedResult = true;
        List<Boolean> actualResult = testSubscriber.getOnNextEvents();

        Assert.assertEquals(expectedResult, actualResult.get(0));
    }

    @Test
    public void testUsernameIsNotValid(){
        TestSubscriber<Boolean> testSubscriber = TestSubscriber.create();
        cache.isUsernameValid("Oliva").subscribe(testSubscriber);
        testSubscriber.assertError(InvalidLoginException.class);

        cache.isUsernameValid(Constants.EMTPY_STRING).subscribe(testSubscriber);
        testSubscriber.assertError(InvalidLoginException.class);
    }

}
