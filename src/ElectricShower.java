public class ElectricShower extends Shower{

    public ElectricShower(int electricityUse, int gasUse, int waterUse, int timeOn) {
        super(electricityUse, gasUse, waterUse, timeOn);
    }

    @Override
    void shower() {
        setTimeRemaining(getTimeOn());
        setCurrentState(true);
        System.out.println("Electric shower turned on");
    }
}
