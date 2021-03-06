import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RestaurantTest {
    Restaurant restaurant;
    LocalTime openingTime,closingTime;
    //REFACTOR ALL THE REPEATED LINES OF CODE
    @BeforeEach
    public void initialize(){
        openingTime = LocalTime.parse("10:30:00");
        closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
         Restaurant restaurantSpyObj = Mockito.spy(new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime));
         Mockito.doReturn(LocalTime.parse("11:30:00")).when(restaurantSpyObj).getCurrentTime();
         assertTrue(restaurantSpyObj.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        Restaurant restaurantSpyObj = Mockito.spy(new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime));
        Mockito.doReturn(LocalTime.parse("09:30:00")).when(restaurantSpyObj).getCurrentTime();
        assertFalse(restaurantSpyObj.isRestaurantOpen());
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    // Newly Added Method for TDD -- New Feature of Getting the Total Order value of Selected Items
    @Test
    public void menu_select_items_should_return_total_order_value_of_items(){
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        try {
            assertEquals(388,restaurant.getTotalOrderValueOfSelectedItems("Sweet corn soup","Vegetable lasagne"));
        } catch (itemNotFoundException e) {
            assertFalse(true);
        }
    }

    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        restaurant.displayDetails();

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}