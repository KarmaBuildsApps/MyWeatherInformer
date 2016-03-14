
package myapp.tae.ac.uk.myweatherapp.model.weather;

import android.os.Parcel;
import android.os.Parcelable;


public class Wind implements Parcelable {

    private Double speed;
    private Double deg;
    public final static Creator<Wind> CREATOR = new Creator<Wind>() {


        public Wind createFromParcel(Parcel in) {
            Wind instance = new Wind();
            instance.speed = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.deg = ((Double) in.readValue((Double.class.getClassLoader())));
            return instance;
        }

        public Wind[] newArray(int size) {
            return (new Wind[size]);
        }
    };

    /**
     * @return The speed
     */
    public Double getSpeed() {
        return speed;
    }

    /**
     * @param speed The speed
     */
    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    /**
     * @return The deg
     */
    public Double getDeg() {
        return deg;
    }

    /**
     * @param deg The deg
     */
    public void setDeg(Double deg) {
        this.deg = deg;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(speed);
        dest.writeValue(deg);
    }

    public int describeContents() {
        return 0;
    }

}
