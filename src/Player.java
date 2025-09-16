import java.awt.Color;
import java.awt.Graphics;

public class Player extends Sprite {
    public static final Color redColor = new Color(255, 0, 0);
    public static final Color blueColor = new Color(0, 0, 255);
    public static final int playerSize = 20;

    // for the sake of simplicity, degrees will be for rotation (360), by default
    // for trig, radians are used, but radians are more confusing to use and
    // conversions exist if needed
    public int rotation = 0;
    public boolean hasFlag = false;
    boolean isRed;

    public Player(int x, int y, boolean isRed, int direction) {
        // calls the sprite constructor
        super(false, isRed ? redColor : blueColor, x, y, playerSize, "null");
        hasFlag = false;
        this.isRed = isRed;
        this.rotation = direction;
    }

    /**
     * Calculates the x points of the triangle
     * 
     * @return the 3 x-values of the points
     */
    private int[] calculateXPoints() {
        int[] Xpoints = new int[3];
        // formula: 1st point is where the player is facing, so it should be
        // size*cos(angle)+x
        Xpoints[0] = (int)(size * Math.cos(Math.toRadians(rotation + 270))) + x;
        Xpoints[1] = (int)(size * Math.cos(Math.toRadians(rotation))) + x;
        Xpoints[2] = (int)(size * Math.cos(Math.toRadians(rotation + 90))) + x;
        return Xpoints;
    }

    /**
     * Calculates the y points of the triangle
     * 
     * @return the 3 y-values of the points
     */
    private int[] calculateYPoints() {
        int[] Ypoints = new int[3];
        Ypoints[0] = (int)(size * Math.sin(Math.toRadians(rotation + 270))) + y;
        Ypoints[1] = (int)(size * Math.sin(Math.toRadians(rotation))) + y;
        Ypoints[2] = (int)(size * Math.sin(Math.toRadians(rotation + 90))) + y;
        return Ypoints;
    }

    @Override
    protected void draw(Graphics g) {
        g.fillPolygon(calculateXPoints(), calculateYPoints(), 3);
    }

    public String toString() {
        String returnR = ""; 

        returnR += "Player\n\t";
        returnR += "Color: " + (isRed ? "Red" : "Blue") + "\n\t";
        returnR += "Rotation:" + rotation + "\n\t";
        returnR += "Points:\n\t\t";

        for (int i = 0; i < calculateXPoints().length; i++) {
            returnR += calculateXPoints()[i] + "," + calculateYPoints()[i] + "\n\t\t";
        }

        return returnR;
    }
    
    public static void instantiateTeams(){
        boolean youAreRed = (int)(Math.random() * 2) == 0;
        
        // Separate players by a fifth of the frame resolution.
        int playerDelta = Main.frameHeight / 5;
        
        for(int i = 0; i < PlayerLogic.yourTeam.length;i++) {
            PlayerLogic.yourTeam[i] = new PlayerLogic(120, i * playerDelta + playerDelta / 2, youAreRed, 90);
            PlayerLogic.enemyTeam[i] = new PlayerLogic(Main.frameWidth - 120, i * playerDelta + playerDelta / 2, !youAreRed, 180);
        }
    }
}