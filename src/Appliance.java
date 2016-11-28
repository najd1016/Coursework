public abstract class Appliance {

    private int electricityUse; //electricity use in 15mins
    private int gasUse; //gas use in 15mins
    private int waterUse; //water use in 15mins

    //how long remains working for when switched on in multiples of 15mins, -1 represents always
    private int timeOn;

    //electricity generated in 15mins
    private int electricityGenerated;

    //how long in multiples of 15 mins is currently remaining of the appliance being on (currentState = true)
    private int timeRemaining;

    private boolean currentState; //false = off, true = on
    private boolean isGenerator; //false = not gen, true = is gen

    private ElectricMeter electricMeter;
    private GasMeter gasMeter;
    private WaterMeter waterMeter;

    //constructor for a normal appliance
    public Appliance(int electricityUse, int gasUse, int waterUse, int timeOn){
        this.electricityUse = electricityUse;
        this.gasUse = gasUse;
        this.waterUse = waterUse;
        this.timeOn = timeOn;
    }

    //constructor for a generator
    public Appliance(int electricityGenerated){
        this.electricityGenerated = electricityGenerated;
        isGenerator = true;
    }

    //throws Exception added to carry Exceptions thrown by appliances back to House, where they are dealt with
    public abstract void use(boolean state, Person person) throws Exception;

    //setter methods for meters, require specific meters instead of just Meter to ensure we can't set our electricMeter to a gasMeter
    public void attachElectricMeter(ElectricMeter electricMeter){
        this.electricMeter = electricMeter;
    }

    public void attachGasMeter(GasMeter gasMeter){
        this.gasMeter = gasMeter;
    }

    public void attachWaterMeter(WaterMeter waterMeter){
        this.waterMeter = waterMeter;
    }

    //returns timeOn so that appliances can pass this to setTimeRemaining and turn on for correct cycle time
    public int getTimeOn(){
        return timeOn;
    }

    //returns currentState so we can tell if appliance is off or on
    public boolean getCurrentState(){
        return currentState;
    }

    //sets the currentState. Required to allow appliances to turn themselves on or off if their use() method was called
    public void setCurrentState(boolean currentState){
        this.currentState = currentState;
    }

    //sets the timeRemaining. Required to allow appliances to turn themselves on for a specific time if their use() method was called
    public void setTimeRemaining(int timeRemaining){
        this.timeRemaining = timeRemaining;
    }

    //checks if appliance is on and how long it has remaining
    //performs logic to turn off appliance if timeRemaining is 0 and also increments meters
    //throws Exception added to carry Exceptions thrown by meters back to house
    public void timePasses() throws Exception {

        if(currentState) {

            if(isGenerator) {

                electricMeter.incrementGenerated(electricityGenerated);

            }else{

                electricMeter.incrementConsumed(electricityUse);

                if (electricMeter.canGenerate())
                    electricMeter.incrementGenerated(electricityGenerated);

                //gas meter not essential as house appliances can run entirely off water + electricity
                //so the gasMeter may not have been created = null pointer exception if you try and increment
                //thus we check if appliance actually requires gas before calling to avoid unneeded errors
                if (gasUse != 0)
                    gasMeter.incrementConsumed(gasUse);

                waterMeter.incrementConsumed(waterUse);

                //decreases time remaining as long as > 0. If < 0, value should -1 which means run for infinity
                //meaning we don't have to worry about decreasing timeOn, turning off etc
                if (timeRemaining > 0) {
                    timeRemaining--;

                    if (timeRemaining == 0) {

                        currentState = false;

                        //split on capitals, e.g. ElectricMeter becomes Electric Meter. Pointless, but why not aye
                        String[] splitName = getClass().getName().split("(?=[A-Z])");

                        for (String s : splitName) {
                            System.out.print(s + " "); //add space to separate split words
                        }

                        System.out.println("turned off");

                    }
                }
            }
        }
    }
}
