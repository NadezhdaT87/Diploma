package ru.iteco.fmhandroid.ui.util;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.doubleClick;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static ru.iteco.fmhandroid.ui.data.DataHelper.withIndex;

import android.os.SystemClock;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.AppActivity;

@LargeTest
@RunWith(AndroidJUnit4.class)
//@RunWith(AllureAndroidJUnit4.class)

public class NewsTest extends TestCase {

    @Rule
    public IntentsTestRule intentsTestRule =
            new IntentsTestRule(AppActivity.class);


    ViewInteraction loginField = onView(allOf(withHint("Login"), withParent(withParent(withId(R.id.login_text_input_layout)))));
    ViewInteraction passwordField = onView(allOf(withHint("Password"), withParent(withParent(withId(R.id.password_text_input_layout)))));
    ViewInteraction buttonEnter = onView(withId(R.id.enter_button));

    ViewInteraction buttonExit = onView(
            withId(R.id.authorization_image_button));

    ViewInteraction buttonLogOut = onView(
            allOf(withId(android.R.id.title), withText("Log out")));
    ViewInteraction buttonMainMenu = onView(
            allOf(withId(R.id.main_menu_image_button), withContentDescription("Main menu")));

    ViewInteraction buttonNews = onView(
            allOf(withId(android.R.id.title), withText("News")));

    ViewInteraction buttonControlPanel = onView(
            withId(R.id.edit_news_material_button));
    ViewInteraction buttonAddNews = onView(
            allOf(withId(R.id.add_news_image_view), withContentDescription("Add news button")));
    ViewInteraction categoryText = onView(
            withId(R.id.news_item_category_text_auto_complete_text_view));
    ViewInteraction fieldCreatingTitle = onView(
            withId(R.id.news_item_title_text_input_edit_text));
    ViewInteraction buttonDateCreatingNews = onView(withId(R.id.news_item_publish_date_text_input_edit_text));

    ViewInteraction buttonTimeCreatingNews = onView(
            withId(R.id.news_item_publish_time_text_input_edit_text));
    ViewInteraction buttonOkTimeCreatingNews = onView(
            allOf(withId(android.R.id.button1), withText("OK")));
    ViewInteraction fieldDescription = onView(withId(R.id.news_item_description_text_input_edit_text));
    ViewInteraction buttonSaveCreatingNews = onView(
            allOf(withId(R.id.save_button), withText("Save"), withContentDescription("Save")));

    ViewInteraction buttonClickNews = onView(
            withIndex(withId(R.id.news_item_material_card_view), 0));
    ViewInteraction buttonDeleteNews = onView(
            withIndex(withId(R.id.delete_news_item_image_view), 0));
    ViewInteraction buttonOkDeleteNews = onView(
            withId(android.R.id.button1));
    ViewInteraction buttonEditNews = onView(
            withIndex(withId(R.id.edit_news_item_image_view), 0));
    ViewInteraction buttonSwitcher = onView(
            withId(R.id.switcher));
    ViewInteraction buttonFilterNewsPanel = onView(
            withId(R.id.filter_news_material_button));
    ViewInteraction fieldCategoryFilter = onView(
            withId(R.id.news_item_category_text_auto_complete_text_view));
    ViewInteraction buttonFilter = onView(
            withId(R.id.filter_button));
    ViewInteraction inActiveCheckBox = onView(
            allOf(withId(R.id.filter_news_inactive_material_check_box), withText("Not active")));
    ViewInteraction activeCheckBox = onView(
            withId(R.id.filter_news_active_material_check_box));


    @Before
    public void login() {
        SystemClock.sleep(5000);
        loginField.perform(replaceText("login2"), closeSoftKeyboard());
        passwordField.perform(replaceText("password2"), closeSoftKeyboard());
        buttonEnter.perform(click());
    }

    @After
    public void logOut() {
        SystemClock.sleep(4000);
        buttonExit.perform(click());
        SystemClock.sleep(4000);
        buttonLogOut.perform(click());
    }

    String nextYear = "01.01.2026";

