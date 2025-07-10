package ru.iteco.fmhandroid.ui.tests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ru.iteco.fmhandroid.ui.data.DataHelper.waitDisplayed;
import static ru.iteco.fmhandroid.ui.data.DataHelper.withIndex;
import static ru.iteco.fmhandroid.ui.steps.AuthorizationSteps.getLogin;
import static ru.iteco.fmhandroid.ui.steps.AuthorizationSteps.getPassword;
import static ru.iteco.fmhandroid.ui.steps.NewsControlPanelSteps.getCategoryAdvertisement;
import static ru.iteco.fmhandroid.ui.steps.NewsControlPanelSteps.getCategoryBirthday;
import static ru.iteco.fmhandroid.ui.steps.NewsControlPanelSteps.getCategoryGratitude;
import static ru.iteco.fmhandroid.ui.steps.NewsControlPanelSteps.getCategoryNeedHelp;
import static ru.iteco.fmhandroid.ui.steps.NewsControlPanelSteps.getCustomCategory;
import static ru.iteco.fmhandroid.ui.steps.NewsControlPanelSteps.getCustomCategoryDescription;
import static ru.iteco.fmhandroid.ui.steps.NewsControlPanelSteps.getCustomCategoryTitle;
import static ru.iteco.fmhandroid.ui.steps.NewsControlPanelSteps.getDescriptionAdvertisement;
import static ru.iteco.fmhandroid.ui.steps.NewsControlPanelSteps.getDescriptionBirthdayEdit;
import static ru.iteco.fmhandroid.ui.steps.NewsControlPanelSteps.getDescriptionGratitude;
import static ru.iteco.fmhandroid.ui.steps.NewsControlPanelSteps.getDescriptionGratitudeDonations;
import static ru.iteco.fmhandroid.ui.steps.NewsControlPanelSteps.getDescriptionNeedHelp;
import static ru.iteco.fmhandroid.ui.steps.NewsControlPanelSteps.getTitleAdvertisement;
import static ru.iteco.fmhandroid.ui.steps.NewsControlPanelSteps.getTitleBirthdayEdit;
import static ru.iteco.fmhandroid.ui.steps.NewsControlPanelSteps.getTitleGratitude;
import static ru.iteco.fmhandroid.ui.steps.NewsControlPanelSteps.getTitleNeedHelp;

import android.view.View;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Story;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.steps.AuthorizationSteps;
import ru.iteco.fmhandroid.ui.steps.MainSteps;
import ru.iteco.fmhandroid.ui.steps.NewsControlPanelSteps;
import ru.iteco.fmhandroid.ui.steps.NewsSteps;

@LargeTest
@RunWith(AndroidJUnit4.class)
@Epic("Тест-кейсы для проведения функционального тестирования \"Панели управления\" (Control panel) новостей мобильного приложения \"Мобильный хоспис\".")
public class NewsControlPanelTest {
    // UI Text Constants
    private static final String NEWS_TEXT = "News";
    private static final String SAVING_FAILED_MESSAGE = "Saving failed. Try again later.";
    private static final String FILL_EMPTY_FIELDS_MESSAGE = "Fill empty fields";
    private static final String NOT_ACTIVE_STATUS = "Not active";
    private static final String NOT_ACTIVE_LABEL = "NOT ACTIVE";

    // Test Data Constants
    private static final String MEETING_TEXT = "Собрание";
    private static final String RELATIVES_VISIT_TEXT = "Посещение родных";
    private static final String ANNIVERSARY_TEXT = "Юбилей";
    private static final String HELP_TEXT = "За помощь";
    private static final String FUTURE_DATE = "01.01.2026";

    // Timeout Constants
    private static final int DEFAULT_TIMEOUT = 5000;

    // View ID Constants
    private static final int NEWS_ITEM_DESCRIPTION_ID = R.id.news_item_description_text_view;
    private static final int NEWS_ITEM_PUBLICATION_DATE_ID = R.id.news_item_publication_date_text_view;
    private static final int NEWS_ITEM_PUBLISHED_ID = R.id.news_item_published_text_view;
    private static final int NEWS_ITEM_TITLE_ID = R.id.news_item_title_text_view;
    private static final int SWITCHER_ID = R.id.switcher;

