//flag is a sprite (If you're curious, this is inheritance, basically it makes flag keep all of the )
public class Flag extends Sprite {
    public static final int size = PlayerLogic.playerSize;
    public boolean isCollected = false;
    public boolean isRed;
    public boolean isDropped = false;
    //3 seconds to collect flag after it's dropped
    public int MResetTimer = 180;
    //if 0, flag is reset
    public Timer resetTimer = new Timer(0,3,0,3);
    public PlayerLogic collectedPlayer;
    /**
     * Constructs a flag object
     * @param x x position
     * @param y y position
     * @param isRed is it red or not?
     */
    public Flag(int x, int y, boolean isRed) {
        // sets all the properties for Sprite
        super(true, isRed ? Player.redColor : Player.blueColor, x, y, size, isRed ? "redFlag":"blueFlag");
        //nobody can collect the flag at the start
        isCollected = false;
        this.isRed = isRed;
    }
    /**
     * updates the flag, called every frame in the main thread
     */
    public void Update(){
        if(collectedPlayer != null){
            x = collectedPlayer.x;
            y = collectedPlayer.y;
        }
        //if the flag hasn't been collected, check if it will be collected
        if(!isCollected)
            checkCollected();
    }
    /**
     * checks if enemy team collected a flag, sets the flag isCollected if it is
     * @return true if they collected it, false otherwise
     */
    public boolean checkCollected(){
        //if the flag was collected before, that doesn't mean it's collected now...
        isCollected = false;
        //figure out who's flag current flag is
        if(PlayerLogic.enemyTeam[0].isRed != isRed)
            //if it's your flag.... loop through each player on the enemies' team and check for a collision
            for(int i = 0; i < PlayerLogic.enemyTeam.length;i++){
                if(PlayerLogic.enemyTeam[i].collide(this)){
                    isCollected = true;
                    collectedPlayer = PlayerLogic.enemyTeam[i];
                    collectedPlayer.hasFlag = true;
                }
            }
        else
            //if it's the enemies' teams flag... loop through each player on your team and check for a collision
            for(int i = 0; i < PlayerLogic.yourTeam.length;i++){
                if(PlayerLogic.yourTeam[i].collide(this)){
                    isCollected = true;
                    collectedPlayer = PlayerLogic.yourTeam[i];
                    collectedPlayer.hasFlag = true;
                }
            }
        return isCollected;
        
    }
    public static Flag getYourFlag(){
        if(PlayerLogic.yourTeam[0].isRed){
            return Main.redFlag;
        }
        else return Main.blueFlag;
    }
    public static Flag getEnemyFlag(){
        if(PlayerLogic.enemyTeam[0].isRed){
            return Main.redFlag;
        }
        else return Main.blueFlag;
    }
    public String toString(){
        String returnR = "";

        returnR += "Player\n\t";
        returnR += "Color: " + (isRed ? "Red" : "Blue") + "\n\t";
        returnR += "Collected?:" + isCollected + "\n\t";
        returnR += "Center: (" + cx() + "," + cy() + ")";

        return returnR;
    }
}