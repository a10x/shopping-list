package uk.ac.le.co2103.hw4;

import java.io.Serializable;

public class ProductHolder implements Serializable {

    private String name;
    private int quantity;
    private Unit unit;

    public ProductHolder(String name, int quantity, Unit unit){
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    public String getName(){
        return this.name;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public Unit getUnit(){
        return this.unit;
    }

}
