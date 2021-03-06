public abstract class Cooker extends Appliance{

    public Cooker(int electricityUse, int gasUse, int waterUse, int timeOn) {
        super(electricityUse, gasUse, waterUse, timeOn);
    }

    //calls cook method
    @Override
    public void use(boolean state, Person person) throws Exception{
        if (!getCurrentState()) {
            if (person instanceof GrownUp) {
                cook();
            } else {
                throw new Exception(person.getName() + " is a child and tried to use a cooker!");
            }
        } else {
            throw new Exception(person.getName() + " tried to use a cooker already in use");
        }
    }

    //method abstract just so we can hardcode "Electric Cooker turned on" etc in the separate classes
    abstract void cook();
}
