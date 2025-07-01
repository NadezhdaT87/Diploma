package ru.iteco.fmhandroid.ui.tests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
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

@Epic("Тест-кейсы для проведения функционального тестирования вкладки \"Новости\" (News) мобильного приложения \"Мобильный хоспис\".")
public class NewsTest {
    AuthorizationSteps authorizationSteps = new AuthorizationSteps();
    MainSteps mainSteps = new MainSteps();
    NewsSteps newsSteps = new NewsSteps();
    NewsControlPanelSteps newsControlPanelSteps = new NewsControlPanelSteps();
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

    //  Тест-кейсы для проведения функционального тестирования страницы "Новости" мобильного приложения "Мобильный хоспис".

    // ТС - 18 Переход на страницу "Фильтрация новостей" на странице "Новости" (Позитивный).
    @Test
    @Story("TC - 18")
    @Description("Переход на страницу \"Фильтрация новостей\" на странице \"Новости\" (Позитивный)")
    public void ShowFilterNewsInNewPage() {
        onView(isRoot()).perform(waitDisplayed(mainSteps.getMainMenuButton(), 5000));
        mainSteps.clickButtonMainMenu();
        newsSteps.clickButtonNews();
        onView(withText("News")).check(matches(isDisplayed()));
        newsSteps.clickButtonFilterNews();
        newsSteps.checkTitleFilterNews();
        onView(withText("Filter news")).check(matches(isDisplayed()));
    }

    // ТС - 21 Фильтрация новостей с некорректным диапазон (конец раньше начала) даты на странице "Новости" (Негативный)..
    @Test
    @Story("TC - 21")
    @Description(" Фильтрация новостей с некорректным диапазон (конец раньше начала) даты на странице \"Новости\" (Негативный).")
    public void filterNewsWithDateEndLessThanStart() {
        onView(isRoot()).perform(waitDisplayed(mainSteps.getMainMenuButton(), 5000));
        mainSteps.clickButtonMainMenu();
        newsSteps.clickButtonNews();
        newsSteps.clickButtonFilterNews();
        newsSteps.checkTitleFilterNews();
        newsSteps.clickButtonDateStart();
        newsSteps.clickButtonOkDateStart();
        newsSteps.clickButtonDateEndLessThanStart();
        newsSteps.clickButtonCategoryFilter();
        onView(withId(R.id.all_news_cards_block_constraint_layout)).check(matches(isDisplayed()));
    }

    // ТС - 23 Фильтрация новостей без заполнения полей  на странице "Новости" (Негативный).
    @Test
    @Story("TC - 23")
    @Description("Фильтрация новостей без заполнения полей на странице \"Новости\" (Негативный).")
    public void filterNewsWithoutFillingFields() {
        onView(isRoot()).perform(waitDisplayed(mainSteps.getMainMenuButton(), 5000));
        mainSteps.clickButtonMainMenu();
        newsSteps.clickButtonNews();
        newsSteps.clickButtonFilterNews();
        newsSteps.checkTitleFilterNews();
        newsSteps.clickButtonCategoryFilter();
        onView(withId(R.id.all_news_cards_block_constraint_layout)).check(matches(isDisplayed()));
    }
    //   TC - 25 - Переход на страницу "Панель управления" на странице "Новости" (Позитивный).
    @Test
    @Story("TC - 25")
    @Description(" TC - 25 - Переход на страницу \"Панель управления\" на странице \"Новости\" (Позитивный).")
    public void ShowControlPanelPage() {
        onView(isRoot()).perform(waitDisplayed(mainSteps.getMainMenuButton(), 5000));
        mainSteps.clickButtonMainMenu();
        newsSteps.clickButtonNews();
        newsControlPanelSteps.clickButtonControlPanel();
        onView(withText("Control panel")).check(matches(isDisplayed()));
    }
    
}
