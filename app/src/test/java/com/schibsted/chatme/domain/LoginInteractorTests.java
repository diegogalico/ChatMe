package com.schibsted.chatme.domain;

import com.schibsted.chatme.data.cache.DiskCache;
import com.schibsted.chatme.data.net.RestData;
import com.schibsted.chatme.domain.executors.SchedulerProvider;
import com.schibsted.chatme.domain.interactors.impl.LoginInteractorImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import rx.Observable;
import rx.observers.TestSubscriber;

import static edu.emory.mathcs.backport.java.util.Collections.singletonList;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by diego.galico
 */

@RunWith(RobolectricTestRunner.class)
public class LoginInteractorTests {
    @Mock
    RestData restData;

    @Mock
    DiskCache cache;

    private LoginInteractorImpl interactor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        SchedulerProvider schedulerProvider = new SchedulerProvider() {
            @Override public <T> Observable.Transformer<T, T> applySchedulers() {
                return observable -> observable;
            }
        };

        when(cache.saveMessageWrapper(any())).thenReturn(Observable.just(null));

        interactor = new LoginInteractorImpl(schedulerProvider, cache);
    }

    @Test
    public void testHitsMemoryCache() {
        Boolean expectedResult = true;

        when(cache.isUsernameValid("Diego")).thenReturn(Observable.just(true));

        TestSubscriber<Boolean> testSubscriberFirst = new TestSubscriber<>();
        interactor.validateUsername("Diego").subscribe(testSubscriberFirst);
        testSubscriberFirst.assertNoErrors();
        testSubscriberFirst.assertReceivedOnNext(singletonList(expectedResult));
    }

}