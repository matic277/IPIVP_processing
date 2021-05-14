import processing.core.PApplet;
import processing.core.PFont;
import treemap.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class TreemapExercise extends PApplet {
    public static void main(String[] args) { PApplet.main("TreemapExercise"); }

    final int WIDTH = 1024, HEIGHT = 768;

    Treemap map;

    MapLayout algorithm;

    PFont font;

    public void setup() {
        WordMap wordMap = new WordMap();
//        wordMap.setBounds(new Rect(0, 0, WIDTH, HEIGHT));

        // read file
        Arrays.stream(loadStrings("equator.txt")).forEach(wordMap::addWord);
        wordMap.finishAdd();

        algorithm = new PivotBySplitSize();
        map = new Treemap(wordMap, 0, 0, width, height);
        map.setLayout(algorithm);
        map.updateLayout();

        stroke(0.25f);
        font = createFont("Serif", 12);
        textFont(font);
    }

    @Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    @Override
    public void draw() {
//        background(0, 0, 0);
        map.draw();
//        fill(0, 102, 153);
//        text("AAAAAAAAAAAAAAAAAAAAAAAAAA", 30, 30);
        this.redraw();
    }

    class WordMap extends treemap.SimpleMapModel {
        HashMap<String, WordItem> map;
        public WordMap() { map = new HashMap<>(); }
        public void addWord(String word) {
            WordItem item = new WordItem(word);
            if (map.containsKey(word)) {
                map.get(word).incrementSize();
            } else {
                map.put(word, item);
            }
        }
        public void finishAdd(){
            items = new WordItem[map.size()];
            map.values().toArray(items);
        }
    }

    class WordItem extends treemap.SimpleMapItem {
        String word;
        public WordItem(String w) { word = w; }
        public void draw() {
            float value = map(word.length(), 0, 20, 0, 255);

            fill(value, value, value, 255);
            rect(x, y, w, h);
            strokeWeight(0.25f);

            if (w > textWidth(word) && h > textAscent()+5) {
                textAlign(CENTER, CENTER);

                String strSize = "#" + this.size;
                strSize = strSize.replace(".0", "");

                fill(0, 102, 153);
                text(word, x+width/2, y+height/2);

                if (mouseX > x & mouseX < x+w &&
                    mouseY > y && mouseY < y+h) {
                    text(strSize, x+w/2, y+h/2);
//                    strokeWeight(1f);
//                    stroke(255);
                }
            }
        }
    }

}