    AuthorizationSteps authorizationSteps = new AuthorizationSteps();
    MainSteps mainSteps = new MainSteps();
    NewsSteps newsSteps = new NewsSteps();
    NewsControlPanelSteps newsControlPanelSteps = new NewsControlPanelSteps();

    private View decorView;

    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Before
    public void setUp() {
        try {
            mainSteps.mainScreenLoad();
        } catch (Exception e) {
            authorizationSteps.fillLoginField(getLogin());
            authorizationSteps.fillPasswordField(getPassword());
            authorizationSteps.clickButtonSignIn();
            mainSteps.mainScreenLoad();
        }

        activityScenarioRule.getScenario().onActivity(activity -> decorView = activity.getWindow().getDecorView());
    }

    @Test
    @Story("TC - 32")
    @Description("Переход в форму \"Создание новостей\" на странице \"Панель управления\" (Позитивный).")
    public void ShowAddNewsInControlPanel() {
        onView(isRoot()).perform(waitDisplayed(mainSteps.getMainMenuButton(), DEFAULT_TIMEOUT));
        mainSteps.clickButtonMainMenu();
        newsSteps.clickButtonNews();
        newsControlPanelSteps.clickButtonControlPanel();
        newsControlPanelSteps.clickAddNews();
        onView(withText(NEWS_TEXT)).check(matches(isDisplayed()));
        pressBack();
    }

    @Test
    @Story("TC - 33")
    @Description("Создание новости в форме \"Создание новости\" с категорией \"Объявление\" на странице \"Панель управления\" (Позитивный).")
    public void creationNewsInControlPaneAdvertisement() {
        onView(isRoot()).perform(waitDisplayed(mainSteps.getMainMenuButton(), DEFAULT_TIMEOUT));
        mainSteps.clickButtonMainMenu();
        newsSteps.clickButtonNews();
        newsControlPanelSteps.clickButtonControlPanel();
        newsControlPanelSteps.clickAddNews();
        newsControlPanelSteps.fillInNewsCategoryField(getCategoryAdvertisement());
        newsControlPanelSteps.fillTitleCreatingNews(getTitleAdvertisement());
        newsControlPanelSteps.clickButtonDateCreatingNews();
        newsControlPanelSteps.clickButtonOkDateCreatingNews();
        newsControlPanelSteps.clickButtonTimeCreatingNews();
        newsControlPanelSteps.clickButtonOkTimeCreatingNews();
        newsControlPanelSteps.fillDescriptionCreatingNews(getDescriptionAdvertisement());
        newsControlPanelSteps.clickButtonSaveCreatingNews();
        newsControlPanelSteps.clickButtonToExpandNews();
        onView(withIndex(withId(NEWS_ITEM_DESCRIPTION_ID), 0)).check(matches(withText(MEETING_TEXT)));
        newsControlPanelSteps.clickButtonToDeleteNews();
        newsControlPanelSteps.clickButtonToOkDeleteNews();
    }

    @Test
    @Story("TC - 34")
    @Description("Ввод в поле \"Категория\" собственного названия категории в форме \"Создание новости\" странице \"Панель управления\" (Негативный).")
    public void customCategoryName() {
        onView(isRoot()).perform(waitDisplayed(mainSteps.getMainMenuButton(), DEFAULT_TIMEOUT));
        mainSteps.clickButtonMainMenu();
        newsSteps.clickButtonNews();
        newsControlPanelSteps.clickButtonControlPanel();
        newsControlPanelSteps.clickAddNews();
        newsControlPanelSteps.fillInNewsCategoryField(getCustomCategory());
        newsControlPanelSteps.fillTitleCreatingNews(getCustomCategoryTitle());
        newsControlPanelSteps.clickButtonDateCreatingNews();
        newsControlPanelSteps.clickButtonOkDateCreatingNews();
        newsControlPanelSteps.fillDescriptionCreatingNews(getCustomCategoryDescription());
        newsControlPanelSteps.clickButtonTimeCreatingNews();
        newsControlPanelSteps.clickButtonOkTimeCreatingNews();
        newsControlPanelSteps.clickButtonSaveCreatingNews();
        onView(withText(SAVING_FAILED_MESSAGE))
                .inRoot(withDecorView(Matchers.not(decorView)))
                .check(matches(isDisplayed()));
        pressBack();
    }

