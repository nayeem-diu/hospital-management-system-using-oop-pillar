class Billing {
    private static final double BED_PRICE_PER_DAY = 3500.0;
    private static final double BLOOD_PRICE_PER_BAG = 1000.0;
    private static final double DISCOUNT_THRESHOLD = 30000.0;
    private static final double DISCOUNT_RATE = 0.10;

    private double lastSubtotal;
    private double lastDiscount;
    private double lastTotal;

    private double calculateSubtotal(int bedDays, int bloodBags) {
        return (bedDays * BED_PRICE_PER_DAY) + (bloodBags * BLOOD_PRICE_PER_BAG);
    }

    private double calculateDiscount(double subtotal) {
        if (subtotal > DISCOUNT_THRESHOLD) {
            return subtotal * DISCOUNT_RATE;
        }
        return 0.0;
    }

    public double generateBill(String patientId, int bedDays, int bloodBags) {
        double subtotal = calculateSubtotal(bedDays, bloodBags);
        double discount = calculateDiscount(subtotal);
        double total = subtotal - discount;

        this.lastSubtotal = subtotal;
        this.lastDiscount = discount;
        this.lastTotal = total;

        printInvoice(patientId, bedDays, bloodBags);
        return total;
    }

    private void printInvoice(String patientId, int bedDays, int bloodBags) {
        System.out.println("============ BILLING INVOICE ============");
        System.out.println("Patient ID     : " + patientId);
        System.out.println("Bed Days       : " + bedDays + " x " + BED_PRICE_PER_DAY + " tk = "
                + (bedDays * BED_PRICE_PER_DAY) + " tk");
        System.out.println("Blood Bags     : " + bloodBags + " x " + BLOOD_PRICE_PER_BAG + " tk = "
                + (bloodBags * BLOOD_PRICE_PER_BAG) + " tk");
        System.out.println("------------------------------------------");
        System.out.println("Subtotal       : " + lastSubtotal + " tk");
        if (lastDiscount > 0) {
            System.out.println("Discount (10%) : -" + lastDiscount + " tk");
        } else {
            System.out.println("Discount       : Not applicable (needs > " + DISCOUNT_THRESHOLD + " tk)");
        }
        System.out.println("Total Payable  : " + lastTotal + " tk");
        System.out.println("==========================================");
    }

    public double getLastTotal() {
        return lastTotal;
    }

    // NEW: these two let the GUI show the same subtotal/discount breakdown
    // that printInvoice() prints to the console, without duplicating the
    // pricing math anywhere else.
    public double getLastSubtotal() {
        return lastSubtotal;
    }

    public double getLastDiscount() {
        return lastDiscount;
    }
}
