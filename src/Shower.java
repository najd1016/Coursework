public abstract class Shower extends Appliance{

    public Shower(int electricityUse, int gasUse, int waterUse, int timeOn) {
        super(electricityUse, gasUse, waterUse, timeOn);
    }

    @Override
    public void use(boolean state, Person person) throws Exception{
        shower();
    }

    abstract void shower();
}
