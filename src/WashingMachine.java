public class WashingMachine extends Appliance{

    public WashingMachine(int electricityUse, int gasUse, int waterUse, int timeOn) {
        super(electricityUse, gasUse, waterUse, timeOn);
    }

    @Override
    public void use(boolean state, Person person) throws Exception {
        doWashing();
    }

    public void doWashing(){
        setTimeRemaining(getTimeOn());
        setCurrentState(true);
        System.out.println("Washing machine turned on");
    }
}
