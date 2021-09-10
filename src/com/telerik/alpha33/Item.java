package com.telerik.alpha33;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Item implements Comparable<Item> {
    public String NAME_EXIST = "Error: Item {%s} already exists";

    private String name;
    private Double price;
    private String type;
    private List<String> allNames = new ArrayList<>();

    public Item(String name, Double price, String type) {
        setName(name);
        setPrice(price);
        this.type = type;
    }
    public void setName(String name) {
        if (allNames.contains(name)) {
            throw new IllegalArgumentException(String.format(NAME_EXIST,getName()));
        }
        this.name = name;
    }


    public void setPrice(Double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannon be negative");
        } else {
            this.price = price;
        }
    }
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Double getPrice() {
        return price;
    }
    @Override
    public int compareTo(Item o) {
        Item item = new Item(this.name,this.price,this.type);
        return item.compareTo(o);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(name, item.name) && Objects.equals(price, item.price) && Objects.equals(type, item.type) && Objects.equals(allNames, item.allNames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, type, allNames);
    }
    @Override
    public String toString() {
        return String.format("Ok: %s(%.2f), ",getName(), getPrice());
    }
}

