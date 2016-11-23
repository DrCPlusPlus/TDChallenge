package com.example.alexis.tdmoneyed;


import java.io.Serializable;

// ListItem objects
public class ListItem implements Serializable {

    private String item_name;
    private Boolean checked_icon;
    private Double amount = 0.00;


    ListItem(String item, Boolean icon, Double dollars){
        this.item_name = item;
        this.checked_icon = icon;
        this.amount = dollars;
    }

    public String getItem_name(){return item_name; }
    public Boolean getChecked_Icon(){return checked_icon; }
    public Double getAmount(){ return amount; }

    public void setItem_name(String type){ this.item_name = type; }
    public void setChecked_icon(Boolean icon){ this.checked_icon = icon; }
    public void setAmount(Double dollars) { this.amount = dollars; }

}