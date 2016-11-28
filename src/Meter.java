public abstract class Meter {

    private int consumed;
    private int generated;
    private boolean generating;

    public Meter(int startingValueConsumed, boolean canGenerate){
        consumed = startingValueConsumed;
        generating = canGenerate;
    }

    public void incrementConsumed(){
        consumed++;
    }

    public void incrementConsumed(int amountToIncrement) throws Exception{
        if(amountToIncrement >= 0) {
            consumed += amountToIncrement;
        }else{
            throw new Exception("Attempted to increment consumed on "+getType()+" meter by a negative amount!");
        }
    }

    public void incrementGenerated() throws Exception{
        if(canGenerate()) {
            generated ++;
        }else{
            throw new Exception("This "+getType()+" meter cannot generate!");
        }
    }

    public void incrementGenerated(int amountToIncrement) throws Exception{
        if(canGenerate()) {
            generated += amountToIncrement;
        }else{
            throw new Exception("This "+getType()+" meter cannot generate!");
        }
    }

    public Boolean canGenerate(){
        return generating;
    }

    public Integer getConsumed(){
        return consumed;
    }

    public Integer getGenerated(){
        return generated;
    }

    abstract String getType();

}
