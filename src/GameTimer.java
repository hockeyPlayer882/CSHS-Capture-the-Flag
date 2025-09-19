public class GameTimer extends Thread{
    boolean hasStarted;
    boolean isPaused;
    Main instance;
    public GameTimer(Main instance){
        hasStarted = false;
        isPaused = false;
        this.instance=instance;
    }
    @Override
    public void run(){
        System.out.println("game thread started");
        while(true){
            //player updates
            for(int i = 0; i < PlayerLogic.yourTeam.length;i++){
                PlayerLogic.yourTeam[i].move();
                PlayerLogic.enemyTeam[i].move();
                PlayerLogic.yourTeam[i].Update();
                PlayerLogic.yourTeam[i].Update();
            }
            //flag updates
            Main.redFlag.Update();
            Main.blueFlag.Update();
            //set framerate
            try {
                sleep(Timer.FPS/2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}