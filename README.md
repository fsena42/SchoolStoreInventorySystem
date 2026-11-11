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

If your terminal does not support `src/*.java`, compile the three files by listing their paths explicitly.

## Features
- Add stock items in Cleaning, Food, and Scholastics categories.
- Search by exact item name (case-insensitive).
- View all items.
- Restock and issue selected stock.
- Reject invalid quantities and prevent issuing more than available.
- Calculate total inventory value in UGX.

The application stores data in memory. Closing the program resets added changes; a database or file-saving feature is not required by the lecturer's brief.
