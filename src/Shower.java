public abstract class Shower extends Appliance{

    public Shower(int electricityUse, int gasUse, int waterUse, int timeOn) {
        super(electricityUse, gasUse, waterUse, timeOn);
    }

    @Override
    public void use(boolean state, Person person) throws Exception{
        if (!getCurrentState()) {
            shower();
        } else {
            throw new Exception(person.getName() + " tried to use a shower already in use");
        }
    }

    abstract void shower();
}
