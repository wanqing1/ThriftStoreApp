package model;

// Represents a piece of item to be sold in the store, having a name, price (in CAD), condition and owner
public class Item {
    private String name;
    private double price;
    private String condition;
    private String owner;

    // REQUIRES: price > 0
    // EFFECTS: creates an item to be sold
    public Item(String name, double price, String condition, String owner) {
        this.name = name;
        this.price = price;
        this.condition = condition;
        this.owner = owner;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public String getCondition() {
        return this.condition;
    }

    public String getOwner() {
        return  this.owner;
    }

}
