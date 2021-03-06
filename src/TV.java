public class TV extends Appliance{

    public TV(int electricityUse, int gasUse, int waterUse, int timeOn) {
        super(electricityUse, gasUse, waterUse, timeOn);
    }

    //checks person using is an adult as children aren't allowed to turn off TV
    //then sets currentState = state accordingly ie wont alter currentState if on and
    //person is a child
    public void use(boolean state, Person person) throws Exception {
        if (person instanceof GrownUp) {

            setCurrentState(state);

            if(state){
                System.out.println("TV turned on");
            }else{
                System.out.println("TV turned off");
            }

        }else{
            if (state) {
                setCurrentState(true);
                System.out.println("TV turned on");
            }else{
                throw new Exception("Child tried to turn off TV: not allowed!");
            }
        }
    }

}
