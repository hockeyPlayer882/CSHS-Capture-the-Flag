import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Color;
public class Sprite{
    //images are rendered differently from polygons (IE: traingles)
    BufferedImage image;
    boolean isImage;
    String polygon;
    Color color;
    int x;
    int y;
    int size;
    /**
     * constructor for the sprite
     * @param isImage true if sprite should be rendered as an image, false otherwise
     * @param c the color of the object you want to render (not used for images)
     * @param x x position of the sprite
     * @param y y position of the sprite
     * @param size size of the sprite (rectangular)
     * @param imageName name of the image in the Images folder (null for non-images)
     */
    public Sprite(boolean isImage, Color c, int x, int y, int size, String imageName){
        this.isImage = isImage;
        if(isImage){
            //gets the image from the files
            //ImageIO code must be sorrounded by a try/catch block, it tries the code in the try, if it fails, it goes to the catch part
            try {
                //get the path to the image (imageName must match the name in the Images folder)
                image = ImageIO.read(new File("./Images" + imageName));
            } catch (IOException e) {
                System.out.println("File with name: " + imageName + "does not exist in the images folder");
                e.printStackTrace();
            }
        }
        color = c;
        this.x = x;
        this.y = y; 
        this.size = size;
        //adds the sprite to the linkedList
        Main.renderedSprites.add(this);
    }    
    /**
     * Uses the prebuilt Rectangle class to check if an object collides with another
     * @param other the other sprite being collided with
     * @return true if they collided, false otherwise
     */
    public boolean collide(Sprite other){
        Rectangle thisR = new Rectangle(x,y,size,size);
        Rectangle otherR = new Rectangle(x, y, size, size);
        return thisR.intersects(otherR);
    }
    /**
     * paints a given sprite
     * @param g the graphics window
     * @param m the main instance
     */
    public void paint(Graphics g, Main m){
        g.setColor(color);
        if(isImage){
            draw(g,m);
        }
        else draw(g);
    }
    /**
     * draws images
     * @param g graphics window
     * @param m main instance
     */
    protected void draw(Graphics g, Main m){
        g.drawImage(null, 0, 0, m);
    }
    /**
     * draws a rectangle (default)
     * @param g the graphics window
     */
    protected void draw(Graphics g){
        //default non-image is a rectangle, will be overridden later
        g.drawRect(x, y, size, size);        
    }
}