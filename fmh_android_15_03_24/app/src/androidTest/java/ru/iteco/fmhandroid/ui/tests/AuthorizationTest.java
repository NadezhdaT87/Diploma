package ru.iteco.fmhandroid.ui.tests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ru.iteco.fmhandroid.ui.data.DataHelper.waitDisplayed;
import static ru.iteco.fmhandroid.ui.steps.AuthorizationSteps.getDifferentRegexLogin;
import static ru.iteco.fmhandroid.ui.steps.AuthorizationSteps.getLogin;
import static ru.iteco.fmhandroid.ui.steps.AuthorizationSteps.getLoginWithSpecialCharacters;
import static ru.iteco.fmhandroid.ui.steps.AuthorizationSteps.getPassword;
import static ru.iteco.fmhandroid.ui.steps.AuthorizationSteps.getSpaceLogin;
import static ru.iteco.fmhandroid.ui.steps.AuthorizationSteps.getSpacePassword;
import static ru.iteco.fmhandroid.ui.steps.AuthorizationSteps.getUnregisteredLogin;
import static ru.iteco.fmhandroid.ui.steps.AuthorizationSteps.getUnregisteredPassword;

import android.view.View;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.hamcrest.Matchers;
import org.junit.After;
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

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
@Epic("Тест-кейсы для проведения функционального тестирования вкладки \"Авторизация\" мобильного приложения \"Мобильный хоспис\".")
public class AuthorizationTest {

    // Константы для сообщений
    private static final String LOGIN_PASSWORD_EMPTY_ERROR = "Login and password cannot be empty";
    private static final String SOMETHING_WRONG_ERROR = "Something went wrong. Try again later.";

    AuthorizationSteps authorizationSteps = new AuthorizationSteps();
    MainSteps mainSteps = new MainSteps();

    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);
    private View decorView;

    @Before
    public void setUp() {
        try {
            authorizationSteps.loadAuthorizationPage();
        } catch (Exception e) {
            authorizationSteps.clickButtonExit();
            authorizationSteps.clickButtonLogOut();
            authorizationSteps.loadAuthorizationPage();
        }
        activityScenarioRule.getScenario().onActivity(activity -> decorView = activity.getWindow().getDecorView());
    }

    @After
    public void tearDown() {
        try {
            authorizationSteps.clickButtonExit();
            authorizationSteps.clickButtonLogOut();
        } catch (Exception ignored) {
        }
    }

    @Test
    @Story("TC - 3")
    @Description("Поле \"Логин\" , \"Пароль\" пустые, при авторизации (Негативный).")
    public void loginPasswordFieldIsEmpty() {
        onView(isRoot()).perform(waitDisplayed(authorizationSteps.getLoginLayout(), 5000));
        authorizationSteps.textAuthorization();
        authorizationSteps.fillLoginField("");
        authorizationSteps.fillPasswordField("");
        authorizationSteps.clickButtonSignIn();
        onView(withText(LOGIN_PASSWORD_EMPTY_ERROR))
                .inRoot(withDecorView(Matchers.not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    @Story("TC - 4")
    @Description("Успешная авторизация, ввод валидных данных в поле \"Логин\", \"Пароль\" (Позитивный).")
    public void successfulAuthorization() {
        onView(isRoot()).perform(waitDisplayed(authorizationSteps.getLoginLayout(), 5000));
        authorizationSteps.textAuthorization();
        authorizationSteps.fillLoginField(getLogin());
        authorizationSteps.fillPasswordField(getPassword());
        authorizationSteps.clickButtonSignIn();
        onView(isRoot()).perform(waitDisplayed(R.id.authorization_image_button, 3000));
        mainSteps.showTitleNewsOnMain();
        authorizationSteps.clickButtonExit();
        authorizationSteps.clickButtonLogOut();
    }

    @Test
    @Story("TC - 5")
    @Description("Поле \"Логин\", \"Пароль\" заполнено кириллицей, при авторизации (Негативный).")
    public void loginPasswordFieldWithCyrillic() {
        onView(isRoot()).perform(waitDisplayed(authorizationSteps.getLoginLayout(), 5000));
        authorizationSteps.textAuthorization();
        authorizationSteps.fillLoginField(getUnregisteredLogin());
        authorizationSteps.fillPasswordField(getUnregisteredPassword());
        authorizationSteps.clickButtonSignIn();
        onView(withText(SOMETHING_WRONG_ERROR))
                .inRoot(withDecorView(Matchers.not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    @Story("TC - 6")
    @Description("Поле \"Логин\", \"Пароль\" содержит пробел, при авторизации (Негативный).")
    public void passwordFieldWithSpace() {
        onView(isRoot()).perform(waitDisplayed(authorizationSteps.getLoginLayout(), 5000));
        authorizationSteps.textAuthorization();
        authorizationSteps.fillLoginField(getSpaceLogin());
        authorizationSteps.fillPasswordField(getSpacePassword());
        authorizationSteps.clickButtonSignIn();
        onView(withText(SOMETHING_WRONG_ERROR))
                .inRoot(withDecorView(Matchers.not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    @Story("TC - 7")
    @Description("Поле \"Логин\" состоит из букв разного регистра, при авторизации (Негативный).")
    public void loginFieldLettersOfDifferentCase() {
        onView(isRoot()).perform(waitDisplayed(authorizationSteps.getLoginLayout(), 5000));
        authorizationSteps.textAuthorization();
        authorizationSteps.fillLoginField(getDifferentRegexLogin());
        authorizationSteps.fillPasswordField(getPassword());
        authorizationSteps.clickButtonSignIn();
        onView(withText(SOMETHING_WRONG_ERROR))
                .inRoot(withDecorView(Matchers.not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    @Story("TC - 8")
    @Description("Поле \"Логин\" состоит из спецсимволов, при авторизации (Негативный).")
    public void loginFieldWithSpecialCharacters() {
        onView(isRoot()).perform(waitDisplayed(authorizationSteps.getLoginLayout(), 5000));
        authorizationSteps.textAuthorization();
        authorizationSteps.fillLoginField(getLoginWithSpecialCharacters());
        authorizationSteps.fillPasswordField(getPassword());
        authorizationSteps.clickButtonSignIn();
        onView(withText(SOMETHING_WRONG_ERROR))
                .inRoot(withDecorView(Matchers.not(decorView)))
                .check(matches(isDisplayed()));
    }
}
