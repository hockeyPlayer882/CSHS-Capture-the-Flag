//imports for graphics and linked lists
import java.awt.*;
import javax.swing.*;
import java.util.LinkedList;

//Main class must extend JPanel to work (makes Main a child of JPanel so it can to graphics stuff)
public class Main extends JPanel {
    public static int redScore = 0;
    public static int blueScore = 0;
    //time elapsed
    Timer gameTimer = new Timer(0,2,0,2);
    // paint thread, used to draw stuff
    public PaintThread paintThread;

    // current instance of the CTF game
    public static Main instance;
    // graphics frame
    Frame frame;

    // these values are arbitrary, they just worked best for my LCD size, I plan on
    // having these changable, but it's not currently tested
    public static final int frameWidth = 1300; // WARN: School pc screens are 1366x768; be careful not to exceed this.
    public static final int frameHeight = 700;

    // this is basically an arraylist, but it's faster with linear access
    // used to draw stuff
    public static LinkedList<Sprite> renderedSprites = new LinkedList<Sprite>();
    //flags (x position to be determined cuz colors are randomized)
    public static Flag blueFlag = new Flag(0, frameHeight/2, false);
    public static Flag redFlag = new Flag(0, frameHeight/2, true);

    // constructor
    public Main(JFrame frame) {
        this.frame = frame;
        paintThread = new PaintThread(this);
    }

    // main method
    public static void main(String[] args) {
        // create a new JFrame and give it a title
        JFrame frame = new JFrame("CSHS Capture the Flag");
        // create current instance
        instance = new Main(frame);
        GameTimer t = new GameTimer(instance);

        // set properties of the frame
        frame.setVisible(true);
        frame.setSize(frameWidth, frameHeight);
        frame.add(instance);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 1st paint
        frame.repaint();
        Player.instantiateTeams();
        instance.paintThread.start();
        t.start();
    }

    public void paintComponent(Graphics g) {
        // calls parent paintComponent, which cleans up the frame we are using
        super.paintComponent(g);
        //background (left side team)
        g.setColor(new Color(PlayerLogic.yourTeam[0].color.getRed(),PlayerLogic.yourTeam[0].color.getGreen(),PlayerLogic.yourTeam[0].color.getBlue(),128));
        g.fillRect(0,0,frameWidth/2,frameHeight);
        g.setColor(new Color(PlayerLogic.yourTeam[0].color.getRed(),PlayerLogic.yourTeam[0].color.getGreen(),PlayerLogic.yourTeam[0].color.getBlue(),128+(128/2)));
        g.fillRect(0,0,frameWidth/12,frameHeight);
        //background (right side team)
        g.setColor(new Color(PlayerLogic.enemyTeam[0].color.getRed(),PlayerLogic.enemyTeam[0].color.getGreen(),PlayerLogic.enemyTeam[0].color.getBlue(),128));
        g.fillRect(frameWidth/2,0,frameWidth/2,frameHeight);
        g.setColor(new Color(PlayerLogic.enemyTeam[0].color.getRed(),PlayerLogic.enemyTeam[0].color.getGreen(),PlayerLogic.enemyTeam[0].color.getBlue(),128+(128/2)));
        g.fillRect(frameWidth-frameWidth/12,0,frameWidth/12,frameHeight);

        // iterate through linkedlist (easy with enhanced for, weird without)
        for (Sprite s : renderedSprites) {
            // take care of painting stuff (see Sprite for how paint works)
            s.paint(g, this);
        }
    }

    public class PaintThread extends Thread {
        Main instance;

        public PaintThread(Main instance) {
            this.instance = instance;
        }

        @Override
        public void run() {
            System.out.println("program started");

            while (true) {
                //no limits on render framerate
                if (instance != null)
                    instance.frame.repaint();
            }
        }
    }
}
