package ru.iteco.fmhandroid.ui.tests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ru.iteco.fmhandroid.ui.data.DataHelper.waitDisplayed;
import static ru.iteco.fmhandroid.ui.data.DataHelper.withIndex;
import static ru.iteco.fmhandroid.ui.steps.AuthorizationSteps.getLogin;
import static ru.iteco.fmhandroid.ui.steps.AuthorizationSteps.getPassword;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

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
import ru.iteco.fmhandroid.ui.steps.ThematicQuoteSteps;

@LargeTest
//@RunWith(AndroidJUnit4.class)
@RunWith(AllureAndroidJUnit4.class)

@Epic("Тест-кейсы для проведения функционального тестирования на странице \"Тематические цитаты\" мобильного приложения \"Мобильный хоспис\".")
public class ThematicQuoteTest {

    AuthorizationSteps authorizationSteps = new AuthorizationSteps();
    ThematicQuoteSteps thematicQuoteSteps = new ThematicQuoteSteps();
    MainSteps mainSteps = new MainSteps();


    @Rule
    public ActivityScenarioRule<AppActivity> activityRule =
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
    }

    //  ТC - 47 - Работа кнопки "стрелка вниз" в посте  на странице "Главное - жить любя" (Love is all) (Позитивный).
    @Test
    @Story("TC - 47")
    @Description("Работа кнопки \"стрелка вниз\" в посте  на странице \"Главное - жить любя\" (Love is all) (Позитивный).")
    public void expandThematicQuote() {
        onView(isRoot()).perform(waitDisplayed(thematicQuoteSteps.getMissionImageButton(), 5000));
        thematicQuoteSteps.clickButtonThematicQuote();
        thematicQuoteSteps.checkTitleThematicQuote();
        thematicQuoteSteps.clickButtonToExpandThematicQuote();
        onView(withIndex(withId(R.id.our_mission_item_description_text_view), 0)).check(matches(isDisplayed()));
    }

}

