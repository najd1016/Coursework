public class PowerShower extends Shower{

    public PowerShower(int electricityUse, int gasUse, int waterUse, int timeOn) {
        super(electricityUse, gasUse, waterUse, timeOn);
    }

    @Override
    void shower() {
        setTimeRemaining(getTimeOn());
        setCurrentState(true);
        System.out.println("Power shower turned on");
    }
}
