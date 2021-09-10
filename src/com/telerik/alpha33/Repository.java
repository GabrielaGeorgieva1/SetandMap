package com.telerik.alpha33;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Repository {
    public String ADD_SUCCESSFULLY = "Ok: Item {%s} added successfully";
    private final Set<Item> allProducts = new TreeSet<>();

    public String add(String name, Double price, String type) {
        allProducts.add(new Item(name, price, type));
        return String.format(ADD_SUCCESSFULLY,name);
    }

    public void filterByType(String type) {
        String result = allProducts.stream()
                .filter(item -> item.getType().equals(type))
                .limit(10)
                .map(Item::toString)
                .collect(Collectors.joining(", ","OK: ",""));;
        printForEmptyList(result);
    }

    public void filterByPrice(Double minPrice, Double maxPrice) {
        String result = allProducts.stream()
                .filter(item -> item.getPrice() >= minPrice)
                .filter(item -> item.getPrice() <= maxPrice)
                .limit(10)
                .map(Item::toString)
                .collect(Collectors.joining(", ","OK: ",""));
        printForEmptyList(result);
    }

    public void filterByMinPrice(Double minPrice) {
        String result = allProducts.stream()
                .filter(item -> item.getPrice() >= minPrice)
                .limit(10)
                .map(Item::toString)
                .collect(Collectors.joining(", ","OK: ",""));
        printForEmptyList(result);
    }

    public void filterByMaxPrice(Double maxPrice) {
        String result = allProducts.stream()
                .filter(item -> item.getPrice() <= maxPrice)
                .limit(10)
                .map(Item::toString)
                .collect(Collectors.joining(", ","OK: ",""));
        printForEmptyList(result);
    }



    public void printForEmptyList(String elements) {
        if (elements.isEmpty()) {
            System.out.print("Ok:");
            return;
        }
    }
}



