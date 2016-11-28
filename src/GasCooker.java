public class GasCooker extends Cooker {

    public GasCooker(int electricityUse, int gasUse, int waterUse, int timeOn) {
        super(electricityUse, gasUse, waterUse, timeOn);
    }

    @Override
    void cook() {
        setTimeRemaining(getTimeOn());
        setCurrentState(true);
        System.out.println("Gas cooker turned on");
    }
}
