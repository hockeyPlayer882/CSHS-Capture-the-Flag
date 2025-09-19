import java.awt.Color;
import java.awt.Graphics;

public class Player extends Sprite {
    public static final Color redColor = new Color(255, 0, 0);
    public static final Color blueColor = new Color(0, 0, 255);
    public static final int playerSize = 20;
    // speed in pixels/frame (60FPS I think)
    public static final int playerSpeed = 5;
    public boolean isMoving = false;
    // for the sake of simplicity, degrees will be for rotation (360), by default
    // for trig, radians are used, but radians are more confusing to use and
    // conversions exist if needed
    private int rotation = 0;

    // for smooth rotation
    private int setRotation = 0;

    // how fast should they turn?
    private int rotationStep = 5;
    public boolean hasFlag = false;
    boolean isRed;

    /**
     * constructs the player
     * 
     * @param x         x position
     * @param y         y position
     * @param isRed     is it red or not
     * @param direction starting rotation (in degrees)
     */
    public Player(int x, int y, boolean isRed, int direction) {
        // calls the sprite constructor
        super(false, isRed ? redColor : blueColor, x, y, playerSize, "null");
        hasFlag = false;
        this.isRed = isRed;
        this.rotation = direction;
        // all Players start not moving
        isMoving = false;
    }

    /**
     * Moves the player in it's specified direction
     */
    public void move() {
        if (isMoving) {
            x += playerSpeed * Math.cos(Math.toRadians(rotation));
            y += playerSpeed * Math.sin(Math.toRadians(rotation));
        }
    }
    public int getRotation(){
        return rotation;
    }
    /**
     * tells the program to point to a specified new angle (again in degrees)
     * 
     * @param newAngle the new angle you want the player to point to
     */
    public void setRotation(int newAngle) {
        rotation = newAngle;
    }
    public void pointAtSprite(Sprite other){
        //if they are on the same x-axis, point straight up or down
        if(x == other.x){
            setRotation(y > other.y ? 90:270);
            return;
        }
        //if they are on the same y-axis, point straight left or right
        if(y == other.y){
            setRotation(x > other.x ? 180:0);
            return;
        }

        System.out.println("x: " + x + "y: " + y);
        System.out.println("otherX: " + other.x + ". otherY: " + other.y);

        // Avoid integer division edge case.
        double theta = Math.toDegrees(Math.atan((other.y - y) / (float)(other.x - x)));
        System.out.println(theta);
        setRotation((int)theta);
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
        Xpoints[0] = (int) (size * Math.cos(Math.toRadians(rotation + 270))) + x;
        Xpoints[1] = (int) (size * Math.cos(Math.toRadians(rotation))) + x;
        Xpoints[2] = (int) (size * Math.cos(Math.toRadians(rotation + 90))) + x;
        return Xpoints;
    }

    /**
     * Calculates the y points of the triangle
     * 
     * @return the 3 y-values of the points
     */
    private int[] calculateYPoints() {
        int[] Ypoints = new int[3];
        Ypoints[0] = (int) (size * Math.sin(Math.toRadians(rotation + 270))) + y;
        Ypoints[1] = (int) (size * Math.sin(Math.toRadians(rotation))) + y;
        Ypoints[2] = (int) (size * Math.sin(Math.toRadians(rotation + 90))) + y;
        return Ypoints;
    }

    /**
     * Draws the player as a triangle
     */
    @Override
    protected void draw(Graphics g) {
        g.fillPolygon(calculateXPoints(), calculateYPoints(), 3);
    }

    /**
     * shows the various properties of the player (rotation,color, and position) in
     * a string format
     * 
     * @return a string representation of the player with various properties
     */
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

    /**
     * Instantiated the teams, will be called in the main method on startup
     */
    public static void instantiateTeams() {
        boolean youAreRed = (int) (Math.random() * 2) == 0;
        // Separate players by a fifth of the frame resolution.
        final int playerDelta = Main.frameHeight / 5;

        for (int i = 0; i < PlayerLogic.yourTeam.length; i++) {
            PlayerLogic.yourTeam[i] = new PlayerLogic(120, i * playerDelta + playerDelta / 2, youAreRed, 0);
            PlayerLogic.enemyTeam[i] = new PlayerLogic(Main.frameWidth - 120, i * playerDelta + playerDelta / 2,
                    !youAreRed, 180);
        }
        //moves the flags to the correct position
        final int offset = 50;
        if(youAreRed){
            Main.redFlag.x =offset;
            Main.blueFlag.x = Main.frameWidth-offset; 
        }
        else{

            Main.blueFlag.x =offset;
            Main.redFlag.x = Main.frameWidth-offset; 
        }
    }

}