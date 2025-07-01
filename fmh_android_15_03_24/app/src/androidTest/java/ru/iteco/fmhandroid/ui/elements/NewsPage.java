package ru.iteco.fmhandroid.ui.elements;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

public class NewsPage {

    public ViewInteraction getNewsButton;
    public ViewInteraction getButtonFilterNews;
    public ViewInteraction getButtonCategoryFilter;
    public ViewInteraction getTitleFilterNews;

    public ViewInteraction getNewsButtonDateStart;
    public ViewInteraction getNewsButtonOkDateStart;
    public ViewInteraction getNewsButtonDateEnd;
    public ViewInteraction getEditTextCategory;


    public NewsPage() {
        getNewsButton = onView(allOf(withId(android.R.id.title), withText("News")));
        getButtonFilterNews = onView(withId(R.id.filter_news_material_button));
        getButtonCategoryFilter = onView(withId(R.id.filter_button));
        getTitleFilterNews = onView(withId(R.id.filter_news_title_text_view));
        getNewsButtonDateStart = onView(withId(R.id.news_item_publish_date_start_text_input_edit_text));
        getNewsButtonOkDateStart = onView(withId(android.R.id.button1));
        getNewsButtonDateEnd = onView(withId(R.id.news_item_publish_date_end_text_input_edit_text));
        getEditTextCategory = onView(withId(R.id.news_item_category_text_auto_complete_text_view));

    }

}