    @Test
    @Story("TC - 36")
    @Description("TC - 36 - Ручной ввод времени в поле \"Время\", при создании новости, в форме \"Создание новости\", на странице \"Панель управления\" (Позитивный).")
    public void manualInputTime() {
        onView(isRoot()).perform(waitDisplayed(mainSteps.getMainMenuButton(), DEFAULT_TIMEOUT));
        mainSteps.clickButtonMainMenu();
        newsSteps.clickButtonNews();
        newsControlPanelSteps.clickButtonControlPanel();
        newsControlPanelSteps.clickAddNews();
        newsControlPanelSteps.fillInNewsCategoryField(getCategoryNeedHelp());
        newsControlPanelSteps.fillTitleCreatingNews(getTitleNeedHelp());
        newsControlPanelSteps.clickButtonDateCreatingNews();
        newsControlPanelSteps.clickButtonOkDateCreatingNews();
        newsControlPanelSteps.clickButtonTimeCreatingNews();
        newsControlPanelSteps.manualInputTime();
        newsControlPanelSteps.clickButtonOkTimeCreatingNews();
        newsControlPanelSteps.fillDescriptionCreatingNews(getDescriptionNeedHelp());
        newsControlPanelSteps.clickButtonSaveCreatingNews();
        newsControlPanelSteps.clickButtonToExpandNews();
        onView(withIndex(withId(NEWS_ITEM_DESCRIPTION_ID), 0)).check(matches(withText(RELATIVES_VISIT_TEXT)));
        newsControlPanelSteps.clickButtonToDeleteNews();
        newsControlPanelSteps.clickButtonToOkDeleteNews();
    }

    @Test
    @Story("TC - 25")
    @Description("Создание новости без заполнения полей в форме \"Создание новости\" на странице \"Панель управления\" (Негативный).")
    public void fieldsEmptyCreationNewsInControlPanel() {
        onView(isRoot()).perform(waitDisplayed(mainSteps.getMainMenuButton(), DEFAULT_TIMEOUT));
        mainSteps.clickButtonMainMenu();
        newsSteps.clickButtonNews();
        newsControlPanelSteps.clickButtonControlPanel();
        newsControlPanelSteps.clickAddNews();
        newsControlPanelSteps.clickButtonSaveCreatingNews();
        onView(withText(FILL_EMPTY_FIELDS_MESSAGE))
                .inRoot(withDecorView(Matchers.not(decorView)))
                .check(matches(isDisplayed()));
        pressBack();
    }

    @Test
    @Story("TC - 38")
    @Description("Поле \"Дата публикации\" состоит из даты будущего года, при создании новости, на странице \"Панель управления\" (Позитивный)")
    public void fieldDateConsistsOfNextYearDate() {
        onView(isRoot()).perform(waitDisplayed(mainSteps.getMainMenuButton(), DEFAULT_TIMEOUT));
        mainSteps.clickButtonMainMenu();
        newsSteps.clickButtonNews();
        newsControlPanelSteps.clickButtonControlPanel();
        newsControlPanelSteps.clickAddNews();
        newsControlPanelSteps.fillInNewsCategoryField(getCategoryGratitude());
        newsControlPanelSteps.fillTitleCreatingNews(getTitleGratitude());
        newsControlPanelSteps.clickButtonDateCreatingNextDate();
        newsControlPanelSteps.clickButtonTimeCreatingNews();
        newsControlPanelSteps.clickButtonOkTimeCreatingNews();
        newsControlPanelSteps.fillDescriptionCreatingNews(getDescriptionGratitude());
        newsControlPanelSteps.clickButtonSaveCreatingNews();
        onView(withIndex(withId(NEWS_ITEM_PUBLICATION_DATE_ID), 0)).check(matches(withText(FUTURE_DATE)));
        newsControlPanelSteps.clickButtonToDeleteNews();
        newsControlPanelSteps.clickButtonToOkDeleteNews();
    }

    @Test
    @Story("TC - 40")
    @Description("Переход в форму \"Редактирование новости\" на странице \"Панель управления\" (Позитивный).")
    public void showFormEditingNewsInControlPanel() {
        onView(isRoot()).perform(waitDisplayed(mainSteps.getMainMenuButton(), DEFAULT_TIMEOUT));
        mainSteps.clickButtonMainMenu();
        newsSteps.clickButtonNews();
        newsControlPanelSteps.clickButtonControlPanel();
        newsControlPanelSteps.clickButtonToEditNews();
        onView(withText(NEWS_TEXT)).check(matches(isDisplayed()));
        pressBack();
    }

