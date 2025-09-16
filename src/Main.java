//imports for graphics and linked lists
import java.awt.*;
import javax.swing.*;
import java.util.LinkedList;

//Main class must extend JPanel to work (makes Main a child of JPanel so it can to graphics stuff)
public class Main extends JPanel {
    // paint thread, used to draw stuff
    public PaintThread paintThread;

    // current instance of the CTF game
    public static Main instance;

    // graphics frame
    Frame frame;

    // these values are arbitrary, they just worked best for my LCD size, I plan on
    // having these changable, but it's not currently tested
    public static final int frameWidth = 1300; // TODO: School pc screens are 1366x768; be careful not to exceed this.
    public static final int frameHeight = 700;

    // this is basically an arraylist, but it's faster with linear access
    // used to draw stuff
    public static LinkedList<Sprite> renderedSprites = new LinkedList<Sprite>();

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

        // set properties of the frame
        frame.setVisible(true);
        frame.setSize(frameWidth, frameHeight);
        frame.add(instance);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 1st paint
        frame.repaint();
        Player.instantiateTeams();

        for (PlayerLogic p : PlayerLogic.yourTeam)
            System.out.println(p);
        for (PlayerLogic e : PlayerLogic.enemyTeam)
            System.out.println(e);
    }

    public void paintComponent(Graphics g) {
        // calls parent paintComponent, which cleans up the frame we are using
        super.paintComponent(g);

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
                if (instance != null)
                    instance.frame.repaint();

                try {
                    sleep(50);  // TODO: Why is the repaint interval 50 ms?
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
