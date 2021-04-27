package uk.ac.le.co2103.hw4;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "shopping_list")
public class ShoppingList {

    @PrimaryKey(autoGenerate=true)
    private int listId;
    private String name;

    @ColumnInfo(typeAffinity=ColumnInfo.BLOB)
    private byte[] image;

    @Ignore
    public ShoppingList(){
        this("NULL",  null);
    }

    public ShoppingList(String name, byte[] image){
        this.name = name;
        this.image = image;
    }

    public void setListId(int listId){this.listId = listId;}
    public int getListId(){return this.listId;}

    public void setName(String name){this.name = name;}
    public String getName(){return this.name;}

    public void setImage(byte[] image){this.image = image;}
    public byte[] getImage(){return image;}
}
