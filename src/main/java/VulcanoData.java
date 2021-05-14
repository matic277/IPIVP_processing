public class VulcanoData {
    public String place, fellOrfound;
    public int year;
    public double longitude, latitude, massG;

    public VulcanoData(String[] cols) {
        place = cols[0];
        if (!cols[1].isEmpty()) year = Integer.parseInt(cols[1]);
        massG = Double.parseDouble(cols[2]);
        longitude = Double.parseDouble(cols[3]);
        latitude = Double.parseDouble(cols[4]);
        fellOrfound = cols[5];
    }
}
