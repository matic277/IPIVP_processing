import processing.core.PApplet;
import processing.core.PImage;

public class ProcessingTest extends PApplet {
    
    public ProcessingTest() {}
    
    PImage bg;
    
    @Override
    public void settings() {
        size(500, 500);
        
        bg = loadImage("image.png");
        
        if (bg != null) System.out.println("image loaded");
    }
    
    @Override
    public void draw() {
        if (bg == null) background(0);
        else image(bg, 0, 0);
        
        ellipse(mouseX, mouseY, 50, 50);
    }
    
    @Override
    public void mousePressed() {
        saveFrame("image.png");
        System.out.println("saving");
    }
    
    public static void main(String[] args) { PApplet.main("ProcessingTest"); }
}