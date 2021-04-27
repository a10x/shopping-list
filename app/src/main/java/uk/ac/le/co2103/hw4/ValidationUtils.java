package uk.ac.le.co2103.hw4;

public class ValidationUtils {

    public static boolean ValidateShoppingList(ShoppingList list){
        if(list == null) return false;

        return list.getName() != null && !list.getName().trim().isEmpty();
    }

    public static boolean ValidateShoppingListName(String name){
        return name != null && !name.trim().isEmpty();
    }

    public static boolean ValidateProductName(String name){
        return name != null && !name.trim().isEmpty();
    }

    public static boolean ValidateProductQuantity(String num){
        return num != null && !num.trim().isEmpty() && !(Integer.parseInt(num) <= 0);
    }

}
