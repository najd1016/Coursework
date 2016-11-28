public class Tester {

    public void test(){
        House myHouse = new House();

        try {
            myHouse.addAppliance(null);
            System.out.println("[Fail] Doesn't handle null appliance correctly");
        } catch (Exception e) {
            System.out.println("[Pass] Handles null appliance correctly");
        }

        if(myHouse.numAppliances() == 0) System.out.println("[Pass] Can count appliances correctly");

        try {
            for (int i = 0; i < 26; i++) {
                myHouse.addAppliance(new Dishwasher(0, 0, 0, 0));
            }
            System.out.println("[Fail] Allows adding of too many appliances");
        }catch (Exception e){
            System.out.println("[Pass] Handles adding too many appliances correctly");
        }

        if(myHouse.numAppliances() == 25) {
            System.out.println("[Pass] Can add appliances correctly");
        }else{
            System.out.println("[Fail] Doesn't add appliances correctly");
        }

        Dishwasher dishwasher = new Dishwasher(0, 0, 0, 0);
        myHouse = new House();

        for (int i = 0; i < 25; i++) {
            try {
                myHouse.addAppliance(dishwasher);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < 25; i++) {
            myHouse.removeAppliance(dishwasher);
        }

        if(myHouse.numAppliances() == 0){
            System.out.println("[Pass] Can remove appliances correctly");
        }else{
            System.out.println("[Fail] Failed to remove appliances correctly");
        }

        //todo add tests for people, meters, passing in negative values for appliances?

    }
}
