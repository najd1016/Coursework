public class Main {

    private House myHouse;

    public static void main(String[] args){
        Main m = new Main();

        if(args.length > 0) {
            m.run(args[0]);
        }else{
            System.err.println("No config file specified");
            System.exit(0);
        }
    }

    //Creates configurator which configures myHouse and then executes timePasses on myHouse until the system exits
    private void run(String argument){

        Configurator configurator = new Configurator();

        Tester tester = new Tester();
        tester.test();

        if(!argument.isEmpty()){

            //house must be created/init before parsingConfig otherwise myHouse.addXYZ etc will return null pointers exceptions

            //have to pass in myHouse variable so configurator can add appliances etc
            myHouse = configurator.parseConfig(configurator.readConfig(argument));

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
