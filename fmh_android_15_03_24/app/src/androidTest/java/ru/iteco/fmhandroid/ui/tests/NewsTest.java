package ru.iteco.fmhandroid.ui.tests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ru.iteco.fmhandroid.ui.data.DataHelper.waitDisplayed;
import static ru.iteco.fmhandroid.ui.data.DataHelper.withIndex;
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
import ru.iteco.fmhandroid.ui.steps.AuthorizationSteps;
import ru.iteco.fmhandroid.ui.steps.MainSteps;
import ru.iteco.fmhandroid.ui.steps.NewsControlPanelSteps;
import ru.iteco.fmhandroid.ui.steps.NewsSteps;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
@Epic("Тест-кейсы для проведения функционального тестирования вкладки \"Новости\" (News) мобильного приложения \"Мобильный хоспис\".")
public class NewsTest {

    // Константы для текстовых элементов интерфейса
    private static final String NEWS_TITLE = "News";
    private static final String FILTER_NEWS_TITLE = "Filter news";
    private static final String CONTROL_PANEL_TITLE = "Control panel";

    // Константы для ID элементов
    private static final int ALL_NEWS_CARDS_BLOCK_ID = R.id.all_news_cards_block_constraint_layout;

    // Константы для таймаутов
    private static final int DEFAULT_TIMEOUT = 5000;

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
        mainSteps.clickButtonMainMenu();
        newsSteps.clickButtonNews();
    }

    @Test
    @Story("TC - 18")
    @Description("Переход на страницу \"Фильтрация новостей\" на странице \"Новости\" (Позитивный)")
    public void ShowFilterNewsInNewPage() {
        onView(isRoot()).perform(waitDisplayed(newsSteps.getButtonFilterNews(), 5000));
        newsSteps.clickButtonFilterNews();
        onView(withText(FILTER_NEWS_TITLE)).check(matches(isDisplayed()));
        pressBack();
    }

    @Test
    @Story("TC - 21")
    @Description("Фильтрация новостей с некорректным диапазон (конец раньше начала) даты на странице \"Новости\" (Негативный).")
    public void filterNewsWithDateEndLessThanStart() {
        onView(isRoot()).perform(waitDisplayed(newsSteps.getButtonFilterNews(), 5000));
        onView(withText(NEWS_TITLE)).check(matches(isDisplayed()));
        newsSteps.clickButtonFilterNews();
        newsSteps.checkTitleFilterNews();
        newsSteps.clickButtonDateStart();
        newsSteps.clickButtonOkDateStart();
        newsSteps.clickButtonDateEndLessThanStart();
        newsSteps.clickButtonCategoryFilter();
        onView(withId(ALL_NEWS_CARDS_BLOCK_ID)).check(matches(isDisplayed()));
    }

    @Test
    @Story("TC - 23")
    @Description("Фильтрация новостей без заполнения полей на странице \"Новости\" (Негативный).")
    public void filterNewsWithoutFillingFields() {
        onView(isRoot()).perform(waitDisplayed(newsSteps.getButtonFilterNews(), 5000));
        newsSteps.clickButtonFilterNews();
        newsSteps.checkTitleFilterNews();
        newsSteps.clickButtonCategoryFilter();
        onView(withIndex(withId(R.id.view_news_item_image_view), 0)).check(matches(isDisplayed()));
    }

    @Test
    @Story("TC - 25")
    @Description("Переход на страницу \"Панель управления\" на странице \"Новости\" (Позитивный).")
    public void ShowControlPanelPage() {
        onView(isRoot()).perform(waitDisplayed(R.id.edit_news_material_button, 5000));
        newsControlPanelSteps.clickButtonControlPanel();
        onView(withText(CONTROL_PANEL_TITLE)).check(matches(isDisplayed()));
        pressBack();
    }
}