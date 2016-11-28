public class WindTurbine extends Appliance {

    public WindTurbine(int electricityGenerated) {
        super(electricityGenerated);
        setCurrentState(true);
    }

    @Override
    public void use(boolean state, Person person) throws Exception {

    }
}
