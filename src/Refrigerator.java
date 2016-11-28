public class Refrigerator extends Appliance{

    public Refrigerator(int electricityUse, int gasUse, int waterUse, int timeOn) {
        super(electricityUse, gasUse, waterUse, timeOn);

        //since its always on and can't be switched off we set the current state
        //to true to turn it on and leave it as that
        setCurrentState(true);
        System.out.println("Refrigerator turned on");
    }

    @Override
    public void use(boolean state, Person person) throws Exception{ }
}
