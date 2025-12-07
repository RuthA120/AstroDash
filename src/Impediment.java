public class Impediment {
    /*Attributes*/
    private int xLocation;
    private int yLocation;
    private int width;
    private int height;

    /*Constructor*/
    public Impediment(int xLocation, int yLocation, int width, int height)
    {
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.width = width;
        this.height = height;

    }
    public Impediment()
    {
        this.xLocation = 0;
        this.yLocation = 0;
        this.width = 0;
        this.height = 0;
    }

    public void runTests(){ // Check if negative
        assert xLocation >= 0;
        assert yLocation >= 0;
        assert width >= 0;
        assert height >= 0;
    }

    /*Getters and Setters*/
    public int getXLocation()
    {
        return xLocation;
    }
    public int getYLocation()
    {
        return yLocation;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }

    public void setWidth(int width){
        this.width = width;
    }
    public void setHeight(int height){
        this.height = height;
    }
    public void setXLocation(int xLocation)
    {
        this.xLocation = xLocation;
    }
    public void setYLocation(int yLocation)
    {
        this.yLocation = yLocation;
    }

    /*Testing Methods*/
    public String toString(){
        return "Impediment: " + xLocation + ", " + yLocation + "\n";
    }

}
