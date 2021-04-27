package uk.ac.le.co2103.hw4;

import android.view.View;
import android.widget.TextView;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4ClassRunner.class)
public class ShoppingListTest {

    @Rule
    public ActivityScenarioRule<MainActivity> rule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testAddNewList() {
        String listName = "Birthday Party";

        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.edit_list_name)).perform(ViewActions.typeText(listName), ViewActions.closeSoftKeyboard());
        onView(withText("Create")).perform(ViewActions.click());
        onView(withText(listName)).check(matches(ViewMatchers.isDisplayed()));

        ShoppingListDatabase.getInstance(InstrumentationRegistry.getInstrumentation().getTargetContext()).listDao().delete((int) ShoppingListRepository.LAST_INSERTED);
    }

    @Test
    public void testDeleteList() {
        String listName = "Birthday Party";

        //create a list
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.edit_list_name)).perform(ViewActions.typeText(listName), ViewActions.closeSoftKeyboard());
        onView(withText("Create")).perform(ViewActions.click());

        //delete the list
        onView(withText(listName)).perform(ViewActions.longClick());
        onView(withText("Delete")).perform(ViewActions.click());

        try{
            onView(withText(listName));
        }catch (NoMatchingViewException ignored){}
    }

    @Test
    public void testAddProduct() {
        String listName = "Birthday Party";
        String productName = "Cake";
        String quantity = "1";
        String selectUnit = "Kg";

        //create a list
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.edit_list_name)).perform(ViewActions.typeText(listName), ViewActions.closeSoftKeyboard());
        onView(withText("Create")).perform(ViewActions.click());

        //add product
        onView(withText(listName)).perform(ViewActions.click());
        onView(withId(R.id.fabAddProduct)).perform(ViewActions.click());
        onView(withId(R.id.editTextName)).perform(ViewActions.typeText(productName), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editTextQuantity)).perform(ViewActions.typeText(quantity), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.spinner)).perform(ViewActions.click());
        onData(allOf(is(instanceOf(String.class)), is(selectUnit))).perform(click());
        onView(withText("Add")).perform(ViewActions.click());
        onView(withText(productName)).check(matches(ViewMatchers.isDisplayed()));

        ShoppingListDatabase.getInstance(InstrumentationRegistry.getInstrumentation().getTargetContext()).listDao().delete((int) ShoppingListRepository.LAST_INSERTED);
    }

    @Test
    public void testAddDuplicateProduct() {
        String listName = "Birthday Party";
        String productName = "Cake";
        String quantity = "1";
        String selectUnit = "Kg";
        String productExistsMessage = "Product already exists";

        //create a list
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.edit_list_name)).perform(ViewActions.typeText(listName), ViewActions.closeSoftKeyboard());
        onView(withText("Create")).perform(ViewActions.click());

        //add product
        onView(withText(listName)).perform(ViewActions.click());
        onView(withId(R.id.fabAddProduct)).perform(ViewActions.click());
        onView(withId(R.id.editTextName)).perform(ViewActions.typeText(productName), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editTextQuantity)).perform(ViewActions.typeText(quantity), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.spinner)).perform(ViewActions.click());
        onData(allOf(is(instanceOf(String.class)), is(selectUnit))).perform(click());
        onView(withText("Add")).perform(ViewActions.click());

        //add product
        onView(withId(R.id.fabAddProduct)).perform(ViewActions.click());
        onView(withId(R.id.editTextName)).perform(ViewActions.typeText(productName), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editTextQuantity)).perform(ViewActions.typeText(quantity), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.spinner)).perform(ViewActions.click());
        onData(allOf(is(instanceOf(String.class)), is(selectUnit))).perform(click());
        onView(withText("Add")).perform(ViewActions.click());

        //TODO: test for toast message
        //onView(allOf(withText(productExistsMessage))).check(matches(ViewMatchers.isDisplayed()));

        //deletes the list
        ShoppingListDatabase.getInstance(InstrumentationRegistry.getInstrumentation().getTargetContext()).listDao().delete((int) ShoppingListRepository.LAST_INSERTED);
    }

    @Test
    public void testEditProduct(){
        String listName = "Birthday Party";
        String productName = "Cake";
        String quantity = "1";
        String selectUnit = "Kg";

        //create a list
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.edit_list_name)).perform(ViewActions.typeText(listName), ViewActions.closeSoftKeyboard());
        onView(withText("Create")).perform(ViewActions.click());

        //add product
        onView(withText(listName)).perform(ViewActions.click());
        onView(withId(R.id.fabAddProduct)).perform(ViewActions.click());
        onView(withId(R.id.editTextName)).perform(ViewActions.typeText(productName), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editTextQuantity)).perform(ViewActions.typeText(quantity), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.spinner)).perform(ViewActions.click());
        onData(allOf(is(instanceOf(String.class)), is(selectUnit))).perform(click());
        onView(withText("Add")).perform(ViewActions.click());

        //edit product
        onView(withText(productName)).perform(ViewActions.click());
        onView(withText("Edit")).perform(ViewActions.click());
        onView(withText("+")).perform(ViewActions.click());
        onView(withText("+")).perform(ViewActions.click());
        onView(withText("Save")).perform(ViewActions.click());

        onView(withText(startsWith("3"))).check(matches(ViewMatchers.isDisplayed()));

        ShoppingListDatabase.getInstance(InstrumentationRegistry.getInstrumentation().getTargetContext()).listDao().delete((int) ShoppingListRepository.LAST_INSERTED); }

    @Test
    public void testDeleteProduct() {
        String listName = "Birthday Party";
        String productName = "Cake";
        String quantity = "1";
        String selectUnit = "Kg";

        //create a list
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.edit_list_name)).perform(ViewActions.typeText(listName), ViewActions.closeSoftKeyboard());
        onView(withText("Create")).perform(ViewActions.click());

        //add product
        onView(withText(listName)).perform(ViewActions.click());
        onView(withId(R.id.fabAddProduct)).perform(ViewActions.click());
        onView(withId(R.id.editTextName)).perform(ViewActions.typeText(productName), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editTextQuantity)).perform(ViewActions.typeText(quantity), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.spinner)).perform(ViewActions.click());
        onData(allOf(is(instanceOf(String.class)), is(selectUnit))).perform(ViewActions.click());
        onView(withText("Add")).perform(ViewActions.click());

        onView(withText(productName)).perform(ViewActions.click());
        onView(withText("Delete")).perform(ViewActions.click());

        try{
            onView(withText(productName));
        }catch (NoMatchingViewException ignored){}

        ShoppingListDatabase.getInstance(InstrumentationRegistry.getInstrumentation().getTargetContext()).listDao().delete((int) ShoppingListRepository.LAST_INSERTED);
    }
}