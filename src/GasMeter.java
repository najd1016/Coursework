public class GasMeter extends Meter{

    public GasMeter(int startingValueConsumed, boolean canGenerate) {
        super(startingValueConsumed, canGenerate);
    }

    @Override
    String getType() {
        return "gas";
    }
}
