package myapp.tae.ac.uk.myweatherapp.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import myapp.tae.ac.uk.myweatherapp.R;
import myapp.tae.ac.uk.myweatherapp.constants.Constants;
import myapp.tae.ac.uk.myweatherapp.model.weather.Wind;

/**
 * Created by Karma on 12/03/16.
 */
public class FragmentWindIfo extends Fragment {
    @Bind(R.id.ivWindDirectionPointer)
    ImageView ivWindDirectionPointer;
    @Bind(R.id.tvWindSpeedValue)
    TextView tvWindSpeedValue;
    @Bind(R.id.tvWindDegreeValue)
    TextView getTvWindDegreeValue;
    private float PREVIOUS_DEGREE = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wind_detail_layout, container, false);
        ButterKnife.bind(this, view);
        setRetainInstance(true);
        return view;
    }


    public void updateViews(Wind wind) {
        tvWindSpeedValue.setText(wind.getSpeed().toString() + Constants.MPH_UNIT);
        getTvWindDegreeValue.setText(wind.getDeg().intValue() + Constants.WIND_DEGREE_UNIT);
        RotateAnimation mRoteAnimation = new RotateAnimation(PREVIOUS_DEGREE,
                wind.getDeg().floatValue(), Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRoteAnimation.setFillAfter(true);
        mRoteAnimation.setDuration(1000);
        ivWindDirectionPointer.startAnimation(mRoteAnimation);

        PREVIOUS_DEGREE = wind.getDeg().floatValue();
    }


}
