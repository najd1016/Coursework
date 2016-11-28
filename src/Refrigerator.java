public class Refrigerator extends Appliance{

    public Refrigerator(int electricityUse, int gasUse, int waterUse, int timeOn) {
        super(electricityUse, gasUse, waterUse, timeOn);
        setCurrentState(true);
        System.out.println("Refrigerator turned on");
    }

    @Override
    public void use(boolean state, Person person) throws Exception{ }
}