    @Test
    @Story("TC - 41")
    @Description("Редактирование новости валидными данными на странице \"Панель управления\" (Позитивный).")
    public void editingNewsControlPanel() {
        onView(isRoot()).perform(waitDisplayed(mainSteps.getMainMenuButton(), DEFAULT_TIMEOUT));
        mainSteps.clickButtonMainMenu();
        newsSteps.clickButtonNews();
        newsControlPanelSteps.clickButtonControlPanel();
        newsControlPanelSteps.clickButtonToEditNews();
        newsControlPanelSteps.fillInNewsCategoryField(getCategoryBirthday());
        newsControlPanelSteps.fillTitleCreatingNews(getTitleBirthdayEdit());
        newsControlPanelSteps.fillDescriptionCreatingNews(getDescriptionBirthdayEdit());
        newsControlPanelSteps.clickButtonSaveCreatingNews();
        newsControlPanelSteps.clickButtonToExpandNews();
        onView(withIndex(withId(NEWS_ITEM_DESCRIPTION_ID), 0)).check(matches(withText(ANNIVERSARY_TEXT)));
        pressBack();
    }

    @Test
    @Story("TC - 43")
    @Description("Смена статуса новости, находящаяся в статусе \"АКТИВНА\" на статус \"НЕ АКТИВНА\", на странице \"Панель управления\"  в форме \"Редактирование новости\" (Control panel) мобильного приложения \"Мобильный хоспис\" (Позитивный).")
    public void changingStatusNewsControlPanel() {
        onView(isRoot()).perform(waitDisplayed(mainSteps.getMainMenuButton(), DEFAULT_TIMEOUT));
        mainSteps.clickButtonMainMenu();
        newsSteps.clickButtonNews();
        newsControlPanelSteps.clickButtonControlPanel();
        newsControlPanelSteps.clickButtonToEditNews();
        newsControlPanelSteps.clickButtonToSwitchStatusNews();
        onView(withId(SWITCHER_ID))
                .check(matches(withText(NOT_ACTIVE_STATUS)))
                .check(matches(isDisplayed()));
        newsControlPanelSteps.clickButtonSaveCreatingNews();
        onView(withIndex(withId(NEWS_ITEM_PUBLISHED_ID), 0)).check(matches(withText(NOT_ACTIVE_LABEL)));
        newsControlPanelSteps.clickButtonToDeleteNews();
        newsControlPanelSteps.clickButtonToOkDeleteNews();
    }

    @Test
    @Story("TC - 46")
    @Description("Удаление активной новости на странице \"Панель управления\"  (Позитивный).")
    public void deletingNewsControlPanel() {
        onView(isRoot()).perform(waitDisplayed(mainSteps.getMainMenuButton(), DEFAULT_TIMEOUT));
        mainSteps.clickButtonMainMenu();
        newsSteps.clickButtonNews();
        newsControlPanelSteps.clickButtonControlPanel();
        newsControlPanelSteps.clickAddNews();
        newsControlPanelSteps.fillInNewsCategoryField(getCategoryGratitude());
        newsControlPanelSteps.fillTitleCreatingNews(getTitleGratitude());
        newsControlPanelSteps.clickButtonDateCreatingNextDate();
        newsControlPanelSteps.clickButtonTimeCreatingNews();
        newsControlPanelSteps.clickButtonOkTimeCreatingNews();
        newsControlPanelSteps.fillDescriptionCreatingNews(getDescriptionGratitudeDonations());
        newsControlPanelSteps.clickButtonSaveCreatingNews();
        newsControlPanelSteps.clickButtonToExpandNews();
        onView(withIndex(withId(NEWS_ITEM_DESCRIPTION_ID), 0)).check(matches(withText(HELP_TEXT)));
        newsControlPanelSteps.clickButtonToDeleteNews();
        newsControlPanelSteps.clickButtonToOkDeleteNews();
        onView(Matchers.allOf(withId(NEWS_ITEM_TITLE_ID), withText(HELP_TEXT))).check(doesNotExist());
    }
}