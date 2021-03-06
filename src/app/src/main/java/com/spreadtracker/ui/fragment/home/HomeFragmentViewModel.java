package com.spreadtracker.ui.fragment.home;

import android.animation.ArgbEvaluator;
import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.spreadtracker.R;

/**
 * A ViewModel containing shared data for the {@link HomeFragment} and {@link OverlayFragment}
 * fragments. Note that this view model is shared across multiple fragments.
 */
public class HomeFragmentViewModel extends AndroidViewModel {

    private ArgbEvaluator mColorInterpolator;

    private boolean mColorsBeenSet = false;
    private int mHealthyColor, mUnhealthyColor;

    /**
     * Represents the infected percentage of the user,
     * as shown on the overlay screen.
     * Values range from 0 to 1.
     */
    private MutableLiveData<Double> infectedPercentage;

    public HomeFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Double> getInfectedPercentage() {
        if (infectedPercentage == null) infectedPercentage = new MutableLiveData<>();
        return infectedPercentage;
    }

    /**
     * Gets the interpolated color of the overlay according to the infected percentage.
     */
    public int getInfectedPercentageColor () {
        if (mColorInterpolator == null) mColorInterpolator = new ArgbEvaluator();
        if (!mColorsBeenSet) {
            mHealthyColor = ContextCompat.getColor(getApplication(), R.color.diseaseHealthy);
            mUnhealthyColor = ContextCompat.getColor(getApplication(), R.color.diseaseUnhealthy);
            mColorsBeenSet = true;
        }

        float value;
        if (getInfectedPercentage().getValue() == null) value = 1.0f;
        else value = (float)(double)getInfectedPercentage().getValue();

        return (int)mColorInterpolator.evaluate(value,
                mHealthyColor, mUnhealthyColor);
    }
}
