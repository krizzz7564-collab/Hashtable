import java.util.*;

public class InventoryManager {

    // productId -> stock count
    private HashMap<String, Integer> stock = new HashMap<>();

    // waiting list when stock finishes
    private LinkedHashMap<Integer, String> waitingList = new LinkedHashMap<>();

    // add product
    public void addProduct(String productId, int quantity) {
        stock.put(productId, quantity);
    }

    // check stock
    public int checkStock(String productId) {
        return stock.getOrDefault(productId, 0);
    }

    // purchase item
    public synchronized String purchaseItem(String productId, int userId) {

        int available = stock.getOrDefault(productId, 0);

        if (available > 0) {
            stock.put(productId, available - 1);
            return "Purchase successful. Remaining stock: " + (available - 1);
        } else {
            waitingList.put(userId, productId);
            return "Stock finished. Added to waiting list. Position: " + waitingList.size();
        }
    }

    public static void main(String[] args) {

        InventoryManager manager = new InventoryManager();

        manager.addProduct("IPHONE15_256GB", 3);

        System.out.println("Stock: " + manager.checkStock("IPHONE15_256GB"));

        System.out.println(manager.purchaseItem("IPHONE15_256GB", 101));
        System.out.println(manager.purchaseItem("IPHONE15_256GB", 102));
        System.out.println(manager.purchaseItem("IPHONE15_256GB", 103));
        System.out.println(manager.purchaseItem("IPHONE15_256GB", 104)); // waiting list
    }
}