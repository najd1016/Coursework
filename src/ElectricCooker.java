public class ElectricCooker extends Cooker{

    public ElectricCooker(int electricityUse, int gasUse, int waterUse, int timeOn) {
        super(electricityUse, gasUse, waterUse, timeOn);
    }

    //turns on cooker
    @Override
    void cook() {
        setTimeRemaining(getTimeOn());
        setCurrentState(true);
        System.out.println("Electric cooker turned on");
    }
}
