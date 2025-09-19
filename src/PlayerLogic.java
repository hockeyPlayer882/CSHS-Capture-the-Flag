/*
 * You will only be writing code in this class, DO NOT MESS WITH ANY OTHER CLASSES, if there's an issue that you believe is not related to your code, email 
 * Michael Middendorf and I will determine what the issue is.
 */
//I could be mean and force everyone to search through the Player code to find all the stuff you have access to, but becuase I'm feeling nice I'll list them here...
//Player properties:
//      X,Y
//      playerSpeed,playerSize (constant ints)
//      isMoving,isRed,hasFlag (all booleans, by default isMoving is false, so you have to set it to true if you want them to move, which they will do automatically)
//Player methods:
//  getRotation() gets the current rotation
//  setRotation() tells a player to point in a given direction
//  pointToSprite() points the player towards a given sprite
//Flag properties:
//      X,Y (all ints)
//      isCollected,isRed (constant ints)
//      size(constant int)
public class PlayerLogic extends Player {
    public static PlayerLogic[] yourTeam = new PlayerLogic[5];
    public static PlayerLogic[] enemyTeam = new PlayerLogic[5];

    /**
     * generates a player with logic
     * 
     * @param x         x position
     * @param y         y position
     * @param isRed     is it red or not?
     * @param direction starting rotation
     */
    public PlayerLogic(int x, int y, boolean isRed, int direction) {
        super(x, y, isRed, direction);
    }
    /**
     * Write your movement logic here....
     */
    /**
     * This update method is called every frame, use it
     * Keep in mind that move is already called every frame, so you can NOT use it here, since you will get your move speed doubled
     */
    public void Update(){
        //testing cases
        yourTeam[1].setRotation(30);
        //spin?
        yourTeam[0].isMoving = true;
        yourTeam[1].isMoving = true;
        yourTeam[2].isMoving = true;
        yourTeam[3].isMoving = true;
        //keep the last player static
        yourTeam[0].pointAtSprite(Flag.getEnemyFlag());
    }

}