//SEKIRANDA EMMA MICHAEL 25/U/15631/PS
//KINAJOLE MUHAMMAD MAGGWA 26/U/03885/PSA
//WAFULA JORAM 23/U/26217
//ALUGA DESREE OJOK 25/U/0339

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class InventoryManagementGUI extends JFrame {
    private StoreInventory inventory;
    private JTextField nameField;
    private JComboBox<String> categoryBox;
    private JTextField quantityField;
    private JTextField priceField;
    private JTextField searchField;
    private JTable itemTable;
    private DefaultTableModel tableModel;
    private JLabel statusLabel;
    private JLabel totalValueLabel;

    public InventoryManagementGUI() {
        inventory = new StoreInventory();

        setTitle("School Store Inventory Management System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        add(createInputPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);

        // A few sample items make it easier to test the application.
        inventory.addItem("Counter Books", "Scholastics", 50, 2500);
        inventory.addItem("Liquid Soap", "Cleaning", 20, 5000);
        inventory.addItem("Rice", "Food", 30, 4200);
        refreshTable();
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 5, 8, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Add New Stock Item"));

        nameField = new JTextField();
        categoryBox = new JComboBox<>(new String[] {"Cleaning", "Food", "Scholastics"});
        quantityField = new JTextField();
        priceField = new JTextField();
        JButton addButton = new JButton("Add Item");

        panel.add(new JLabel("Item Name"));
        panel.add(new JLabel("Category"));
        panel.add(new JLabel("Quantity"));
        panel.add(new JLabel("Unit Price (UGX)"));
        panel.add(new JLabel(""));

        panel.add(nameField);
        panel.add(categoryBox);
        panel.add(quantityField);
        panel.add(priceField);
        panel.add(addButton);

        addButton.addActionListener(e -> handleAction("ADD"));
        return panel;
    }

    private JScrollPane createTablePanel() {
        String[] columns = {"ID", "Item Name", "Category", "Quantity", "Unit Price (UGX)", "Stock Value (UGX)"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        itemTable = new JTable(tableModel);
        itemTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return new JScrollPane(itemTable);
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(15);
        JButton searchButton = new JButton("Search");
        JButton showAllButton = new JButton("Show All");
        JButton restockButton = new JButton("Restock Selected");
        JButton issueButton = new JButton("Issue Selected");

        searchPanel.add(new JLabel("Search by name:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(showAllButton);
        searchPanel.add(restockButton);
        searchPanel.add(issueButton);

        searchButton.addActionListener(e -> handleAction("SEARCH"));
        showAllButton.addActionListener(e -> handleAction("SHOW_ALL"));
        restockButton.addActionListener(e -> handleAction("RESTOCK"));
        issueButton.addActionListener(e -> handleAction("ISSUE"));

        JPanel informationPanel = new JPanel(new BorderLayout());
        statusLabel = new JLabel("Ready.");
        totalValueLabel = new JLabel("Total inventory value: UGX 0.00");
        informationPanel.add(statusLabel, BorderLayout.WEST);
        informationPanel.add(totalValueLabel, BorderLayout.EAST);

        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(informationPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void handleAction(String action) {
        switch (action) {
            case "ADD":
                addItem();
                break;
            case "SEARCH":
                searchItem();
                break;
            case "SHOW_ALL":
                refreshTable();
                statusLabel.setText("Showing all items.");
                break;
            case "RESTOCK":
                changeStock(true);
                break;
            case "ISSUE":
                changeStock(false);
                break;
            default:
                statusLabel.setText("Unknown action.");
        }
    }

    private void addItem() {
        String name = nameField.getText().trim();
        String category = (String) categoryBox.getSelectedItem();

        try {
            int quantity = Integer.parseInt(quantityField.getText().trim());
            double price = Double.parseDouble(priceField.getText().trim());

            if (name.isEmpty() || quantity < 0 || price < 0) {
                JOptionPane.showMessageDialog(this,
                        "Enter an item name, a quantity of 0 or more, and a price of 0 or more.");
                return;
            }

            boolean added = inventory.addItem(name, category, quantity, price);
            if (added) {
                statusLabel.setText("Item added successfully.");
                nameField.setText("");
                quantityField.setText("");
                priceField.setText("");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Could not add item. The array may be full.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantity must be a whole number and price must be numeric.");
        }
    }

    private void searchItem() {
        String name = searchField.getText().trim();
        Item item = inventory.findItemByName(name);

        if (item == null) {
            refreshTable();
            statusLabel.setText("No item found with that exact name.");
        } else {
            tableModel.setRowCount(0);
            addItemToTable(item);
            statusLabel.setText("Search result: " + item.getName());
        }
    }

    private void changeStock(boolean restock) {
        int selectedRow = itemTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select an item from the table first.");
            return;
        }

        int itemId = (int) tableModel.getValueAt(selectedRow, 0);
        Item item = inventory.findItemById(itemId);
        if (item == null) {
            JOptionPane.showMessageDialog(this, "The selected item could not be found.");
            return;
        }

        String prompt = restock ? "Enter quantity to add:" : "Enter quantity to issue:";
        String input = JOptionPane.showInputDialog(this, prompt);
        if (input == null) {
            return;
        }

        try {
            int amount = Integer.parseInt(input.trim());
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Enter a quantity greater than zero.");
                return;
            }

            if (restock) {
                item.restock(amount);
                statusLabel.setText("Stock restocked successfully.");
            } else {
                boolean issued = item.issueStock(amount);
                if (issued) {
                    statusLabel.setText("Stock issued successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Not enough stock available.");
                }
            }
            refreshTable();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter a valid whole number.");
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (int i = 0; i < inventory.getItemCount(); i++) {
            addItemToTable(inventory.getItemAt(i));
        }
        totalValueLabel.setText(String.format("Total inventory value: UGX %,.2f",
                inventory.getTotalInventoryValue()));
    }

    private void addItemToTable(Item item) {
        Object[] row = {
            item.getItemId(),
            item.getName(),
            item.getCategory(),
            item.getQuantity(),
            String.format("%,.2f", item.getUnitPrice()),
            String.format("%,.2f", item.getTotalValue())
        };
        tableModel.addRow(row);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InventoryManagementGUI app = new InventoryManagementGUI();
            app.setVisible(true);
        });
    }
}
