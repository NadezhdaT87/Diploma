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
//@RunWith(AllureAndroidJUnit4.class)

@Epic("Тест-кейсы для проведения функционального тестирования \"Панели управления\" (Control panel) новостей мобильного приложения \"Мобильный хоспис\".")
public class NewsControlPanelTest {
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

    // ТС - 32 - Переход в форму "Создание новостей" на странице "Панель управления" (Позитивный).
    @Test
    @Story("TC - 32")
    @Description("Переход в форму \"Создание новостей\" на странице \"Панель управления\" (Позитивный).")
    public void ShowAddNewsInControlPanel() {
        onView(isRoot()).perform(waitDisplayed(mainSteps.getMainMenuButton(), 5000));
        mainSteps.clickButtonMainMenu();
        newsSteps.clickButtonNews();
        newsControlPanelSteps.clickButtonControlPanel();
        newsControlPanelSteps.clickAddNews();
        onView(withText("News")).check(matches(isDisplayed()));
    }


    // ТС - 33 - Создание новости в форме "Создание новости" с категорией "Объявление" на странице "Панель управления" (Позитивный).
    @Test
    @Story("TC - 33")
    @Description("Создание новости в форме \"Создание новости\" с категорией \"Объявление\" на странице \"Панель управления\" (Позитивный).")
    public void creationNewsInControlPaneAdvertisement() {
        onView(isRoot()).perform(waitDisplayed(mainSteps.getMainMenuButton(), 5000));
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
        onView(withIndex(withId(R.id.news_item_description_text_view), 0)).check(matches(withText("Собрание")));
        newsControlPanelSteps.clickButtonToDeleteNews();
        newsControlPanelSteps.clickButtonToOkDeleteNews();
    }

    //  ТC - 34 - Ввод в поле "Категория" собственного названия категории в форме "Создание новости" странице "Панель управления" (Негативный).
    @Test
    @Story("TC - 34")
    @Description("Ввод в поле \"Категория\" собственного названия категории в форме \"Создание новости\" странице \"Панель управления\" (Негативный).")
    public void customCategoryName() {
        onView(isRoot()).perform(waitDisplayed(mainSteps.getMainMenuButton(), 5000));
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
        onView(withText("Saving failed. Try again later."))
                .inRoot(withDecorView(Matchers.not(decorView)))
                .check(matches(isDisplayed()));
        pressBack();
    }

    //  TC - 36 - Ручной ввод времени в поле "Время", при создании новости, в форме "Создание новости", на странице "Панель управления" (Позитивный).
    @Test
    @Story("TC - 36")
    @Description("TC - 36 - Ручной ввод времени в поле \"Время\", при создании новости, в форме \"Создание новости\", на странице \"Панель управления\" (Позитивный).")
    public void manualInputTime() {
        onView(isRoot()).perform(waitDisplayed(mainSteps.getMainMenuButton(), 5000));
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
        onView(withIndex(withId(R.id.news_item_description_text_view), 0)).check(matches(withText("Посещение родных")));
        newsControlPanelSteps.clickButtonToDeleteNews();
        newsControlPanelSteps.clickButtonToOkDeleteNews();
    }

    //  ТC - 37 -Создание новости без заполнения полей в форме "Создание новости" на странице "Панель управления" (Негативный).
    @Test
    @Story("TC - 25")
    @Description("Создание новости без заполнения полей в форме \"Создание новости\" на странице \"Панель управления\" (Негативный).")
    public void fieldsEmptyCreationNewsInControlPanel() {
        onView(isRoot()).perform(waitDisplayed(mainSteps.getMainMenuButton(), 5000));
        mainSteps.clickButtonMainMenu();
        newsSteps.clickButtonNews();
        newsControlPanelSteps.clickButtonControlPanel();
        newsControlPanelSteps.clickAddNews();
        newsControlPanelSteps.clickButtonSaveCreatingNews();
        onView(withText("Fill empty fields"))
                .inRoot(withDecorView(Matchers.not(decorView)))
                .check(matches(isDisplayed()));
        pressBack();
    }

