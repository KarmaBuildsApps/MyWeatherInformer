package myapp.tae.ac.uk.myweatherapp.util;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Karma on 14/03/16.
 */
public class JSONUtil {
    private Context mContext;
    private JSONArray mCountryJSONArray;
    private String mFileName;

    public JSONUtil(Context context, String fileName) {
        this.mContext = context;
        this.mFileName = fileName;
        mCountryJSONArray = getCountryCodeArray(mFileName);
    }

    public void reLoadCountryJSONArray(String fileName) {
        this.mFileName = fileName;
        mCountryJSONArray = getCountryCodeArray(fileName);
    }

    private String loadJSONFilefromAssets(String fileName) {
        String jsonFileContent;
        try {
            InputStream inputStream = mContext.getAssets().open(fileName);
            int read = inputStream.available();
            byte[] buffer = new byte[read];
            inputStream.read(buffer);
            inputStream.close();
            jsonFileContent = new String(buffer, "UTF-8");
            return jsonFileContent;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private JSONArray getCountryCodeArray(String fileName) {
        String fileContent = loadJSONFilefromAssets(fileName);
        JSONArray countryCodeArray = null;
        if (fileContent == null)
            return null;
        try {
            countryCodeArray = new JSONArray(fileContent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return countryCodeArray;
    }

    public String getCountryAlpha2Code(String countryName) {
        String countryAlpha2 = "";
        countryName = countryName.toLowerCase();
        try {
            if (mCountryJSONArray != null) {
                String jsonCountryName = "";
                JSONObject jsonObject;
                boolean isMatched;
                for (int i = 0; i < mCountryJSONArray.length(); i++) {
                    jsonObject = mCountryJSONArray.getJSONObject(i);
                    jsonCountryName = jsonObject.getString("name").toLowerCase();
//                    isMatched = jsonCountryName.matches("(.*?" + Pattern.quote(countryName) +
//                            "\\s.*)|(.*?" + Pattern.quote(countryName) + "$)");
                    isMatched = jsonCountryName.contains(countryName);
                    if (isMatched) {

                        if (isNotPartOf(jsonCountryName, countryName)) {
                            countryAlpha2 = jsonObject.getString("alpha-2");
                            break;
                        } else {
                            continue;
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return countryAlpha2;
    }

    private boolean isNotPartOf(String jsonCountryName, String countryName) {
        for (int i = 0; i < jsonCountryName.length(); i++) {
            for (int j = 0; j < countryName.length(); j++) {
                if (jsonCountryName.charAt(i) == countryName.charAt(j)) {
                    if (j == countryName.length() - 1) {
                        if (i == jsonCountryName.length() - 1) {
                            return true;
                        } else if (jsonCountryName.charAt(i + 1) == ' ' || jsonCountryName.charAt(i + 1) == ',') {
                            return true;
                        }
                        return false;
                    }
                    i++;
                } else {
                    break;
                }

            }
        }
        return false;
    }
}
