import java.awt.Color;

//flag is a sprite (If you're curious, this is inheritance, basically it makes flag keep all of the )
public class Flag extends Sprite {
    public static final int size = PlayerLogic.playerSize;

    public Flag(int x, int y, boolean isRed) {
        // sets all the properties for Sprite
        super(true, new Color(0, 0, 0), x, y, size, "Flag");
    }
}