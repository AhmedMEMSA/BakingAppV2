package ahmedmemsa.nanodegree.baking;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ahmedmemsa.nanodegree.baking.Activities.RecipeDetailsActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by asmaa on 5/30/2017.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeIngredientsTest {
    @Rule
    public ActivityTestRule<RecipeDetailsActivity> activityTestRule
            = new ActivityTestRule<>(RecipeDetailsActivity.class);

    @Test
    public void clickIngredientsButton_ShowDialog() {
        onView(withId(R.id.cv_ingredients)).perform(click());
        onView(withId(R.id.ingredients_parent)).check(matches(isDisplayed()));

    }
}
