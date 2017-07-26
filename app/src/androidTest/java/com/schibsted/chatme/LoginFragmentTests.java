package com.schibsted.chatme;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentManager;

import com.schibsted.chatme.data.cache.DiskCache;
import com.schibsted.chatme.presentation.app.ChatmeApplication;
import com.schibsted.chatme.presentation.ui.activities.LoginActivity;
import com.schibsted.chatme.presentation.ui.fragments.LoginFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import rx.Observable;
import rx.schedulers.TestScheduler;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Mockito.when;

/**
 * Created by diego.galico
 */

@RunWith(AndroidJUnit4.class)
public class LoginFragmentTests {
    @Rule
    public final ActivityTestRule<LoginActivity> main = new ActivityTestRule<>(LoginActivity.class, false, false);

    private TestScheduler testScheduler;
    private DiskCache diskCache;

    @Before
    public void setup() throws Throwable {
        // Set up application
        ChatmeApplication app = (ChatmeApplication) getTargetContext().getApplicationContext();
        MockApplicationComponent component = (MockApplicationComponent) app.getApplicationComponent();
        diskCache = component.getDiskCache();
        testScheduler = component.getTestScheduler();

        // Launch main activity
        main.launchActivity(LoginActivity.getStartIntent(getTargetContext(), false));
    }

    @Test
    public void testScreenDisplaysCorrectly() throws Throwable {
        when(diskCache.isUsernameValid("Diego")).thenReturn(Observable.just(true));

        setupFragment();

        // Check screen
        onView(withId(R.id.input_layout_username)).check(matches(isDisplayed()));
        onView(withId(R.id.button_login)).check(matches(isDisplayed()));
    }

    private void setupFragment() {
        FragmentManager fragmentManager = main.getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, new LoginFragment()).commitAllowingStateLoss();

        // Wait for the fragment to be committed
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.waitForIdleSync();
    }
}