    //  ТC - 38 - Поле "Дата публикации" состоит из даты будущего года, при создании новости, на странице "Панель управления" (Позитивный).
    @Test
    @Story("TC - 38")
    @Description("Поле \"Дата публикации\" состоит из даты будущего года, при создании новости, на странице \"Панель управления\" (Позитивный)")
    public void fieldDateConsistsOfNextYearDate() {
        onView(isRoot()).perform(waitDisplayed(mainSteps.getMainMenuButton(), 5000));
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
        onView(withIndex(withId(R.id.news_item_publication_date_text_view), 0)).check(matches(withText("01.01.2026")));
        newsControlPanelSteps.clickButtonToDeleteNews();
        newsControlPanelSteps.clickButtonToOkDeleteNews();
    }

//  TC - 40 - Переход в форму "Редактирование новости" на странице "Панель управления" (Позитивный).
@Test
@Story("TC - 40")
@Description("Переход в форму \"Редактирование новости\" на странице \"Панель управления\" (Позитивный).")
public void editingNewsControlPanel1() {
    onView(isRoot()).perform(waitDisplayed(mainSteps.getMainMenuButton(), 5000));
    mainSteps.clickButtonMainMenu();
    newsSteps.clickButtonNews();
    newsControlPanelSteps.clickButtonControlPanel();
    newsControlPanelSteps.clickButtonToEditNews();
    onView(withText("News")).check(matches(isDisplayed()));
    pressBack();
}

    //  TC - 41 - Редактирование новости валидными данными на странице "Панель управления" (Позитивный).
    @Test
    @Story("TC - 41")
    @Description("Редактирование новости валидными данными на странице \"Панель управления\" (Позитивный).")
    public void editingNewsControlPanel() {
        onView(isRoot()).perform(waitDisplayed(mainSteps.getMainMenuButton(), 5000));
        mainSteps.clickButtonMainMenu();
        newsSteps.clickButtonNews();
        newsControlPanelSteps.clickButtonControlPanel();
        newsControlPanelSteps.clickButtonToEditNews();
        newsControlPanelSteps.fillInNewsCategoryField(getCategoryBirthday());
        newsControlPanelSteps.fillTitleCreatingNews(getTitleBirthdayEdit());
        newsControlPanelSteps.fillDescriptionCreatingNews(getDescriptionBirthdayEdit());
        newsControlPanelSteps.clickButtonSaveCreatingNews();
        newsControlPanelSteps.clickButtonToExpandNews();
        onView(withIndex(withId(R.id.news_item_description_text_view), 0)).check(matches(withText("Юбилей")));
        pressBack();
    }

    //  TC - 43 - Смена статуса новости, находящаяся в статусе "АКТИВНА" на статус "НЕ АКТИВНА", на странице "Панель управления"  в форме "Редактирование новости" (Позитивный).
    @Test
    @Story("TC - 43")
    @Description("Смена статуса новости, находящаяся в статусе \"АКТИВНА\" на статус \"НЕ АКТИВНА\", на странице \"Панель управления\"  в форме \"Редактирование новости\" (Control panel) мобильного приложения \"Мобильный хоспис\" (Позитивный).")
    public void changingStatusNewsControlPanel() {
        onView(isRoot()).perform(waitDisplayed(mainSteps.getMainMenuButton(), 5000));
        mainSteps.clickButtonMainMenu();
        newsSteps.clickButtonNews();
        newsControlPanelSteps.clickButtonControlPanel();
        newsControlPanelSteps.clickButtonToEditNews();
        newsControlPanelSteps.clickButtonToSwitchStatusNews();
        onView(withId(R.id.switcher))
                .check(matches(withText("Not active")))
                .check(matches(isDisplayed()));
        newsControlPanelSteps.clickButtonSaveCreatingNews();
        onView(withIndex(withId(R.id.news_item_published_text_view), 0)).check(matches(withText("NOT ACTIVE")));
        newsControlPanelSteps.clickButtonToDeleteNews();
        newsControlPanelSteps.clickButtonToOkDeleteNews();
    }

    //  TC - 46 - Удаление активной новости на странице "Панель управления"  (Позитивный).
    @Test
    @Story("TC - 46")
    @Description("Удаление активной новости на странице \"Панель управления\"  (Позитивный).")
    public void deletingNewsControlPanel() {
        onView(isRoot()).perform(waitDisplayed(mainSteps.getMainMenuButton(), 5000));
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
        onView(withIndex(withId(R.id.news_item_description_text_view), 0)).check(matches(withText("За помощь")));
        newsControlPanelSteps.clickButtonToDeleteNews();
        newsControlPanelSteps.clickButtonToOkDeleteNews();
        onView(Matchers.allOf(withId(R.id.news_item_title_text_view), withText("За помощь"))).check(doesNotExist());
    }

}
