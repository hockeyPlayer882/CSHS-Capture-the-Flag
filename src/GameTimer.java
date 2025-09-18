public class GameTimer extends Thread {
    boolean hasStarted;
    boolean isPaused;
    Main instance;

    public GameTimer(Main instance) {
        hasStarted = false;
        isPaused = false;
        this.instance = instance;
    }

    @Override
    public void run() {
        System.out.println("game thread started");

        while (true) {

            for (int i = 0; i < PlayerLogic.yourTeam.length; i++) {
                PlayerLogic.yourTeam[i].move();
                PlayerLogic.enemyTeam[i].move();
                PlayerLogic.yourTeam[i].Update();
                PlayerLogic.yourTeam[i].Update();
            }

            try {
                // TODO: Might need to revert to FPS / 2 tho that's technically incorrect.
                sleep(1000 / Timer.FPS); // FPS is the reciprocal of the delta time, which is in seconds.
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}