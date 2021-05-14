import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PShape;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Airports extends PApplet {
    
    PShape bg;
    PFont font;
    
    List<AirportData> data;
    
    public static final int WIDTH = 1400, HEIGHT = 800;
    
    public void setup() {
        bg = loadShape("WorldMap.svg");
        font = createFont("Arial", 14);
        data = new ArrayList<>(34070);
        
        // read file
        try {
            final int[] lineNum = new int[1];
            Files.readAllLines(Paths.get("./airrouts_clean.csv")).forEach(line -> {
//                if (lineNum[0] == 0) { lineNum[0] = 1; return; }
                AirportData d = new AirportData(line);
                data.add(d);
            });
        }
        catch (Exception e) { e.printStackTrace(); }
    }
    
    @Override
    public void settings() {
        size(1400, 800);
    }
    
    @Override
    public void draw() {
        shape(bg, 0, 0, WIDTH, HEIGHT);
        ellipse(mouseX, mouseY, 10, 10);
        
        for (AirportData dp : this.data) {
            if (!dp.valid) continue;

            noStroke();

            float size = (float) 5;

            float graphLat = map(dp.srcLatitude, 90, -90, 0, HEIGHT);
            float graphLong = map(dp.srcLongitude, -180, 180, 0, WIDTH);

            float graphLatDest = map(dp.destLatitude, 90, -90, 0, HEIGHT);
            float graphLongDest = map(dp.destLongitude, -180, 180, 0, WIDTH);

            fill(255, 0, 0);
            ellipse(graphLong, graphLat, size, size);
            fill(0, 255, 0);
            ellipse(graphLongDest, graphLatDest, size, size);

            fill(0, 0, 0);
            float cx = (graphLong + graphLongDest) / 2;
            float cy = (graphLat + graphLatDest) / 2;
            float r = sqrt(
                    pow((graphLongDest - graphLong), 2) +
                    pow((graphLatDest  - graphLat),  2));

            float archStart = atan((cy - graphLat) / (cx - graphLong));
            noFill();
            stroke(0, 150, 250, 10);
            arc(cx, cy, r, r, archStart-PI, archStart);
        }
    }
    
    @Override
    public void mousePressed() {

    }
    
    public static void main(String[] args) { PApplet.main("Airports"); }
}