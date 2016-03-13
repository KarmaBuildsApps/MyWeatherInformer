
package myapp.tae.ac.uk.myweatherapp.model;

import javax.annotation.Generated;
import android.os.Parcel;
import android.os.Parcelable;

public class Main implements Parcelable
{
    private Double temp;
    private Double pressure;
    private Integer humidity;
    private Double tempMin;
    private Double tempMax;
    private Double seaLevel;
    private Double grndLevel;
    public final static Creator<Main> CREATOR = new Creator<Main>() {


        public Main createFromParcel(Parcel in) {
            Main instance = new Main();
            instance.temp = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.pressure = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.humidity = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.tempMin = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.tempMax = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.seaLevel = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.grndLevel = ((Double) in.readValue((Double.class.getClassLoader())));
            return instance;
        }

        public Main[] newArray(int size) {
            return (new Main[size]);
        }

    }
    ;

    /**
     * 
     * @return
     *     The temp
     */
    public Double getTemp() {
        return temp;
    }

    /**
     * 
     * @param temp
     *     The temp
     */
    public void setTemp(Double temp) {
        this.temp = temp;
    }

    /**
     * 
     * @return
     *     The pressure
     */
    public Double getPressure() {
        return pressure;
    }

    /**
     * 
     * @param pressure
     *     The pressure
     */
    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    /**
     * 
     * @return
     *     The humidity
     */
    public Integer getHumidity() {
        return humidity;
    }

    /**
     * 
     * @param humidity
     *     The humidity
     */
    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    /**
     * 
     * @return
     *     The tempMin
     */
    public Double getTempMin() {
        return tempMin;
    }

    /**
     * 
     * @param tempMin
     *     The temp_min
     */
    public void setTempMin(Double tempMin) {
        this.tempMin = tempMin;
    }

    /**
     * 
     * @return
     *     The tempMax
     */
    public Double getTempMax() {
        return tempMax;
    }

    /**
     * 
     * @param tempMax
     *     The temp_max
     */
    public void setTempMax(Double tempMax) {
        this.tempMax = tempMax;
    }

    /**
     * 
     * @return
     *     The seaLevel
     */
    public Double getSeaLevel() {
        return seaLevel;
    }

    /**
     * 
     * @param seaLevel
     *     The sea_level
     */
    public void setSeaLevel(Double seaLevel) {
        this.seaLevel = seaLevel;
    }

    /**
     * 
     * @return
     *     The grndLevel
     */
    public Double getGrndLevel() {
        return grndLevel;
    }

    /**
     * 
     * @param grndLevel
     *     The grnd_level
     */
    public void setGrndLevel(Double grndLevel) {
        this.grndLevel = grndLevel;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(temp);
        dest.writeValue(pressure);
        dest.writeValue(humidity);
        dest.writeValue(tempMin);
        dest.writeValue(tempMax);
        dest.writeValue(seaLevel);
        dest.writeValue(grndLevel);
    }

    public int describeContents() {
        return  0;
    }

}
