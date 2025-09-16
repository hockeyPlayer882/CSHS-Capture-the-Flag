/*
 * You will only be writing code in this class, DO NOT MESS WITH ANY OTHER CLASSES, if there's an issue that you believe is not related to your code, email 
 * Michael Middendorf and I will determine what the issue is.
 */
public class PlayerLogic extends Player{
    public static PlayerLogic[] yourTeam = new PlayerLogic[5];
    public static PlayerLogic[] enemyTeam = new PlayerLogic[5];
    public static void instantiateTeams(){
        boolean youAreRed = Math.random()*2 == 0;
        for(int i = 0; i < yourTeam.length;i++){
            yourTeam[i] = new PlayerLogic(120, i*(Main.frameHeight/5),youAreRed,0);
            enemyTeam[i] = new PlayerLogic(Main.frameWidth-120, i*(Main.frameHeight/5),!youAreRed,180);
        }
    }
    public PlayerLogic(int x, int y, boolean isRed, int direction){
        super(x,y,isRed);
        rotation = direction;
    } 
    /**
     * Write your movement logic here....
     * 
     */
}