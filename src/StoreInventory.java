public class StoreInventory {
    private Item[] items;
    private int itemCount;
    private int nextItemId;

    public StoreInventory() {
        items = new Item[100];
        itemCount = 0;
        nextItemId = 1;
    }

    public boolean addItem(String name, String category, int quantity, double unitPrice) {
        if (name == null || name.trim().isEmpty()
                || category == null || category.trim().isEmpty()
                || quantity < 0 || unitPrice < 0 || itemCount >= items.length) {
            return false;
        }

        Item item = new Item(nextItemId, name.trim(), category, quantity, unitPrice);
        items[itemCount] = item;
        itemCount++;
        nextItemId++;
        return true;
    }

    public Item findItemByName(String name) {
        if (name == null) {
            return null;
        }

        int i = 0;
        while (i < itemCount) {
            if (items[i].getName().equalsIgnoreCase(name.trim())) {
                return items[i];
            }
            i++;
        }
        return null;
    }

    public Item findItemById(int itemId) {
        for (int i = 0; i < itemCount; i++) {
            if (items[i].getItemId() == itemId) {
                return items[i];
            }
        }
        return null;
    }

    public Item getItemAt(int index) {
        if (index >= 0 && index < itemCount) {
            return items[index];
        }
        return null;
    }

    public int getItemCount() {
        return itemCount;
    }

    public double getTotalInventoryValue() {
        double total = 0;
        for (int i = 0; i < itemCount; i++) {
            total = total + items[i].getTotalValue();
        }
        return total;
    }
}
