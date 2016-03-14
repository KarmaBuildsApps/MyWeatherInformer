
package myapp.tae.ac.uk.myweatherapp.model.weather;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class WeatherInfo implements Parcelable {

    private Coord coord;
    private List<Weather> weather = new ArrayList<Weather>();
    private String base;
    private Main main;
    private Wind wind;
    private Clouds clouds;
    private Integer dt;
    private Sys sys;
    private Integer id;
    private String name;
    private Integer cod;
    public final static Creator<WeatherInfo> CREATOR = new Creator<WeatherInfo>() {


        public WeatherInfo createFromParcel(Parcel in) {
            WeatherInfo instance = new WeatherInfo();
            instance.coord = ((Coord) in.readValue((Coord.class.getClassLoader())));
            in.readList(instance.weather, (Weather.class.getClassLoader()));
            instance.base = ((String) in.readValue((String.class.getClassLoader())));
            instance.main = ((Main) in.readValue((Main.class.getClassLoader())));
            instance.wind = ((Wind) in.readValue((Wind.class.getClassLoader())));
            instance.clouds = ((Clouds) in.readValue((Clouds.class.getClassLoader())));
            instance.dt = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.sys = ((Sys) in.readValue((Sys.class.getClassLoader())));
            instance.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.cod = ((Integer) in.readValue((Integer.class.getClassLoader())));
            return instance;
        }

        public WeatherInfo[] newArray(int size) {
            return (new WeatherInfo[size]);
        }

    };

    /**
     * @return The coord
     */
    public Coord getCoord() {
        return coord;
    }

    /**
     * @param coord The coord
     */
    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    /**
     * @return The weather
     */
    public List<Weather> getWeather() {
        return weather;
    }

    /**
     * @param weather The weather
     */
    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    /**
     * @return The base
     */
    public String getBase() {
        return base;
    }

    /**
     * @param base The base
     */
    public void setBase(String base) {
        this.base = base;
    }

    /**
     * @return The main_activity_menu
     */
    public Main getMain() {
        return main;
    }

    /**
     * @param main The main_activity_menu
     */
    public void setMain(Main main) {
        this.main = main;
    }

    /**
     * @return The wind
     */
    public Wind getWind() {
        return wind;
    }

    /**
     * @param wind The wind
     */
    public void setWind(Wind wind) {
        this.wind = wind;
    }

    /**
     * @return The clouds
     */
    public Clouds getClouds() {
        return clouds;
    }

    /**
     * @param clouds The clouds
     */
    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    /**
     * @return The dt
     */
    public Integer getDt() {
        return dt;
    }

    /**
     * @param dt The dt
     */
    public void setDt(Integer dt) {
        this.dt = dt;
    }

    /**
     * @return The sys
     */
    public Sys getSys() {
        return sys;
    }

    /**
     * @param sys The sys
     */
    public void setSys(Sys sys) {
        this.sys = sys;
    }

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The cod
     */
    public Integer getCod() {
        return cod;
    }

    /**
     * @param cod The cod
     */
    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(coord);
        dest.writeList(weather);
        dest.writeValue(base);
        dest.writeValue(main);
        dest.writeValue(wind);
        dest.writeValue(clouds);
        dest.writeValue(dt);
        dest.writeValue(sys);
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(cod);
    }

    public int describeContents() {
        return 0;
    }

}
