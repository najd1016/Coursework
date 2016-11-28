public class Main {

    private House myHouse;

    public static void main(String[] args){
        Main m = new Main();
        m.run(args[0]);
    }

    //Creates configurator which configures myHouse and then executes timePasses on myHouse until the system exits
    public void run(String argument){

        //house must be created/init before parsingConfig otherwise myHouse.addXYZ etc will return null pointers exceptions
        myHouse = new House();

        Configurator configurator = new Configurator();

        Tester tester = new Tester();
        tester.test();

        if(!argument.isEmpty()){

            //have to pass in myHouse variable so configurator can add appliances etc
            configurator.parseConfig(configurator.readConfig(argument), myHouse);

        }else{
            System.err.println("No config specified so simulation can't run!");
            System.exit(0);
        }

        while (true){
            try
            {
                Thread.sleep(100);
                myHouse.timePasses();
            }
            catch (InterruptedException e)
            {
                System.err.println("Simulation interrupted!");
                System.err.println(e.toString());
            }
        }
    }
}
