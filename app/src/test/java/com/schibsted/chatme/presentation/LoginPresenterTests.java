package com.schibsted.chatme.presentation;

import com.schibsted.chatme.domain.interactors.impl.LoginInteractorImpl;
import com.schibsted.chatme.presentation.presenters.LoginPresenter;
import com.schibsted.chatme.presentation.presenters.impl.LoginPresenterImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import rx.Observable;
import rx.schedulers.TestScheduler;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static rx.Observable.just;

/**
 * Created by diego.galico
 */

@RunWith(RobolectricTestRunner.class)
public class LoginPresenterTests {

    @Mock
    LoginInteractorImpl interactor;

    @Mock
    LoginPresenter.LoginView view;

    private LoginPresenterImpl presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new LoginPresenterImpl(interactor);
    }

    @Test
    public void testMessageActivityCalledCorrectly() {
        TestScheduler testScheduler = new TestScheduler();
        Observable<Boolean> result = just(true).subscribeOn(testScheduler);
        when(interactor.validateUsername("Diego")).thenReturn(result);

        presenter.attachView(view);
        presenter.setUsername("Diego");
        presenter.validateUsername();

        testScheduler.triggerActions();

        verify(view, times(1)).startMessageActivity();
    }
}
