import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Abstract class LibraryItem
abstract class LibraryItem {
    private String id;
    protected boolean availability;
    protected LocalDate dueToDate;

    public LibraryItem(String id) {
        this.id = id;
        this.availability = true;
    }

    public String getId() {
        return id;
    }

    public boolean isAvailable() {
        return availability;
    }

    public abstract void borrowItem();

    public void returnItem() {
        this.availability = true;
        this.dueToDate = null;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Available: " + availability +
                (dueToDate != null ? ", Due Date: " + dueToDate : "");
    }
}

// Class Book
class Book extends LibraryItem {
    private String title;

    public Book(String id, String title) {
        super(id);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public void borrowItem() {
        if (isAvailable()) {
            availability = false;
            dueToDate = LocalDate.now().plusWeeks(4); // Due in 4 weeks
        }
    }

    @Override
    public String toString() {
        return "Book - " + title + ", " + super.toString();
    }
}

// Class Magazine
class Magazine extends LibraryItem {
    private String issue;

    public Magazine(String id, String issue) {
        super(id);
        this.issue = issue;
    }

    public String getIssue() {
        return issue;
    }

    @Override
    public void borrowItem() {
        if (isAvailable()) {
            availability = false;
            dueToDate = LocalDate.now().plusWeeks(2); // Due in 2 weeks
        }
    }

    @Override
    public String toString() {
        return "Magazine - Issue: " + issue + ", " + super.toString();
    }
}

// Class LibraryManager
class LibraryManager {
    private List<LibraryItem> libraryItems = new ArrayList<>();

    public void addLibraryItem(LibraryItem item) {
        libraryItems.add(item);
    }

    public void removeLibraryItem(LibraryItem item) {
        libraryItems.remove(item);
    }

    public List<LibraryItem> getLibraryItems() {
        return libraryItems;
    }

    public LibraryItem findItemById(String id) {
        for (LibraryItem item : libraryItems) {
            if (item.getId().equalsIgnoreCase(id)) {
                return item;
            }
        }
        return null;
    }
}

// Main Class with Swing GUI
public class LibraryManagementSystem {
    private static LibraryManager libraryManager = new LibraryManager();

    public static void main(String[] args) {
        
        

        // Create Main Menu
        SwingUtilities.invokeLater(() -> createMainMenu());
    }

    private static void createMainMenu() {
        JFrame frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JButton viewItemsButton = new JButton("View Library Items");
        JButton borrowItemButton = new JButton("Borrow Item");
        JButton returnItemButton = new JButton("Return Item");
        JButton addBookButton = new JButton("Add Book");
        JButton addMagazineButton = new JButton("Add Magazine");
        JButton exitButton = new JButton("Exit");

        // Action Listeners
        viewItemsButton.addActionListener(e -> viewLibraryItems());
        borrowItemButton.addActionListener(e -> borrowLibraryItem());
        returnItemButton.addActionListener(e -> returnLibraryItem());
        addBookButton.addActionListener(e -> addBook());
        addMagazineButton.addActionListener(e -> addMagazine());
        exitButton.addActionListener(e -> System.exit(0));

        // Layout
        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.add(viewItemsButton);
        panel.add(borrowItemButton);
        panel.add(returnItemButton);
        panel.add(addBookButton);
        panel.add(addMagazineButton);
        panel.add(exitButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void viewLibraryItems() {
        List<LibraryItem> items = libraryManager.getLibraryItems();
        StringBuilder sb = new StringBuilder();
        for (LibraryItem item : items) {
            sb.append(item).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.length() > 0 ? sb.toString() : "No items in the library.", "Library Items", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void borrowLibraryItem() {
        String id = JOptionPane.showInputDialog("Enter the ID of the item to borrow:");
        LibraryItem item = libraryManager.findItemById(id);

        if (item != null) {
            if (item.isAvailable()) {
                item.borrowItem();
                JOptionPane.showMessageDialog(null, "Item borrowed successfully. Due Date: " + item.dueToDate, "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Item is not available.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Item not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void returnLibraryItem() {
        String id = JOptionPane.showInputDialog("Enter the ID of the item to return:");
        LibraryItem item = libraryManager.findItemById(id);

        if (item != null) {
            if (!item.isAvailable()) {
                item.returnItem();
                JOptionPane.showMessageDialog(null, "Item returned successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Item is already available.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Item not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void addBook() {
        String id = JOptionPane.showInputDialog("Enter the Book ID:");
        String title = JOptionPane.showInputDialog("Enter the Book Title:");
        libraryManager.addLibraryItem(new Book(id, title));
        JOptionPane.showMessageDialog(null, "Book added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void addMagazine() {
        String id = JOptionPane.showInputDialog("Enter the Magazine ID:");
        String issue = JOptionPane.showInputDialog("Enter the Magazine Issue:");
        libraryManager.addLibraryItem(new Magazine(id, issue));
        JOptionPane.showMessageDialog(null, "Magazine added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
