import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PShape;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PShape;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Earthquake extends PApplet {

    PShape bg;
    PFont font;

    List<EarthquakeData> data;

    public static final int WIDTH = 1000, HEIGHT = 600;

    public void setup() {
        bg = loadShape("WorldMap.svg");
        font = createFont("Arial", 14);
        data = new ArrayList<>(34070);

        // read file
        try {
            final int[] lineNum = new int[1];
            Files.readAllLines(Paths.get("./earthquakes.csv")).forEach(line -> {
                EarthquakeData d = new EarthquakeData(line);
                data.add(d);
            });
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    @Override
    public void draw() {
        beginRecord(PDF, "out.pdf");
        shape(bg, 0, 0, WIDTH, HEIGHT);
        noStroke();

        colorMode(HSB, 360, 255, 255);

        for (EarthquakeData dp : this.data) {
            if (!dp.valid) continue;

            float latitude = map(dp.latitude, 90, -90, 0, HEIGHT);
            float longitude =  map(dp.longitude, -180, 180, 0, WIDTH);
            float size = (10 * sqrt(dp.mag) / PI);

            float depth = map(dp.depth, 0, 1000, 100 ,255);

            fill(287, 255, depth, 20);
            ellipse(longitude, latitude, size, size);
        }
        endRecord();
    }

    @Override
    public void mousePressed() {

    }

    public static void main(String[] args) { PApplet.main("Earthquake"); }
}