public class EarthquakeData {

    float longitude, latitude;
    float mag;
    float depth;

    boolean valid = true;

    public EarthquakeData(String line) {
        String[] t = line.split(",");

        try {
            latitude = Float.parseFloat(t[1]);
            longitude = Float.parseFloat(t[2]);
            mag = Float.parseFloat(t[4]);
            depth = Float.parseFloat(t[3]);
        }
        catch (Exception e) { valid = false; }
    }
}
