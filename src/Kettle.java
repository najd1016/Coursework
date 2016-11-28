public class Kettle extends Appliance {

    public Kettle(int electricityUse, int gasUse, int waterUse, int timeOn) {
        super(electricityUse, gasUse, waterUse, timeOn);
    }

    @Override
    public void use(boolean state, Person person) throws Exception {
        if (person instanceof Adult){
            boil();
        }else{
            throw new Exception("Child tried to use the kettle!");
        }
    }

    public void boil() {
        setTimeRemaining(getTimeOn());
        setCurrentState(true);
        System.out.println("Kettle turned on");
    }
}
