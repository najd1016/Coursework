
public class Boiler extends Appliance{

    public Boiler(int electricityUse, int gasUse, int waterUse, int timeOn) {
        super(electricityUse, gasUse, waterUse, timeOn);
    }

    //checks person using is an adult and then sets currentState = state
    //throws exception if child is trying to use boiler
    @Override
    public void use(boolean state, Person person) throws Exception {
        if (person instanceof GrownUp) {

            setCurrentState(state);

            if(state){
                System.out.println("Boiler turned on");
            }else{
                System.out.println("Boiler turned off");
            }

        }else{
            throw new Exception("Child tried to use a boiler!");
        }
    }

    //extra methods allow easier use when simulating central heating

    public void turnOn() {
        setCurrentState(true);
        System.out.println("Boiler turned on");
    }

    public void turnOff() {
        setCurrentState(false);
        System.out.println("Boiler turned off");
    }
}
