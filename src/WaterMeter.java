public class WaterMeter extends Meter{

    public WaterMeter(int startingValueConsumed, boolean canGenerate) {
        super(startingValueConsumed, canGenerate);
    }

    @Override
    String getType() {
        return "water";
    }
}
