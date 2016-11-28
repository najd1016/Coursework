public class WindTurbine extends Appliance {

    //constructor sets the amount of electricity generated and permanently turns on the wind turbine
    public WindTurbine(int electricityGenerated) {
        super(electricityGenerated);
        setCurrentState(true);
    }

    @Override
    public void use(boolean state, Person person) throws Exception {

    }
}
