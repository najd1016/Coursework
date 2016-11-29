import java.text.DecimalFormat;
import java.util.ArrayList;

public class House {

    private ArrayList<Appliance> houseAppliances;

    private ArrayList<Person> persons;

    private Meter electricMeter, gasMeter, waterMeter;

    private int timePassed;

    private int boilerOnFor;

    private double indoorTemperature, outdoorTemperature, desiredTemp;

    //construction sets timePasses = 0, temperature to 20 degrees and initialises both arrayLists
    public House(){
        timePassed = 0;
        indoorTemperature = 20.0;
        outdoorTemperature = 14.0;
        desiredTemp = 18.0;
        houseAppliances = new ArrayList<>();
        persons = new ArrayList<>();
    }

    //using if(instanceof) it checks what type of meter has been supplied and sets the corresponding Meter above
    //if the meter has already been set and the same type is supplied again the 1st will be overwritten
    public void addMeter(Meter meter){
        if(meter instanceof ElectricMeter){

            electricMeter = meter;
            System.out.println("Added electric meter.");

        }else if(meter instanceof GasMeter){

            gasMeter = meter;
            System.out.println("Added gas meter.");

        }else if(meter instanceof WaterMeter){

            waterMeter = meter;
            System.out.println("Added water meter.");

        }else{
            System.out.println("Tried to add a meter with no type!");
        }
    }

    //simply adds the Person object supplied to the ArrayList
    public void addPerson(Person person){
        persons.add(person);
    }

    //throws exceptions if appliance object is null, or the house already has 25 appliances attached
    //otherwise attaches meters (will add gasMeter even if null as null handled elsewhere) and then adds appliance
    //to the array list
    public void addAppliance(Appliance appliance) throws Exception{
        if(appliance == null) throw new Exception("Attempted to add appliance that was equal to null!");

        //have to cast Meters as setter methods take specific meters
        if(numAppliances() < 25) {
            appliance.attachElectricMeter((ElectricMeter) electricMeter);
            appliance.attachGasMeter((GasMeter) gasMeter);
            appliance.attachWaterMeter((WaterMeter) waterMeter);

            houseAppliances.add(appliance);

        }else throw new Exception("Already have 25 appliances can't add more!");
    }

    //simply removes the supplied Appliance object from the arrayList if it is contained
    //no need to catch errors or anything as if not contained in list no exceptions are thrown
    public void removeAppliance(Appliance appliance){
        houseAppliances.remove(appliance);
    }

    //returns number of appliances
    public int numAppliances(){
        return houseAppliances.size();
    }

    //TODO add boiler simulation method

    private void simulateBoiler(Boiler boiler) {

        if (indoorTemperature < desiredTemp) boiler.turnOn();

        //house temp only decreases if boiler off
        if (!boiler.getCurrentState()) {
            if (indoorTemperature > outdoorTemperature) {
                double tempDiff = indoorTemperature - outdoorTemperature;
                indoorTemperature -= (Math.pow(1.3, (tempDiff)) / 12);
            }
        } else {
            boilerOnFor++;
            indoorTemperature += (Math.pow(1.1, boilerOnFor) / 5);

            //boiler always stays on for 2 hours before switching off heat increase is
            //based on how long boiler has been on - ie takes time to 'warmup'
            if (boilerOnFor == 8) {
                boilerOnFor = 0;
                boiler.turnOff();
            }
        }

        DecimalFormat df = new DecimalFormat("#.00");

        System.out.println("Temperature: " + df.format(indoorTemperature));
    }

    /*temperature -= 0.2;

                if(appliance.getCurrentState()) temperature += 0.5;

    //have to cast to Boiler so we can use .turnOn method, could just change state but that wouldn't print 'Boiler turned x'
                if(temperature < 19.0){
        appliance.setCurrentState(true);
    }else{
        appliance.setCurrentState(false);
    }

    //just so we don't get a mad number of decimal places from temperature
    DecimalFormat df = new DecimalFormat("#.00");

                System.out.println("Temperature: "+df.format(temperature));
    */

    //method uses foreach loop to call timePasses on each appliance and the same for persons too
    //within foreach loop for appliances, the simulation of indoor temperature also occurs since we have access to the boiler here
    //method also increments timePassed to keep track of time and prints out the totals when time reaches 96 (end of day)
    public void timePasses(){

        timePassed++;

        System.out.println();
        System.out.println("Time: "+timePassed);

        //persons must be iterated over first so that the increment consumeds are correct if the
        //currentState of an appliance was altered by a persons task
        for (Person person: persons) {
            try {
                person.timePasses(houseAppliances);
            } catch (Exception e) {
                System.err.println("Error whilst calling time passes on person "+person.getName()+": "+e.getMessage());
            }
        }

        for(Appliance appliance: houseAppliances){

            try {
                appliance.timePasses();
            } catch (Exception e) {
                System.err.println("Error whilst calling time passes on appliance: "+appliance.getClass().getName());

                if(e.getMessage() == null) System.err.println("Error seems to caused by a null meter, are all the required meters present?");
            }

            //check if current appliance is our Boiler, means if config doesn't contain boiler temp wont be simulated
            if(appliance instanceof Boiler){
                simulateBoiler((Boiler) appliance);
            }
        }

        if(timePassed == 96){

            System.out.println();
            System.out.println("End of day!");
            System.out.println("Today's totals are:");

            //null checks to avoid null pointer exceptions if the meter was somehow not initialised
            if(electricMeter != null){
                System.out.println("Electricity consumed: "+electricMeter.getConsumed());

                //no point printing generated if its just going to be 0
                if(electricMeter.canGenerate()) System.out.println("Electricity generated: "+electricMeter.getGenerated());
            }

            if(gasMeter != null) System.out.println("Gas consumed: "+gasMeter.getConsumed());

            if(waterMeter != null) System.out.println("Water consumed: "+waterMeter.getConsumed());

            System.exit(0);
        }
    }

}
