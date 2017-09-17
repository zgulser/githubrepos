package com.payconiq.zekigu.githubrepos;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.SearchView;
import android.view.KeyEvent;
import android.view.View;

import com.payconiq.zekigu.githubrepos.ui.views.splash.SplashActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by zekigu on 16.09.2017
 */

@RunWith(AndroidJUnit4.class)
@MediumTest
public class SampleFunctionalUITest {

    @Rule
    public ActivityTestRule<SplashActivity> activityRule = new ActivityTestRule<>(
            SplashActivity.class);

    @Test
    public void testBasicFlow() throws InterruptedException {
        scroll();
        clickItem(3);
        goBack();
        search();
        clickItem(2);
    }

    public void scroll() throws InterruptedException {
        Thread.sleep(5000);
        onView(withId(R.id.reposRecyclerView)).perform(RecyclerViewActions.scrollToPosition(10));
        Thread.sleep(1000);
        onView(withId(R.id.reposRecyclerView)).perform(RecyclerViewActions.scrollToPosition(2));
        Thread.sleep(1000);
    }

    public void clickItem(int position) throws InterruptedException {
        onView(withId(R.id.reposRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(position, ViewActions.click()));
        Thread.sleep(2000);
    }

    public void goBack() throws InterruptedException {
        pressBack();
        Thread.sleep(2000);
    }

    public void search() throws InterruptedException {
        onView(withId(R.id.action_search)).perform(click());
        Thread.sleep(2000);
        onView(withId(android.support.design.R.id.search_src_text)).perform(typeText("action"), pressKey(KeyEvent.KEYCODE_ENTER));
        Thread.sleep(2000);
    }
}
