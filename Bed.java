class Bed {
    private static final int TOTAL_BEDS = 50;
    private String[] occupant;

    public Bed() {
        occupant = new String[TOTAL_BEDS];
    }

    public synchronized int assignBed(String patientId) {
        for (int i = 0; i < TOTAL_BEDS; i++) {
            if (occupant[i] == null) {
                occupant[i] = patientId;
                return i + 1;
            }
        }
        return -1;
    }

    public synchronized boolean releaseBed(int bedNumber) {
        if (bedNumber < 1 || bedNumber > TOTAL_BEDS) return false;
        if (occupant[bedNumber - 1] == null) return false;
        occupant[bedNumber - 1] = null;
        return true;
    }

    public int getTotalBeds() {
        return TOTAL_BEDS;
    }

    public synchronized int getEmptyCount() {
        int count = 0;
        for (String s : occupant) if (s == null) count++;
        return count;
    }

    // NEW: gives the GUI a safe copy of the bed occupancy array so it can
    // draw the bed grid without letting outside code modify the real array.
    public synchronized String[] getOccupantSnapshot() {
        return occupant.clone();
    }

    public synchronized void showBedStatus() {
        int empty = getEmptyCount();
        System.out.println("=========== BED STATUS ===========");
        System.out.println("Total Beds   : " + TOTAL_BEDS);
        System.out.println("Empty Beds   : " + empty);
        System.out.println("Occupied Beds: " + (TOTAL_BEDS - empty));
        System.out.println("-----------------------------------");
        for (int i = 0; i < TOTAL_BEDS; i++) {
            String status = (occupant[i] == null) ? "Empty" : "Patient ID: " + occupant[i];
            System.out.println("Bed " + (i + 1) + " -> " + status);
        }
        System.out.println("===================================");
    }
}
