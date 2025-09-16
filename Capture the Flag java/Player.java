import java.awt.Color;
import java.awt.Graphics;
public class Player extends Sprite{
    public static final Color redColor = new Color(255,0,0);
    public static final Color blueColor = new Color(0,0,255);
    public static final int playerSize = 20;
    //for the sake of simplicity, degrees will be for rotation (360), by default for trig, radians are used, but radians are more confusing to use and conversions exist if needed
    public static int rotation = 0;
    public boolean hasFlag = false;
    boolean isRed;
    public Player(int x, int y,boolean isRed){
        //calls the sprite constructor
        super(false,isRed?redColor:blueColor,x,y,playerSize,"null");
        hasFlag = false;
        this.isRed=isRed;
    }
    /**
     * Calculates the x points of the triangle
     * @return the 3 x-values of the points
     */
    private int[] calculateXPoints(){
        int[] Xpoints = new int[3];
        //formula: 1st point is where the player is facing, so it should be size*cos(angle)+x
        Xpoints[0] = (int)(size*Math.cos(Math.toRadians(rotation)))+x;
        Xpoints[1] = (int)(size*Math.cos(Math.toRadians(rotation+90)))+x;
        Xpoints[2] = (int)(size*Math.cos(Math.toRadians(rotation+180)))+x;
        return Xpoints;
    }
    /**
     * Calculates the y points of the triangle
     * @return the 3 y-values of the points
     */
    private int[] calculateYPoints(){
        int[] Ypoints = new int[3];
        Ypoints[0] = (int)(size*Math.sin(Math.toRadians(rotation)))+x;
        Ypoints[1] = (int)(size*Math.sin(Math.toRadians(rotation+90)))+x;
        Ypoints[2] = (int)(size*Math.sin(Math.toRadians(rotation+180)))+x;
        return Ypoints;
    }
    @Override
    protected void draw(Graphics g){
        
        g.fillPolygon(calculateXPoints(), calculateYPoints(), 3);
    }
    public String toString(){
        String returnR = new String();
        returnR += "Player\n\t";
        returnR += "Color: " + (isRed ? "Red":"Blue") + "\n\t";
        returnR += "Points:\n\t\t";
        for(int i = 0; i < calculateXPoints().length;i++){
            returnR += calculateXPoints()[i] + "," + calculateYPoints()[i] + "\n\t\t";
        }
        return returnR;
    }
}