import java.util.LinkedHashMap;
import java.util.Map;

class BloodBank {
    public static final int PRICE_PER_BAG = 1000;
    private static final int STARTING_BAGS = 10;

    private Map<String, Integer> stock;

    public BloodBank() {
        stock = new LinkedHashMap<>();
        String[] groups = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        for (String g : groups) {
            stock.put(g, STARTING_BAGS);
        }
    }

    public boolean isValidGroup(String group) {
        return stock.containsKey(group);
    }

    public synchronized boolean bookBlood(String group, int qty) {
        if (!isValidGroup(group)) return false;
        if (qty <= 0) return false;
        if (stock.get(group) < qty) return false;
        stock.put(group, stock.get(group) - qty);
        return true;
    }

    public synchronized void showStock() {
        System.out.println("========= BLOOD BANK STOCK =========");
        for (Map.Entry<String, Integer> entry : stock.entrySet()) {
            System.out.println(entry.getKey() + "  ->  " + entry.getValue() + " bag(s)");
        }
        System.out.println("Price per bag: " + PRICE_PER_BAG + " tk");
        System.out.println("=====================================");
    }

    public synchronized boolean isAnyGroupLow() {
        for (int qty : stock.values()) {
            if (qty <= 2) return true;
        }
        return false;
    }

    public synchronized String getLowStockGroups() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : stock.entrySet()) {
            if (entry.getValue() <= 2) {
                sb.append(entry.getKey()).append("(").append(entry.getValue()).append(") ");
            }
        }
        return sb.toString();
    }

    // NEW: hands the GUI a copy of the current stock map so it can fill a
    // table without being able to secretly edit the real stock numbers.
    public synchronized Map<String, Integer> getStockSnapshot() {
        return new LinkedHashMap<>(stock);
    }
}
