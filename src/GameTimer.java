public class GameTimer extends Thread {
    boolean hasStarted;
    boolean isPaused;
    Main instance;
    public static boolean overtime = false;

    private long current_frame_start = System.nanoTime();

    public GameTimer(Main instance) {
        hasStarted = false;
        isPaused = false;
        this.instance = instance;

    }

    @Override
    public void run() {
        System.out.println("Game starting");

        // render thread needs time to figure stuff out, add delay to give it time
        Utils.sleep(500);

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

                if (!overtime)
                    break;

                // NOTE: Collapsed else { if () { }} to else if {}
                else if (Main.yourScore != Main.EnemyScore)
                    break;
            }

            // flag updates
            Main.redFlag.Update();
            Main.blueFlag.Update();
            if (Main.yourScore != Main.EnemyScore){
                instance.gameTimer.isDone = true;
                break;
            }

            // Replaced the old sleep function with a more robust sleeping system.
            // This allows the frame to take as much time as it needs (up to its quantum, 16 ms)
            // and still maintain a full 60 FPS.
            Utils.adaptiveFrameSleep(1000 / (Timer.FPS * Timer.timeScalar), current_frame_start);
            this.current_frame_start = System.nanoTime();            
        }
        if (instance.gameTimer.isDone) {
            if (Main.yourScore == Main.EnemyScore) {
                Timer.timeScalar = 2;

                overtime = true;
                instance.gameTimer.resetTimer();

                // TODO: Explain like I'm 5 why this recursive call is necessary.
                //recursion+iteration=fun!
                run();
            }
        }

    }

}