    //  TC - 28 - Фильтрация новостей по категории, дате и с включенными чекбоксами на странице "Панель управления" (Позитивный).
    @Test
    public void testFilteringNewsAdvertisementControlPanel() {
        SystemClock.sleep(10000);
        buttonMainMenu.check(matches(isDisplayed()));
        buttonMainMenu.perform(click());
        buttonNews.perform(click());
        buttonControlPanel.perform(click());
        buttonAddNews.perform(click());
        SystemClock.sleep(1000);
        categoryText.perform(click(), clearText(), replaceText("Объявление"), closeSoftKeyboard());
        SystemClock.sleep(1000);
        fieldCreatingTitle.perform(click(), clearText(), replaceText("Концерт"), closeSoftKeyboard());
        buttonDateCreatingNews.perform(replaceText(nextYear));
        buttonTimeCreatingNews.perform(click());
        SystemClock.sleep(1000);
        buttonOkTimeCreatingNews.perform(click());
        fieldDescription.perform(replaceText("Золотое Кольцо"), closeSoftKeyboard());
        buttonSaveCreatingNews.perform(scrollTo(), click());
        SystemClock.sleep(1000);
        buttonClickNews.perform(doubleClick());
        SystemClock.sleep(1000);
        onView(withIndex(withId(R.id.news_item_description_text_view), 0)).check(matches(withText("Золотое Кольцо")));
        SystemClock.sleep(5000);
        SystemClock.sleep(10000);
        buttonAddNews.perform(click());
        SystemClock.sleep(1000);
        categoryText.perform(click(), clearText(), replaceText("Праздник"), closeSoftKeyboard());
        SystemClock.sleep(1000);
        fieldCreatingTitle.perform(click(), clearText(), replaceText("Новый год"), closeSoftKeyboard());
        buttonDateCreatingNews.perform(replaceText("01.01.2026"));
        buttonTimeCreatingNews.perform(click());
        SystemClock.sleep(1000);
        buttonOkTimeCreatingNews.perform(click());
        fieldDescription.perform(replaceText("Поздравляем"), closeSoftKeyboard());
        buttonSaveCreatingNews.perform(scrollTo(), click());
        onView(
                withIndex(withId(R.id.news_item_material_card_view), 0)).perform(doubleClick());
        onView(withIndex(withId(R.id.news_item_description_text_view), 0)).check(matches(withText("Поздравляем")));
        SystemClock.sleep(5000);
        buttonFilterNewsPanel.perform(click());
        fieldCategoryFilter.perform(click(), clearText(), replaceText("Объявление"), closeSoftKeyboard());
        buttonFilter.perform(click());
        onView(withIndex(withId(R.id.news_item_description_text_view), 0)).check(matches(withText("Золотое Кольцо")));
    }

    //  ТС - 29 - Фильтрация по не активными новостями на странице "Панель управления" (Позитивный).
    @Test
    public void testFilteringNewsStatusNotActiveControlPanel() {
        SystemClock.sleep(5000);
        buttonMainMenu.check(matches(isDisplayed()));
        buttonMainMenu.perform(click());
        buttonNews.perform(click());
        buttonControlPanel.perform(click());
        SystemClock.sleep(1000);
        buttonEditNews.perform(click());
        buttonSwitcher.perform(click());
        onView(withId(R.id.switcher))
                .check(matches(withText("Not active")))
                .check(matches(isDisplayed()));
        buttonSaveCreatingNews.perform(scrollTo(), click());
        onView(withIndex(withId(R.id.news_item_published_text_view), 0)).check(matches(withText("NOT ACTIVE")));
        buttonFilterNewsPanel.perform(click());
        activeCheckBox.perform(click());
        buttonFilter.perform(click());
        onView(withIndex(withId(R.id.news_item_published_text_view), 0)).check(matches(withText("NOT ACTIVE")));
        buttonDeleteNews.perform(click());
        buttonOkDeleteNews.perform(click());
    }

    //  ТС - 30 - Фильтрация по активными новостями на странице "Панель управления" (Позитивный).
    @Test
    public void testFilteringNewsStatusActiveControlPanel() {
        SystemClock.sleep(10000);
        buttonMainMenu.check(matches(isDisplayed()));
        buttonMainMenu.perform(click());
        buttonNews.perform(click());
        buttonControlPanel.perform(click());
        SystemClock.sleep(1000);
        buttonFilterNewsPanel.perform(click());
        inActiveCheckBox.perform(click());
        buttonFilter.perform(click());
        onView(withIndex(withId(R.id.news_item_published_text_view), 0)).check(matches(withText("ACTIVE")));

    }

}


