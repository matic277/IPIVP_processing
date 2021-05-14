PShape baseMap;
String csv[];
String myData[][];
PFont f;

void setup(){
  f = createFont("Arial", 9);
  size(900,450);
  baseMap = loadShape("WorldMap.svg");
  noLoop();
  csv = loadStrings("MeteorStrikes.csv");
  myData = new String [csv.length][6];
  
  for(int i=0; i<csv.length; i++){
    myData[i] = csv[i].split(",");
  }
  println(myData[22][4]);
}

void draw(){
  shape(baseMap, 0, 0, width, height);
  fill(255,0,0,100);
 
  noStroke();
  
  for(int i=0; i<myData.length; i++){
    fill(255,0,0,100);
    noStroke();
    float graphLat = map(float(myData[i][4]), 90, -90, 0, height);
    float graphLong = map(float(myData[i][3]), -180, 180, 0, width);
    float markerSize = 0.01 * sqrt(float(myData[i][2]))/PI;
    ellipse(graphLong,graphLat,5,5);
    
    if(i<10){
      println(myData[i][0]);
      textFont(f);
      fill(0);
      text(myData[i][1],graphLong+markerSize/2 +5, graphLat +4);
      line(graphLong + markerSize/2, graphLat, graphLong+markerSize/2 + 4, graphLat);
    }
  }
}
