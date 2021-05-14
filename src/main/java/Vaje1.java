import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;
import processing.event.MouseEvent;

public class Vaje1 extends PApplet {
    public static void main(String[] args) { PApplet.main("Vaje1"); }
    
    PanZoomController panZoomController;
    
    PImage img;
    MyImage img2;
    public void setup() {
        // ...
        img = loadImage("image.png");
        panZoomController = new PanZoomController(this);
        img2 = new MyImage(img);
    }
    public void settings() { setSize(800, 600); }
    public void draw() {
        background(1);
        PVector pan = panZoomController.getPan();
        pushMatrix();
        translate(pan.x, pan.y);
        scale(panZoomController.getScale());
        // draw ...
//        image(img, 0, 0 );
        img2.draw(this);
    
        popMatrix();
        ellipse(mouseX, mouseY, 30, 30);
        text(mouseX+", "+ mouseY, mouseX, mouseY-30);
    }
    public void keyPressed() {
        if (img2.contains(mouseX, mouseY)) {
            System.out.println("YES");
        }
        panZoomController.keyPressed();
    }
    public void mouseDragged() {
        panZoomController.mouseDragged(img2);
    }
    public void mouseWheel(MouseEvent event) {
        panZoomController.mouseWheel(event.getCount(), img2);
    }
}

class MyImage {
    PImage img;
    float x = 0, y = 0;
    
    float dx = 0, dy = 0;
    
    public MyImage(PImage img) { this.img = img; }
    
    public void draw(PApplet apl) {
        apl.image(img, 0, 0);
        apl.text("["+dx+", "+dy+"]", 0, 0);
        apl.text(x + img.width + ", " + y + img.height, x + img.width + 10, y + img.height + 10);
    }
    
    public void move(PVector pan) {
        this.x += pan.x;
        this.y += pan.y;
        this.dx += pan.x;
        this.dy += pan.y;
    }
    
    public void move2(float scale) {
        System.out.println("b4: " + x + ", " + y);
        this.dx = x * scale;
        this.dy = y * scale;
        System.out.println("aft: " + x + ", " + y);
    }
    
    public boolean contains(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + img.width &&
                mouseY >= y && mouseY <= y + img.height;
    }
}

class PanZoomController {
    
    private final PVector DIR_UP = new PVector(0, -1);
    private final PVector DIR_DOWN = new PVector(0, 1);
    private final PVector DIR_LEFT = new PVector(-1, 0);
    private final PVector DIR_RIGHT = new PVector(1, 0);
    
    private float panVelocity = 40;
    private float scaleVelocity = 0.1f;
    private float minLogScale = -5;
    private float maxLogScale = 5;
    
    private float logScale = 0;
    private float scale = 1;
    private PVector pan = new PVector();
    
    private PApplet p;
    
    public PanZoomController(PApplet p) {
        this.p = p;
    }
    
    public void mouseDragged(MyImage img2) {
        PVector mouse = new PVector(p.mouseX, p.mouseY);
        PVector pmouse = new PVector(p.pmouseX, p.pmouseY);
        PVector dx = PVector.sub(mouse, pmouse);
        pan.add(dx);
        //System.out.println(dx);
        img2.move(dx);
    }
    
    public void keyPressed() {
        if (p.key == PConstants.CODED) {
            switch (p.keyCode) {
                case PApplet.UP:
                    moveByKey(DIR_UP);
                    break;
                case PApplet.DOWN:
                    moveByKey(DIR_DOWN);
                    break;
                case PApplet.LEFT:
                    moveByKey(DIR_LEFT);
                    break;
                case PApplet.RIGHT:
                    moveByKey(DIR_RIGHT);
                    break;
            }
        }
    }
    
    public void mouseWheel(int step, MyImage img) {
        logScale = PApplet.constrain(logScale + step * scaleVelocity,
                minLogScale,
                maxLogScale);
        float prevScale = scale;
        scale = (float) Math.pow(2, logScale);
        System.out.println(scale);
        
        PVector mouse = new PVector(p.mouseX, p.mouseY);
        pan = PVector.add(mouse,
                PVector.mult(PVector.sub(pan, mouse), scale / prevScale));
        
        
//        if (prevScale > scale)
//            img.move(PVector.add(mouse,
//                PVector.mult(PVector.sub(pan, mouse), scale / prevScale)));
//        else
            img.move2(scale + 2*(1 - scale));
        
        
        System.out.println(PVector.add(mouse,
                PVector.mult(PVector.sub(pan, mouse), scale / prevScale)));
    
        
        
    }
    
    private void moveByKey(PVector direction) {
        pan.add(PVector.mult(direction, panVelocity));
    }
    
    public float getScale() {
        return scale;
    }
    
    public void setScale(float scale) {
        this.scale = scale;
    }
    
    public PVector getPan() {
        return pan;
    }
    
    public void setPan(PVector pan) {
        this.pan = pan;
    }
    
    public void setPanVelocity(float panVelocity) {
        this.panVelocity = panVelocity;
    }
    
    public void setScaleVelocity(float scaleVelocity) {
        this.scaleVelocity = scaleVelocity;
    }
    
    public void setMinLogScale(float minLogScale) {
        this.minLogScale = minLogScale;
    }
    
    public void setMaxLogScale(float maxLogScale) {
        this.maxLogScale = maxLogScale;
    }
}
