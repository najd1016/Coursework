public class Dishwasher extends Appliance{

    public Dishwasher(int electricityUse, int gasUse, int waterUse, int timeOn) {
        super(electricityUse, gasUse, waterUse, timeOn);
    }

    @Override
    public void use(boolean state, Person person) throws Exception{
        if (!getCurrentState()) {
            washDishes();
        } else {
            throw new Exception(person.getName() + " tried to use a dishwasher already in use");
        }
    }

    //turns on dishwasher
    private void washDishes(){
        setTimeRemaining(getTimeOn());
        setCurrentState(true);
        System.out.println("Dishwasher turned on");
    }
}
