package uk.ac.le.co2103.hw4;

import java.io.Serializable;

public class CreateListHolder implements Serializable {

    private String listName;
    private byte[] imageArray;

    public CreateListHolder(String listName, byte[] imageArray){
        this.listName = listName;
        this.imageArray = imageArray;
    }

    public String getListName(){return this.listName;}
    public byte[] getImageArray(){return this.imageArray;}

}
