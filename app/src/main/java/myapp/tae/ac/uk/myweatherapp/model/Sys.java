
package myapp.tae.ac.uk.myweatherapp.model;

import javax.annotation.Generated;

import android.os.Parcel;
import android.os.Parcelable;

@Generated("org.jsonschema2pojo")
public class Sys implements Parcelable {
    private Double message;
    private String country;
    private Integer sunrise;
    private Integer sunset;
    public final static Creator<Sys> CREATOR = new Creator<Sys>() {


        public Sys createFromParcel(Parcel in) {
            Sys instance = new Sys();
            instance.message = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.country = ((String) in.readValue((String.class.getClassLoader())));
            instance.sunrise = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.sunset = ((Integer) in.readValue((Integer.class.getClassLoader())));
            return instance;
        }

        public Sys[] newArray(int size) {
            return (new Sys[size]);
        }

    };

    /**
     * @return The message
     */
    public Double getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(Double message) {
        this.message = message;
    }

    /**
     * @return The country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return The sunrise
     */
    public Integer getSunrise() {
        return sunrise;
    }

    /**
     * @param sunrise The sunrise
     */
    public void setSunrise(Integer sunrise) {
        this.sunrise = sunrise;
    }

    /**
     * @return The sunset
     */
    public Integer getSunset() {
        return sunset;
    }

    /**
     * @param sunset The sunset
     */
    public void setSunset(Integer sunset) {
        this.sunset = sunset;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(message);
        dest.writeValue(country);
        dest.writeValue(sunrise);
        dest.writeValue(sunset);
    }

    public int describeContents() {
        return 0;
    }

}
