public class WashingMachine extends Appliance{

    public WashingMachine(int electricityUse, int gasUse, int waterUse, int timeOn) {
        super(electricityUse, gasUse, waterUse, timeOn);
    }

    @Override
    public void use(boolean state, Person person) throws Exception {
        if (!getCurrentState()) {
            doWashing();
        } else {
            throw new Exception(person.getName() + " tried to use on a washing machine already in use");
        }
    }

    private void doWashing(){
        setTimeRemaining(getTimeOn());
        setCurrentState(true);
        System.out.println("Washing machine turned on");
    }
}
