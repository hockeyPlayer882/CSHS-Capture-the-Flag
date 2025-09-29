import java.awt.Color;
import java.awt.Graphics;

public class Player extends Sprite {
    //ik I could use Color.red and Color.blue, but this also works
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
    public boolean hasFlag = false;
    boolean isRed;
    Timer capturedTimer;
    public boolean isCaptured = false;

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
        capturedTimer = new Timer(0, 5, 0, 5);
        capturedTimer.isDone = true;
        capturedTimer.t.start();
    }

    /**
     * Moves the player in it's specified direction
     */
    public void move() {

        if (isMoving && !isCaptured) {
            x += playerSpeed * Math.cos(Math.toRadians(rotation));
            y += playerSpeed * Math.sin(Math.toRadians(rotation));
        }
        if (isCaptured && capturedTimer.isDone) {
            isCaptured = false;
            final int playerDelta = Main.frameHeight / 5;
            if (PlayerLogic.yourTeam[0].isRed == isRed) {
                for (int i = 0; i < PlayerLogic.yourTeam.length; i++) {
                    if (PlayerLogic.yourTeam[i].equals(this)) {
                        x = 120;
                        y = i * playerDelta + playerDelta / 2;
                    }
                }
            } else {
                for (int i = 0; i < PlayerLogic.enemyTeam.length; i++) {
                    if (PlayerLogic.enemyTeam[i].equals(this)) {
                        x = Main.frameWidth-120;
                        y = i * playerDelta + playerDelta / 2;
                    }
                }
            }
        }
        capturePlayer();
    }

    public int getRotation() {
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

    public void pointAtSprite(Sprite other) {
        /*
         * //if they are on the same x-axis, point straight up or down
         * if(x == other.x){
         * setRotation(y > other.y ? 90:270);
         * return;
         * }
         * //if they are on the same y-axis, point straight left or right
         * if(y == other.y){
         * setRotation(x > other.x ? 180:0);
         * return;
         * }
         */
        double theta = Math.toDegrees(Math.atan2((other.cy() - cy()), (double) (other.cx() - cx())));
        setRotation((int) theta);
    }

    public void capturePlayer() {
        for (int i = 0; i < PlayerLogic.yourTeam.length; i++) {
            // if this player is on the enemy team....
            if (isRed != PlayerLogic.yourTeam[i].isRed) {
                if (collide(PlayerLogic.yourTeam[i]) && cx() > Main.frameWidth / 2) {
                    PlayerLogic.yourTeam[i].capturedTimer.resetTimer();
                    PlayerLogic.yourTeam[i].capturedTimer.t.start();
                    PlayerLogic.yourTeam[i].hasFlag = false;
                    // bye bye player
                    PlayerLogic.yourTeam[i].x = 999;
                    PlayerLogic.yourTeam[i].y = 999;
                    PlayerLogic.yourTeam[i].isCaptured = true;
                    if(Flag.getEnemyFlag().collectedPlayer != null)
                        Flag.getEnemyFlag().resetFlag();
                }
            } else {
                if (collide(PlayerLogic.enemyTeam[i]) && cx() < Main.frameWidth / 2) {
                    PlayerLogic.enemyTeam[i].capturedTimer.resetTimer();
                    PlayerLogic.enemyTeam[i].hasFlag = false;
                    // bye bye player
                    PlayerLogic.enemyTeam[i].x = 999;
                    PlayerLogic.enemyTeam[i].y = 999;
                    PlayerLogic.enemyTeam[i].isCaptured = true;
                    if(Flag.getYourFlag().collectedPlayer != null)
                        Flag.getYourFlag().resetFlag();
                }
            }
        }
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

    public int cx() {
        return x;
    }

    public int cy() {
        return y;
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
        Main.blueFlag.resetFlag();
        Main.redFlag.resetFlag();
    }

}