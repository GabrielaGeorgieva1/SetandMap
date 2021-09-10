package com.telerik.alpha33;

import java.io.ByteArrayInputStream;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static void testInput() {
        String test = "add CowMilk 1.90 dairy\n" +
                "add BulgarianYogurt 1.90 dairy\n" +
                "add SmartWatch 1111.90 technology\n" +
                "add Candy 0.90 food\n" +
                "add Lemonade 11.90 drinks\n" +
                "add Sweatshirt 121.90 clothes\n" +
                "add Pants 49.90 clothes\n" +
                "add CowMilk 1.90 dairy\n" +
                "add Eggs 2.34 food\n" +
                "add Cheese 5.55 dairy\n" +
                "filter by type clothes\n" +
                "filter by price from 1.00 to 2.00\n" +
                "add FreshOrange 1.99 juice\n" +
                "add Aloe 2.7 juice\n" +
                "filter by price from 1200\n" +
                "add Socks 2.90 clothes\n" +
                "filter by type fruits\n" +
                "add DellXPS13 1700.1234 technology\n" +
                "filter by price from 1200\n" +
                "filter by price from 1.50\n" +
                "filter by price to 2.00\n" +
                "filter by type clothes\n" +
                "end\n";
        System.setIn(new ByteArrayInputStream(test.getBytes()));
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        // testInput();
        Scanner sc = new Scanner(System.in);
        String commandLine =  sc.nextLine();
        Repository repository = new Repository();
        while (!commandLine.equals("end")) {
            String[] input = commandLine.split(" ");
            if (input[0].equals("add")) {
                repository.add(input[1], Double.parseDouble(input[2]), input[3]);
            } else if (input[0].equals("filter")) {
                if (input[2].equals("type")) {
                    repository.filterByType(input[3]);
                } else if (input[3].equals("from") && input.length == 5) {
                    repository.filterByMinPrice(Double.parseDouble(input[4]));
                } else if (input[3].equals("to")){
                    repository.filterByMaxPrice(Double.parseDouble(input[4]));
                } else  {
                    repository.filterByPrice(Double.parseDouble(input[4]), Double.parseDouble(input[6]));
                }
            }
            commandLine = sc.nextLine();
        }

    }

    private static class Repository {
        public String ADD_SUCCESSFULLY = "Ok: Item %s added successfully";
        public String NAME_EXIST = "Error: Item %s already exists";
        public String TYPE_EXIST = "Error: Type %s does not exist";

        private final Set<Item> allProducts = new TreeSet<>();
        private HashSet<String> allNames = new HashSet<>();

        public void add(String name, Double price, String type) {
            if (allNames.contains(name)) {
                System.out.println(String.format(NAME_EXIST, name));
                return;
            }
            allNames.add(name);
            allProducts.add(new Item(name, price, type));
            System.out.println(String.format(ADD_SUCCESSFULLY, name));
        }

        public void filterByType(String type) {
            String result = allProducts.stream()
                    .filter(item -> item.getType().equals(type))
                    .limit(10)
                    .map(Item::toString)
                    .collect(Collectors.joining(", ", "Ok: ", ""));
            if(result.length() == 4){
                System.out.println(String.format(TYPE_EXIST,type));
                return;
            }
            System.out.println(result);
        }

        public void filterByPrice(Double minPrice, Double maxPrice) {
            String result = allProducts.stream()
                    .filter(item -> item.getPrice() >= minPrice)
                    .filter(item -> item.getPrice() <= maxPrice)
                    .limit(10)
                    .map(Item::toString)
                    .collect(Collectors.joining(", ", "Ok: ", ""));
            printForEmptyList(result);
            System.out.println(result);
        }

        public void filterByMinPrice(Double minPrice) {
            String result = allProducts.stream()
                    .filter(item -> item.getPrice() >= minPrice)
                    .limit(10)
                    .map(Item::toString)
                    .collect(Collectors.joining(", ", "Ok: ", ""));
            printForEmptyList(result);
            System.out.println(result);
        }

        public void filterByMaxPrice(Double maxPrice) {
            String result = allProducts.stream()
                    .filter(item -> item.getPrice() <= maxPrice)
                    .limit(10)
                    .map(Item::toString)
                    .collect(Collectors.joining(", ", "Ok: ", ""));
            printForEmptyList(result);
            System.out.println(result);
        }


        public void printForEmptyList(String elements) {
            if (elements.isEmpty()) {
                System.out.print("Ok:");
                return;
            }
        }
    }

    private static class Item implements Comparable<Item> {

        private String name;
        private Double price;
        private String type;

        public Item(String name, Double price, String type) {
            setName(name);
            setPrice(price);
            this.type = type;
        }

        public void setName(String name) {
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
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Item item = (Item) o;
            return Objects.equals(name, item.name) && Objects.equals(price, item.price) && Objects.equals(type, item.type);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, price, type);
        }

        @Override
        public String toString() {
            return String.format("%s(%.02f)", getName(), getPrice());
        }

        @Override
        public int compareTo(Item o) {
            return Comparator.comparing(Item::getPrice)
                    .thenComparing(Item::getName)
                    .thenComparing(Item::getType).compare(this,o);
        }
    }
}