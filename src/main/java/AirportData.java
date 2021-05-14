public class AirportData {

    Integer id;
    String name;
    String source, destination;
    float longitude, latitude;

    float srcLongitude, srcLatitude;
    float destLongitude, destLatitude;

    boolean valid;

    public AirportData(String line) {
        String[] tokens = line.split(",");
        valid = true;
        try {
            srcLatitude = Float.parseFloat(tokens[8]);
            srcLongitude = Float.parseFloat(tokens[9]);

            destLatitude = Float.parseFloat(tokens[10]);
            destLongitude = Float.parseFloat(tokens[11]);
        }
        catch (Exception e) {
//            System.out.println("Error");
            valid = false;
        }
    }
}
