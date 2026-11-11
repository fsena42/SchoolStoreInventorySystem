# School Store Inventory Management System

## Requirements
- Java Development Kit (JDK) installed
- Any Java editor/IDE (VS Code is fine)

## Files
- `src/Item.java`: represents one inventory item.
- `src/StoreInventory.java`: stores and manages `Item` objects using an array.
- `src/InventoryManagementGUI.java`: Java Swing interface and program entry point.

## Run in VS Code / terminal
Open a terminal in the `SchoolStoreInventorySystem` folder and run:

```bash
javac -d out src/*.java
java -cp out InventoryManagementGUI
```

If your terminal does not support `src/*.java`, please compile the three files by listing their paths.

## Features
- Adds stock items in Cleaning, Food, and Scholastics categories.
- Search by exact item name (case-insensitive).
- View all items.
- Restock and issue selected stock.
- Rejects invalid quantities and prevent issuing more than available.
- Calculate total inventory value in UGX.

.
