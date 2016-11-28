import java.util.ArrayList;
import java.util.HashMap;

public abstract class Person {

    private int age;
    private int currentTime;
    private String name;
    private String gender; //never used as gender not necessary for anything like age or name is

    //integer first then string as easier to call .get(currentTime) to return task
    //string instead of iterating through to find string with value = currentTime
    private HashMap<Integer, String> tasksToDo;

    //constructor sets age, name and gender. Also initialises tasks hashMap and sets time = 0.
    public Person(int age, String name, String gender){
        this.age = age;
        this.name = name;
        this.gender = gender;

        currentTime = 0;

        tasksToDo = new HashMap<>();
    }

    //If timePasses finds task the string will be passed here along with an ArrayList of appliances passed from the House object
    //ArrayList is necessary so we can A: check if they can actually execute their task (does appliance req exist)
    //and B: call .use(xyz) on the appliance without having to use statics or something else to directly access the appliance in the House object
    public void executeTask(String task, ArrayList<Appliance> appliances) throws Exception{

        ArrayList<Object> desiredClasses = new ArrayList<>();
        boolean state = false;

        switch(task.toLowerCase()){
            case "boil":
                desiredClasses.add(Kettle.class);
                break;
            case "dowashing":
                desiredClasses.add(WashingMachine.class);
                break;
            case "turnontv":
                desiredClasses.add(TV.class);
                state = true;
                break;
            case "turnofftv":
                desiredClasses.add(TV.class);
                break;
            case "turnonlight":
                desiredClasses.add(NightLight.class);
                state = true;
                break;
            case "turnofflight":
                desiredClasses.add(NightLight.class);
                break;
            case "turnonboiler":
                desiredClasses.add(Boiler.class);
                state = true;
                break;
            case "turnoffboiler":
                desiredClasses.add(Boiler.class);
                break;
            case "cook":
                desiredClasses.add(ElectricCooker.class);
                desiredClasses.add(GasCooker.class);
                break;
            case "shower":
                desiredClasses.add(ElectricShower.class);
                desiredClasses.add(PowerShower.class);
                break;
            case "washdishes":
                desiredClasses.add(Dishwasher.class);
                break;
        }

        Boolean applianceFound = false;

        for (Appliance appliance: appliances){

            if(desiredClasses.contains(appliance.getClass())){
                appliance.use(state, this);
                applianceFound = true;
                break; //we have executed our task, no need to go through for loop anymore
            }
        }

        if(!applianceFound) System.out.println("Person '"+name+"' couldn't find appliance to execute task '"+task+"'");
    }

    //called by House class during its timePasses loop. ArrayList is passed in by House object
    //method checks if person has any task set for current time and increments current time so that we keep track of time
    public  void timePasses(ArrayList<Appliance> appliances) throws Exception{
        currentTime++; //increment currentTime by one so we can keep track of how many 15mins have passed. Required as we need to know if currentTime has any tasks allocated

        String task = tasksToDo.get(currentTime);

        if(task != null){ //have found task allocated to this time!

            System.out.println(name+", a(n) "+this.getClass().getName().toLowerCase()+
                    " is attempting to execute task: "+task);

            executeTask(task, appliances);
        }
    }

    //called by Configurator class when creating Person object
    //method takes an already created hashMap and makes tasksToDo = this
    public void setTasks(HashMap<Integer, String> tasks){
        tasksToDo = tasks;
    }

    //returns Name of person object. Useful for debugging ie "Jeremy is too young to use cooker!"
     public String getName(){
        return name;
    }

}
