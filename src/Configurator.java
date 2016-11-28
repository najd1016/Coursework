import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class Configurator {

    private ArrayList<Appliance> appliancesToAdd;

    private ArrayList<Meter> metersToAdd;


    public Configurator(){
        appliancesToAdd = new ArrayList<>();
        metersToAdd = new ArrayList<>();
    }

    //takes argument from command line input which is taken in Main class
    //fetches file at specified address if it exists, if it doesn't exist simulation exits
    public ArrayList<String> readConfig(String filename){

        Path filePath = Paths.get(filename);
        File configText = new File(filePath.toUri());

        if(!configText.exists()){
            System.err.println("Unable to run: specified config file doesn't exist!");
            System.exit(0);
        }

        BufferedReader reader = null;
        ArrayList<String> config = new ArrayList<>();

        try {

            reader = new BufferedReader(new FileReader(configText));
            String text;

            while ((text = reader.readLine())  != null) {
                config.add(text.trim()); //trim to remove excess white space, if any, on the end of lines
            }

        } catch (IOException e) {

            e.printStackTrace();
            return null;

        } finally {

            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return config;
    }

    //takes String array, even if empty, and parses settings from that
    //defaults are assumed to be starting value for consumed of 0 and canGenerate = false
    private ArrayList<Object> parseMeterSettings(String[] inputtedSettings){

        ArrayList<Object> parsedArguments = new ArrayList<>();

        int startValue = 0;
        boolean generating = false;

        try {
            switch (inputtedSettings.length) { //in case 0: no changes occur so case not required as would be empty

                case 1:
                    startValue = Integer.parseInt(inputtedSettings[0]);

                    break;
                case 2:
                    startValue = Integer.parseInt(inputtedSettings[0]);
                    generating = Boolean.parseBoolean(inputtedSettings[1]);

                    break;
                default:
                    System.out.println("Inputted settings for meter had more variables than required, so resorted to defaults");

                    break;
            }

            if (startValue < 0) {
                System.out.println("Config tried to set meter with negative start value, so resorted to default for that value");
                startValue = 0;
            }

        }catch (Exception e){
            System.out.println(e.getMessage()+", so resorted to defaults for this meter");
            startValue = 0;
            generating = false;
        }

        parsedArguments.add(startValue);
        parsedArguments.add(generating);

        return parsedArguments;
    }

    //takes appliance name and returns an array of ints which are the default settings for that appliance
    //[0] in the array for normal appliances is the elec use, [1] = gas
    //[2] = water, [3] = use time. For generators [0] = amountGenerated and that's all.
    private Integer[] getDefaultApplianceSettings(String applianceName){
        Integer[] defaultSettings = null;

        switch (applianceName) {
            case "WashingMachine":
                defaultSettings = new Integer[] {2,0,1,8};
                break;
            case "Refrigerator":
                defaultSettings = new Integer[] {1,0,0,-1};
                break;
            case "Kettle":
                defaultSettings = new Integer[] {20,0,1,1};
                break;
            case "Boiler":
                defaultSettings = new Integer[] {0,1,0,0};
                break;
            case "Dishwasher":
                defaultSettings = new Integer[] {2,0,1,6};
                break;
            case "TV":
                defaultSettings = new Integer[] {1,0,0,0};
                break;
            case "NightLight":
                defaultSettings = new Integer[] {1,0,0,0};
                break;
            case "ElectricCooker":
                defaultSettings = new Integer[] {5,0,0,4};
                break;
            case "GasCooker":
                defaultSettings = new Integer[] {0,4,0,4};
                break;
            case "ElectricShower":
                defaultSettings = new Integer[] {12,0,4,1};
                break;
            case "WindTurbine":
                defaultSettings = new Integer[] {4};
                break;
            case "PowerShower":
                defaultSettings = new Integer[] {0,10,5,1};
                break;
        } //no default case required as if applianceName not recognised null will passed to the parse method and then to the createAppliance method
        //which will thus make a null object, which will be dealt with by the addApp etc method

        return defaultSettings;
    }

    //takes String array, even if empty, and parses settings from that by first getting the defaults
    //and then overwriting the defaults where applicable with the inputted settings. Appliance name req
    //so that we can get the default settings and also for debugging purposes
    private Integer[] parseApplianceSettings(String[] inputtedSettings, String applianceName){

        //get the default values so that they can be overwritten if necessary by the specified settings
        Integer[] parsedSettings = getDefaultApplianceSettings(applianceName);

        if (parsedSettings == null) {
            System.out.println("Syntax error in config, appliance: "+applianceName+" not a valid appliance");

        }else{

            try {
                switch (inputtedSettings.length) {
                    //assumptions for cases 1 and 2 are that they are in the correct order and start from electricityConsumption.
                    //as to which one was the gas usage, water usage etc. Therefore we limit the cases and have a default to catch any typos in the config
                    //case 0 required, even though it does nothing, as otherwise calls default and gives misleading error message

                    case 0:
                        break;
                    case 1:
                        parsedSettings[0] = Integer.parseInt(inputtedSettings[0]); //sets the assumed elecUse or amountGenerated variable

                        break;
                    case 2:
                        parsedSettings[0] = Integer.parseInt(inputtedSettings[0]); //sets the assumed elecUse variable
                        parsedSettings[1] = Integer.parseInt(inputtedSettings[1]); //sets the assumed gasUse variable since that's 2nd in the normal order

                        break;
                    case 3:
                        parsedSettings[0] = Integer.parseInt(inputtedSettings[0]); //sets the elecUse variable
                        parsedSettings[1] = Integer.parseInt(inputtedSettings[1]); //sets the gasUse variable
                        parsedSettings[2] = Integer.parseInt(inputtedSettings[2]); //sets the waterUse variable

                        break;

                    //returns defaults since inputtedSettings did not meet correct case
                    default:
                        System.out.println("Inputted settings for " + applianceName + " had more variables than required, so resorted to defaults");
                        parsedSettings = getDefaultApplianceSettings(applianceName);

                        break;
                }

                int variablesToCheck; //for normal appliances don't need to check polarity of timeOn, as refrigerator has value -1 since runs infinitely = would throw exception

                if (parsedSettings.length > 1) { //if normal appliance
                    variablesToCheck = 3;
                } else { //else must be a generator (wind turbine)
                    variablesToCheck = 1;
                }

                for (int index = 0; index < variablesToCheck; index++) {
                    if (parsedSettings[index] < 0) {
                        throw new Exception("Invalid syntax: one of the xyzUse or amountGenerated variables for an appliance was negative.");
                    }
                }


            } catch (Exception e) {
                System.out.println("Failed to parse one or more integers for " + applianceName + " settings, so resorted to defaults");
                parsedSettings = getDefaultApplianceSettings(applianceName);
            }
        }
        return parsedSettings;
    }

    //returns Meter which is the created meter. Meter can then be cast to correct type of Meter
    //and then passed to myHouse back in Main. Method uses parseMeterSettings to give Meter correct
    //starting values e.g. consumed = 0
    private Meter createMeter(String meterName, String[] meterSettings){

        ArrayList<Object> parsedSettings = parseMeterSettings(meterSettings);

        int startValue = (int) parsedSettings.get(0);
        boolean canGenerate = (boolean) parsedSettings.get(1);

        GasMeter gasMeter = null;
        WaterMeter waterMeter = null;
        ElectricMeter electricMeter = null;

        switch (meterName){
            case "ElectricMeter":
                electricMeter = new ElectricMeter(startValue,canGenerate);
                break;
            case "GasMeter":
                gasMeter = new GasMeter(startValue,canGenerate);
                break;
            case "WaterMeter":
                waterMeter = new WaterMeter(startValue,canGenerate);
                break;
        }

        if(waterMeter != null){
            return waterMeter;
        }else if(gasMeter != null){
            return gasMeter;
        }else if(electricMeter != null){
            return electricMeter;
        }else{
            System.out.println("All meters still null, so have to return null!");
            return null;
        }
    }

    //returns Appliance which is actually a specific appliance e.g. Boiler with the correct settings
    //which are taken from the config or else defaults are taken. Does this by using switch case statements
    //with appliance name to determine which type of Appliance to create
    private Appliance createAppliance(String applianceName, String[] applianceSettings){

        Integer[] parsedSettings = parseApplianceSettings(applianceSettings, applianceName);

        Appliance appliance = null;

        if(parsedSettings != null) {

            if(parsedSettings.length > 1) {

                int eConsump = parsedSettings[0];
                int gConsump = parsedSettings[1];
                int wConsump = parsedSettings[2];
                int timeOn   = parsedSettings[3];

                switch (applianceName) {
                    case "WashingMachine":
                        appliance = new WashingMachine(eConsump, gConsump, wConsump, timeOn);
                        break;
                    case "Refrigerator":
                        appliance = new Refrigerator(eConsump, gConsump, wConsump, timeOn);
                        break;
                    case "Kettle":
                        appliance = new Kettle(eConsump, gConsump, wConsump, timeOn);
                        break;
                    case "Boiler":
                        appliance = new Boiler(eConsump, gConsump, wConsump, timeOn);
                        break;
                    case "Dishwasher":
                        appliance = new Dishwasher(eConsump, gConsump, wConsump, timeOn);
                        break;
                    case "TV":
                        appliance = new TV(eConsump, gConsump, wConsump, timeOn);
                        break;
                    case "NightLight":
                        appliance = new NightLight(eConsump, gConsump, wConsump, timeOn);
                        break;
                    case "ElectricCooker":
                        appliance = new ElectricCooker(eConsump, gConsump, wConsump, timeOn);
                        break;
                    case "GasCooker":
                        appliance = new GasCooker(eConsump, gConsump, wConsump, timeOn);
                        break;
                    case "ElectricShower":
                        appliance = new ElectricShower(eConsump, gConsump, wConsump, timeOn);
                        break;
                    case "PowerShower":
                        appliance = new PowerShower(eConsump, gConsump, wConsump, timeOn);
                        break;
                } //no default case required as if appliance name not recognised, appliance will remain null and null will be caught in addAppOr etc method
            }else{
                int electricityGenerated = parsedSettings[0];

                appliance = new WindTurbine(electricityGenerated);
            }
        }
        return appliance;
    }

    //this is the method which determines from the current line of text in the config whether
    //we are trying to create a Meter or an Appliance and thus calls the correct createXYZ method
    //once the Appliance or Meter has been created it is added to the ArrayLists above
    private void addApplianceOrMeter(String text) throws Exception{

        System.out.println("Trying to add: "+text);

        String[] splitText;
        String[] settings;
        String name;

        splitText = text.split(":"); //split current line on : and return a string array of all the separate parts, since name is before : its index is 0
        //settings, if present, are after : so index is 1
        name = splitText[0];

        if(splitText.length == 2) { //contains a non blank string after : meaning must have settings of some sort

            settings = splitText[1].split(",");
            System.out.println("Settings: " + splitText[1]);

        }else{ //nothing following : therefore no settings specified, meaning parser will just pass in defaults
            settings = new String[0];
            System.out.println("No settings specified, so will be using default values for: "+name);
        }

        Object createdObject;

        if(name.endsWith("Meter")) {
            createdObject = createMeter(name, settings);
        }else{
            createdObject = createAppliance(name, settings);
        }

        if(createdObject != null) {
            if (createdObject instanceof Meter) {
                System.out.println("Created meter, so adding to ArrayList.");

                metersToAdd.add((Meter) createdObject);

            }else{
                System.out.println("Created something other than a meter, so assuming appliance and adding to ArrayList.");

                appliancesToAdd.add((Appliance) createdObject);
            }

        }else{
            System.out.println("Create appliance or meter method has failed and returned a null object for: "+text);
        }

    }

    //splits the inputted string to obtain the strings for name, age and gender
    //throws Exceptions if syntax is incorrect, for instance age is negative or
    //doesn't contain three inputs (missing name, age or gender)
    private void addPerson(ArrayList<String> personData, House myHouse) throws Exception{
        String name, gender;
        int age;

        //gets first line ie Person:x,y,z, splits on : and returns x,y,z
        String combinedData = personData.get(0).split(":")[1];
        String[] splitData = combinedData.split(",");

        if(splitData.length == 3) {

            name = splitData[0];
            age = Integer.parseInt(splitData[1]);
            gender = splitData[2];

            //remove first line, just leaving tasks
            personData.remove(0);

            if(age < 0) throw new Exception("Age of person "+name+" is less than 0, so they will not be added!");

            if(age >= 18){
                Adult adult = new Adult(age, name, gender);
                //isEmpty check stops parsePersonalTasks being called on empty list

                if(!personData.isEmpty()) adult.setTasks(parsePersonalTasks(personData));
                myHouse.addPerson(adult);

            }else{
                Child child = new Child(age, name, gender);

                //isEmpty check stops parsePersonalTasks being called on empty list
                if(!personData.isEmpty()) child.setTasks(parsePersonalTasks(personData));
                myHouse.addPerson(child);
            }

        }else{
            throw new Exception("Invalid syntax for person, so have not added: "+personData.get(0));
        }
    }

    //takes all the lines beneath a Person: declaration and parses the tasks
    //Parsing the tasks simply means separating the task and the time (multiple of 15 mins)
    //at which to execute
    private HashMap<Integer, String> parsePersonalTasks(ArrayList<String> tasks){

        HashMap<Integer, String> parsedTasks = new HashMap<>();

        for (String taskData: tasks){

            String[] splitData = taskData.split(":"); //separating task name and task time into individual strings

            if(splitData.length == 2){ //length = 2 when has task name and task time

                try {
                    parsedTasks.put(Integer.parseInt(splitData[1]), splitData[0]);

                }catch(NumberFormatException  e){
                    System.out.println("Failed to parse integer for time when adding task to person, please check this line for syntax errors: "+taskData);
                }

            }else{ //either doesn't contain : or has nothing following the : either way syntax error in config
                System.out.println("Invalid syntax in config on line: "+taskData+" meaning can't add the task");
            }
        }

        return parsedTasks;
    }

    //takes output from readConfig, detects Person, House etc and calls according methods
    //to create appliances, meters and people. Also catches config errors like having 0 appliances
    //or having less than 3 meters.
    public void parseConfig(ArrayList<String> input, House myHouse) {
        try {
            //essential that it should contain House: so we know where to look for appliances and meters
            if (input.contains("House:")) {

                for (String line : input) {

                    //next lines should be appliances
                    if (line.startsWith("House:")) {

                        //index in arrayList of the line following/below "House:"
                        int index = input.indexOf(line) + 1;

                        //following lines will be appliances until Person: called or file ends (unless invalid conf)
                        for (int i = index; i < input.size(); i++) {

                            String currentLine = input.get(i);

                            if (!currentLine.startsWith("Person:")) {

                                //since not person, must be appliance or meter since has come after House:
                                addApplianceOrMeter(currentLine);

                            } else {

                                if (appliancesToAdd.size() == 0) {
                                    System.err.println("Config file is invalid: house doesn't contain any appliances!");
                                    System.exit(0);
                                }


                                if(metersToAdd.size() < 3){
                                    System.err.println((3-metersToAdd.size())+" meters missing from config. " +
                                            "Whilst those missing may be non-essential, still adding the missing meters");

                                    addMissingMeters();
                                }

                                addArraysToHouse(myHouse);

                                //exit this for loop so it can continue with foreach loop
                                break;
                            }
                        }

                        //next lines should be tasks + info about person
                    } else if (line.startsWith("Person:")) {

                        //index in arrayList of the next line, but we add the current line so we can get data for name age etc
                        int index = input.indexOf(line) + 1;

                        ArrayList<String> personData = new ArrayList<>();

                        personData.add(line);

                        //following lines will be appliances until another Person: called or file ends (unless invalid conf)
                        for (int i = index; i < input.size(); i++) {

                            String currentLine = input.get(i);

                            //keep adding tasks until we reach next person
                            if (!currentLine.startsWith("Person:")) {
                                personData.add(currentLine);
                            } else {
                                addPerson(personData, myHouse);

                                //exit this for loop so it can continue with foreach loop
                                break;
                            }

                            //reached end of file, no need to break for loop as its about to end anyway
                            if (i == (input.size() - 1)) {
                                addPerson(personData, myHouse);
                            }
                        }
                    }
                }
            } else {
                throw new Exception("Config file has invalid syntax: doesn't contain 'House:'!");
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    //use foreach loops to add all elements in both ArrayList, adding the meters first as the house addAppliance
    //attaches the meters to the appliances so the meters cannot be null/must be instantiated first
    private void addArraysToHouse(House myHouse){

        for (Meter meter: metersToAdd) {
            myHouse.addMeter(meter);
        }

        for (Appliance appliance: appliancesToAdd) {
            try {
                myHouse.addAppliance(appliance);
            } catch (Exception e) {
                System.err.println("Failed to add appliance: "+e.getMessage());
            }
        }
    }

    //adds missing meters to ArrayList so that we don't get any un-needed null pointer exceptions
    private void addMissingMeters(){
        boolean electric = false, gas = false
                , water = false;

        for (Meter meter: metersToAdd) {
            if(meter instanceof ElectricMeter) electric = true;
            if(meter instanceof GasMeter) gas = true;
            if(meter instanceof WaterMeter) water = true;
        }

        if(!electric){
            metersToAdd.add(new ElectricMeter(0,false));
            System.out.println("Electric meter had to be added");
        }

        if(!gas){
            metersToAdd.add(new GasMeter(0,false));
            System.out.println("Gas meter had to be added");
        }

        if(!water){
            metersToAdd.add(new WaterMeter(0,false));
            System.out.println("Water meter had to be added");
        }


    }

}
