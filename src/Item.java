public class Item {
    private int itemId;
    private String name;
    private String category;
    private int quantity;
    private double unitPrice;

    public Item(int itemId, String name, String category, int quantity, double unitPrice) {
        this.itemId = itemId;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
    }

    public void setCategory(String category) {
        if (category != null && !category.trim().isEmpty()) {
            this.category = category;
        }
    }

    public void setUnitPrice(double unitPrice) {
        if (unitPrice >= 0) {
            this.unitPrice = unitPrice;
        }
    }

    public void restock(int amount) {
        if (amount > 0) {
            quantity = quantity + amount;
        }
    }

    public boolean issueStock(int amount) {
        if (amount > 0 && amount <= quantity) {
            quantity = quantity - amount;
            return true;
        } else {
            return false;
        }
    }

    public double getTotalValue() {
        return quantity * unitPrice;
    }
}
