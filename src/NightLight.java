public class NightLight extends Appliance{

    public NightLight(int electricityUse, int gasUse, int waterUse, int timeOn) {
        super(electricityUse, gasUse, waterUse, timeOn);
    }

    //if state = true, light is switched on and vice versa
    @Override
    public void use(boolean state, Person person) throws Exception {
        setCurrentState(state);

        if(state){
            System.out.println("Nightlight turned on");
        }else{
            System.out.println("Nightlight turned off");
        }
    }
}
