public class GameTimer extends Thread {
    boolean hasStarted;
    boolean isPaused;
    Main instance;
    public static boolean overtime = false;

    public GameTimer(Main instance) {
        hasStarted = false;
        isPaused = false;
        this.instance = instance;

    }

    @Override
    public void run() {
        System.out.println("Game starting");
        // render thread needs time to figure stuff out, add delay to give it time
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("game thread started");

        while (!instance.gameTimer.isDone) {
            //make overtime run twice as fast
            for (int doubleCode = 0; doubleCode < 2; doubleCode++) {
                for (int i = 0; i < PlayerLogic.yourTeam.length; i++) {
                    PlayerLogic.yourTeam[i].move();
                    PlayerLogic.enemyTeam[i].move();
                    PlayerLogic.enemyTeam[i].Update();
                    PlayerLogic.yourTeam[i].Update();
                }
                if(!overtime){
                    break;
                }
                else{
                    if(Main.yourScore != Main.EnemyScore){
                        break;
                    }
                }
            }
            // flag updates
            Main.redFlag.Update();
            Main.blueFlag.Update();
            if(Main.yourScore != Main.EnemyScore){
                instance.gameTimer.isDone = true;
                break;
            }
            // set framerate
            try {
                sleep(1000 / Timer.FPS); // FPS is the reciprocal of the delta time, which is in seconds.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (instance.gameTimer.isDone) {
            if (Main.yourScore == Main.EnemyScore) {
                if(!overtime)
                    Timer.FPS*=2;
                overtime = true;
                instance.gameTimer.resetTimer();
                //recursion+iteration=fun!
                run();
                
            }
        }

    }

}