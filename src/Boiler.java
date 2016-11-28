
public class Boiler extends Appliance{

    public Boiler(int electricityUse, int gasUse, int waterUse, int timeOn) {
        super(electricityUse, gasUse, waterUse, timeOn);
    }

    //checks person using is an adult and then sets currentState = state
    @Override
    public void use(boolean state, Person person) throws Exception {
        if(person instanceof Adult) {

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
}
