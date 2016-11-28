public class ElectricMeter extends Meter {

    public ElectricMeter(int startingValueConsumed, boolean canGenerate) {
        super(startingValueConsumed, canGenerate);
    }

    @Override
    String getType() {
        return "electric";
    }
}
