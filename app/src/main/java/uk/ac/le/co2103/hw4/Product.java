package uk.ac.le.co2103.hw4;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName="product", foreignKeys = @ForeignKey(entity=ShoppingList.class, onDelete=ForeignKey.CASCADE, parentColumns="listId", childColumns="fkShoppingList"))
public class Product {
    @PrimaryKey(autoGenerate=true)
    private int productId;

    private String name;
    private int quantity;
    private Unit unit;

    private int fkShoppingList;

    public Product(String name, int quantity, Unit unit, int fkShoppingList){
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.fkShoppingList = fkShoppingList;
    }

    public void setProductId(int id){this.productId = id;}
    public int getProductId(){return this.productId;}

    public void setName(String name){this.name = name;}
    public String getName(){return this.name;}

    public void setQuantity(int quantity){this.quantity = quantity;}
    public int getQuantity(){return this.quantity;}

    public void setUnit(Unit unit){this.unit = unit;}
    public Unit getUnit(){return this.unit;}

    public int getFkShoppingList() {return fkShoppingList;}
    public void setFkShoppingList(int fkShoppingList) {this.fkShoppingList = fkShoppingList;}
}
