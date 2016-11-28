public abstract class Cooker extends Appliance{

    public Cooker(int electricityUse, int gasUse, int waterUse, int timeOn) {
        super(electricityUse, gasUse, waterUse, timeOn);
    }

    //calls cook method
    @Override
    public void use(boolean state, Person person) throws Exception{
        if(person instanceof Adult){
            cook();
        }else{
            throw new Exception("Child tried to use a cooker!");
        }
    }

    //method abstract just so we can hardcode "Electric Cooker turned on" etc in the separate classes
    abstract void cook();
}
