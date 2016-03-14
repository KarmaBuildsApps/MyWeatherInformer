
package myapp.tae.ac.uk.myweatherapp.model.weather;

import android.os.Parcel;
import android.os.Parcelable;


public class Clouds implements Parcelable {
    private Integer all;
    public final static Creator<Clouds> CREATOR = new Creator<Clouds>() {


        public Clouds createFromParcel(Parcel in) {
            Clouds instance = new Clouds();
            instance.all = ((Integer) in.readValue((Integer.class.getClassLoader())));
            return instance;
        }

        public Clouds[] newArray(int size) {
            return (new Clouds[size]);
        }

    };

    /**
     * @return The all
     */
    public Integer getAll() {
        return all;
    }

    /**
     * @param all The all
     */
    public void setAll(Integer all) {
        this.all = all;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(all);
    }

    public int describeContents() {
        return 0;
    }

}
