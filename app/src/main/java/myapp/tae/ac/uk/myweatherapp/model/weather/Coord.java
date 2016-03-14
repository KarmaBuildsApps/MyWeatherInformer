
package myapp.tae.ac.uk.myweatherapp.model.weather;

import android.os.Parcel;
import android.os.Parcelable;

public class Coord implements Parcelable {
    private Double lon;
    private Double lat;
    public final static Creator<Coord> CREATOR = new Creator<Coord>() {


        public Coord createFromParcel(Parcel in) {
            Coord instance = new Coord();
            instance.lon = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.lat = ((Double) in.readValue((Double.class.getClassLoader())));
            return instance;
        }

        public Coord[] newArray(int size) {
            return (new Coord[size]);
        }

    };

    /**
     * @return The lon
     */
    public Double getLon() {
        return lon;
    }

    /**
     * @param lon The lon
     */
    public void setLon(Double lon) {
        this.lon = lon;
    }

    /**
     * @return The lat
     */
    public Double getLat() {
        return lat;
    }

    /**
     * @param lat The lat
     */
    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(lon);
        dest.writeValue(lat);
    }

    public int describeContents() {
        return 0;
    }

}
