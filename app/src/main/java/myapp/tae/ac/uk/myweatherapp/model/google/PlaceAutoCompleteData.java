package myapp.tae.ac.uk.myweatherapp.model.google;

/**
 * Created by Karma on 13/03/16.
 */
public class PlaceAutoCompleteData {
    private String mId;
    private String mCityName;
    private String mCountry;

    public String getId() {
        return mId;
    }

    public void setId(String id){
        mId = id;
    }

    public String getCityName() {
        return mCityName;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCityCountry(String secondaryPlaceData) {
        String[] secondaryData;
        if (!secondaryPlaceData.isEmpty() && secondaryPlaceData.contains(",")) {
            secondaryData = secondaryPlaceData.split(",");
            mCityName = secondaryData[0];
            mCountry = secondaryData[1];
        }
    }
}
