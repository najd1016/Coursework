public class Kettle extends Appliance {

    public Kettle(int electricityUse, int gasUse, int waterUse, int timeOn) {
        super(electricityUse, gasUse, waterUse, timeOn);
    }

    @Override
    public void use(boolean state, Person person) throws Exception {
        if (!getCurrentState()) {
            if (person instanceof GrownUp) {
                boil();
            } else {
                throw new Exception("Child tried to use the kettle!");
            }
            throw new Exception(person.getName() + " tried to cook on a cooker already in use");
        } else {
            throw new Exception(person.getName() + " tried to use a kettle already in use");
        }
    }

    public void boil() {
        setTimeRemaining(getTimeOn());
        setCurrentState(true);
        System.out.println("Kettle turned on");
    }
}
