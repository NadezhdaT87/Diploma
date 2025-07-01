package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.ui.elements.NewsPage;

public class NewsSteps {

    NewsPage newsPage = new NewsPage();

    public void clickButtonNews() {
        Allure.step("Нажать на кнопку Новости в Главном меню");
        newsPage.getNewsButton
                .perform(click());
    }

    public void clickButtonFilterNews() {
        Allure.step("Нажать на кнопку Фильтр на странице Новости");
        newsPage.getButtonFilterNews
                .perform(click());
    }

    public void clickButtonCategoryFilter() {
        Allure.step("Нажать на кнопку Фильтр на странице Фильтровать новость");
        newsPage.getButtonCategoryFilter
                .perform(click());
    }
    public void checkTitleFilterNews() {
        Allure.step("Проверить заголовок Фильтровать новость на странице Новости");
        newsPage.getTitleFilterNews
                .check(matches(allOf(withText("Filter news"), isDisplayed())));
    }

    public void clickButtonDateStart() {
        Allure.step("Указать диапазон дат - начальная дата");
        newsPage.getNewsButtonDateStart
                .perform(click());
    }

    public void clickButtonOkDateStart() {
        Allure.step("Нажать на кнопку ОК");
        newsPage.getNewsButtonOkDateStart
                .perform(click());
    }

    static String date = "01.01.2024";
    public void clickButtonDateEndLessThanStart(){
        Allure.step("В поле конечный диапозон дат выбрать дату меньше начальной");
        newsPage.getNewsButtonDateEnd
                .perform(replaceText(date));
    }





}
