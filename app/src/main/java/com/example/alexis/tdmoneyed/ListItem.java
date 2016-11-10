package com.example.alexis.tdmoneyed;


// ListItem objects
public class ListItem {

    String list_item;
    Boolean checked_icon;


    ListItem(String item, Boolean icon){
        this.list_item = item;
        this.checked_icon = icon;
    }

    public String getList_item(){return list_item; }
    public Boolean getChecked_Icon(){return checked_icon; }

    public void setList_Item(String type){ this.list_item = type; }
    public void setChecked_icon(Boolean icon){ this.checked_icon = icon; }

}