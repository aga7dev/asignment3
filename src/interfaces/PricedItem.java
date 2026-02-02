package interfaces;

public interface PricedItem {

    double getDailyRate();

    default double calculatePrice(int days) {
        if (days <= 0) {
            return 0;
        }
        return getDailyRate() * days;
    }

    static double roundPrice(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
