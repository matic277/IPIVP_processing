import org.apache.commons.lang3.builder.HashCodeBuilder;
import processing.core.PApplet;

import javax.swing.*;

//import treemap.*;

import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Vaje2 extends PApplet {
    
    WordMap model;
    
    public static final int WIDTH = 1000, HEIGHT = 1000;
    public static final int MIN_OCCURENCE = 100;
    
    public Vaje2() {}
    
    @Override
    public void settings() {
        size(WIDTH, HEIGHT);
    
        model = new WordMap();
        
        // read file
        try {
            Files.readAllLines(Paths.get("./equator.txt")).forEach(line -> {
                model.addWord(line);
            });
        }
        catch (Exception e) { e.printStackTrace(); }
        
        model.initVisualization();
        
        
//        model.items.add(new WordItem(new Rectangle(30, 30, 30, 30)));
    }
    
    @Override
    public void draw() {
        background(255,255,255);

        fill(0, 0, 0);
        noFill(); // only draw rectangle outline
        model.draw(this);
    }
    
    @Override
    public void mousePressed() {
    
    }
    
    @Override
    public void mouseMoved() {
        ellipse(mouseX, mouseY, 50, 50);
        model.map.keySet().stream()
                .filter(w -> w.getBoundsRect().contains(mouseX, mouseY) && model.map.get(w) > MIN_OCCURENCE)
                .findFirst()
                .ifPresent((w) -> {
                    text( w.word+" "+model.map.get(w), mouseX, mouseY);
                });
    }
    
    public static void main(String[] args) { PApplet.main("Vaje2"); }
}

class WordItem {
    
    String word;
    Rectangle boundsRect;
    Point position;
    int fontSize;
    
    public WordItem(String word, Point position) {
        this.word = word;
        this.position = position;
    }
    
    public void draw(PApplet gr) {
        
        gr.rect(boundsRect.x, boundsRect.y,boundsRect.width, boundsRect.height);
        gr.textSize(fontSize);
        gr.text(word, position.x, position.y);
    }
    
    public Rectangle getBoundsRect() {
        return boundsRect;
    }
    
    public void setFontSize(int size) {
        this.fontSize = size;
        this.boundsRect = new Rectangle(
                position.x,
                position.y - fontSize,
                fontSize*word.length(),
                fontSize);
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(word).toHashCode();
    }
    
    @Override
    public boolean equals(Object o) {
        return o.getClass() == this.getClass() && ((WordItem) o).word.equalsIgnoreCase(this.word);
    }
}

class WordMap {
    
    List<WordItem> items;
    Map<WordItem, Integer> map; // word -> number of appearances
    
    static final Random RAND = new Random();
    
    public WordMap() {
        items = new ArrayList<>(100);
        map = new HashMap<>();
    }
    
    public void addWord(String word) {
        Point randomPoint = new Point((int)(RAND.nextDouble()*Vaje2.WIDTH), (int)(RAND.nextDouble()*Vaje2.HEIGHT));
//        System.out.println(randomPoint);
        WordItem item = new WordItem(word, randomPoint);
        if (map.containsKey(item)) {
            map.compute(item, (k, v) -> v+1);
        } else {
            map.put(item, 1);
        }
    }
    
    public void draw(PApplet gr) {
//        items.forEach(i -> i.draw(gr));
        map.forEach((k, v) -> {
            if (v > Vaje2.MIN_OCCURENCE) k.draw(gr);
        });
    }
    
    public void initVisualization() {
        map.forEach((k, v) -> {
            k.setFontSize((int)(Math.log((double)v)*4));
        });
    
        System.out.println(map.values().stream().sorted().collect(Collectors.toList()));
    }
}

//class WordItem extends SimpleMapItem {
//    Rectangle boundsRect;
//
//    public WordItem(Rectangle bounds) {
//        this.boundsRect = bounds;
//        this.setBounds(new Rect(bounds.x, bounds.y, bounds.width, bounds.height));
//    }
//
//    public void draw(PApplet gr) {
//        gr.rect(this.x, this.y, this.w, this.h);
//    }
//
//    public Rectangle getBoundsRect() {
//        return boundsRect;
//    }
//}
//
//class WordMap extends SimpleMapModel {
//    List<WordItem> items;
//    Map<String, WordItem> map;
//
//    public WordMap() {
//        items = new ArrayList<>(100);
//        map = new HashMap<>();
//    }
//
//    public void addWord() {
//
//    }
//
//    public void draw(PApplet gr) {
//        items.forEach(i -> i.draw(gr));
//    }
//}