package ru.iteco.fmhandroid.ui.tests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static ru.iteco.fmhandroid.ui.data.DataHelper.waitDisplayed;
import static ru.iteco.fmhandroid.ui.steps.AuthorizationSteps.getLogin;
import static ru.iteco.fmhandroid.ui.steps.AuthorizationSteps.getPassword;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Story;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.steps.AboutSteps;
import ru.iteco.fmhandroid.ui.steps.AuthorizationSteps;
import ru.iteco.fmhandroid.ui.steps.MainSteps;
import ru.iteco.fmhandroid.ui.steps.NewsSteps;
import ru.iteco.fmhandroid.ui.steps.ThematicQuoteSteps;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
@Epic("Тест-кейсы для проведения функционального тестирования вкладки \"Главная страница\" (Main) мобильного приложения \"Мобильный хоспис\".")
public class MainTest {

    // Константы для текстовых элементов
    private static final String NEWS_TITLE = "News";
    private static final String VERSION_TEXT = "Version:";
    private static final String ALL_NEWS_TEXT = "ALL NEWS";
    private static final String LOVE_IS_ALL_TEXT = "Love is all";
    private static final String AUTHORIZATION_TITLE = "Authorization";

    // Константы для таймаутов
    private static final int DEFAULT_TIMEOUT = 5000;

    AuthorizationSteps authorizationSteps = new AuthorizationSteps();
    MainSteps mainSteps = new MainSteps();
    NewsSteps newsSteps = new NewsSteps();
    AboutSteps aboutSteps = new AboutSteps();
    ThematicQuoteSteps thematicQuoteSteps = new ThematicQuoteSteps();

    @Rule
    public ActivityTestRule<AppActivity> activityTestRule =
            new ActivityTestRule<>(AppActivity.class);

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
    }

    @Test
    @Story("TC - 9")
    @Description("Переход на страницу \"Новости\" через \"главное меню\" (Позитивный).")
    public void ShowNewsPage() {
        onView(isRoot()).perform(waitDisplayed(mainSteps.getMainMenuButton(), DEFAULT_TIMEOUT));
        mainSteps.clickButtonMainMenu();
        newsSteps.clickButtonNews();
        onView(allOf(withText(NEWS_TITLE),
                withParent(withParent(withId(R.id.container_list_news_include))))).check(matches(isDisplayed()));
    }

    @Test
    @Story("TC - 10")
    @Description("Переход на страницу \"О приложении\" (Позитивный).")
    public void ShowAboutPage() {
        onView(isRoot()).perform(waitDisplayed(mainSteps.getMainMenuButton(), DEFAULT_TIMEOUT));
        mainSteps.clickButtonMainMenu();
        newsSteps.clickButtonNews();
        onView(withText(NEWS_TITLE)).check(matches(isDisplayed()));
        mainSteps.clickButtonMainMenu();
        aboutSteps.clickButtonAboutMainMenu();
        onView(withText(VERSION_TEXT)).check(matches(isDisplayed()));
    }

    @Test
    @Story("TC - 11")
    @Description("Работа кнопки \"стрелка вниз\" в разделе \"Новости\" на \"главной странице\" (Позитивный).")
    public void extendNews() {
        onView(isRoot()).perform(waitDisplayed(mainSteps.getButtonToExpandNews(), DEFAULT_TIMEOUT));
        mainSteps.clickButtonToExpandNews();
        mainSteps.clickButtonToExpandNews();
        onView(withId(R.id.all_news_text_view)).check(matches(withText(ALL_NEWS_TEXT)));
    }

    @Test
    @Story("TC - 13")
    @Description("Работа текстовой ссылки - кнопки \"Все новости\", на \"Главной странице\" для успешного перехода на страницу \"Новости\" (Позитивный).")
    public void transferToAllNewsThroughMainPage() {
        onView(isRoot()).perform(waitDisplayed(mainSteps.getMainMenuButton(), DEFAULT_TIMEOUT));
        mainSteps.showButtonAllNews();
        mainSteps.clickButtonAllNews();
        onView(allOf(withText(NEWS_TITLE),
                withParent(withParent(withId(R.id.container_list_news_include))))).check(matches(isDisplayed()));
    }

    @Test
    @Story("TC - 14")
    @Description("Переход на страницу \"Главное - жить любя\" (Позитивный).")
    public void ShowLoveIsAllThroughMainPage() {
        onView(isRoot()).perform(waitDisplayed(thematicQuoteSteps.getMissionImageButton(), DEFAULT_TIMEOUT));
        thematicQuoteSteps.clickButtonThematicQuote();
        thematicQuoteSteps.checkTitleThematicQuote();
        onView(withText(LOVE_IS_ALL_TEXT)).check(matches(isDisplayed()));
    }

    @Test
    @Story("TC - 15")
    @Description("Выход из профиля (Позитивный).")
    public void LogoutTest() {
        onView(isRoot()).perform(waitDisplayed(mainSteps.getButtonToExpandNews(), DEFAULT_TIMEOUT));
        authorizationSteps.clickButtonExit();
        authorizationSteps.clickButtonLogOut();
        authorizationSteps.loadAuthorizationPage();
        onView(withText(AUTHORIZATION_TITLE)).check(matches(isDisplayed()));
    }
}