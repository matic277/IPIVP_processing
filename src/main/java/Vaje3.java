import processing.core.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Vaje3 extends PApplet {
    
    PShape bg;
    PFont font;
    
    List<DataPoint> data;
    
    public static final int WIDTH = 1400, HEIGHT = 800;
    
    public void setup() {
        bg = loadShape("WorldMap.svg");
        font = createFont("Arial", 14);
        data = new ArrayList<>(34070);
        
        // read file
        try {
            final int[] lineNum = new int[1];
            Files.readAllLines(Paths.get("./MeteorStrikes.csv")).forEach(line -> {
                if (lineNum[0] == 0) { lineNum[0] = 1; return; }
                
                System.out.println("LINE="+line);
                DataPoint d = new DataPoint(line.split(","));
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
        shape(bg, 0, 0, WIDTH, 800);
        ellipse(mouseX, mouseY, 10, 10);
        
        for (DataPoint dp : this.data) {
            float longitude = map((float) dp.longitude, -180, 180, 0, WIDTH);
            float latitude = map((float) dp.latitude, 90, -90, 0, HEIGHT);
            float size = (float) (0.01 * sqrt((float)dp.massG) / PI);
            
            ellipse(longitude, latitude, size, size);
    
            if(dp.massG > 5000000){
//                println(dp.place);
                textFont(font);
                fill(0);
                text(dp.year, longitude+size/2 +5, latitude +4);
                line(longitude + size/2, latitude, longitude+size/2 + 4, latitude);
            }
        }
    }
    
    @Override
    public void mousePressed() {

    }
    
    public static void main(String[] args) { PApplet.main("Vaje3"); }
}

class DataPoint {
    public String place, fellOrfound;
    public int year;
    public double longitude, latitude, massG;
    
    public DataPoint(String[] cols) {
        place = cols[0];
        if (!cols[1].isEmpty()) year = Integer.parseInt(cols[1]);
        massG = Double.parseDouble(cols[2]);
        longitude = Double.parseDouble(cols[3]);
        latitude = Double.parseDouble(cols[4]);
        fellOrfound = cols[5];
    }
}
