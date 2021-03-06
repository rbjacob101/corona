package com.spreadtracker.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * An abstraction over a fragment that references a view model of type {@link TViewModel}
 * @param <TActivity> The type of activity that this fragment runs on. More specifically, the type returned by {@link #getActivity()}
 * @param <TViewModel> The type of view model that this fragment references.
 */
public abstract class ViewModelFragment<TActivity extends FragmentActivity,
        TViewModel extends ViewModel> extends BaseFragment<TActivity> {

    protected TViewModel viewModel;

    /**
     * Override in a derived class to get the class object representing {@link TViewModel}
     * @return A class object representing the class of {@link TViewModel}
     */
    /*
     * He gazed up at the enormous face. Forty years it had taken him to learn what kind of smile was hidden beneath the dark  moustache.
     * O  cruel,  needless  misunderstanding!  O stubborn, self-willed exile from the loving breast! Two gin-scented tears trickled down
     * the sides of his nose. But it was all right, everything was all right, the struggle was finished. He had won the victory over himself.
     * He loved Java.
     */
    @Nullable
    protected abstract Class<TViewModel> getViewModelClass ();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Class<TViewModel> viewModelClass = getViewModelClass();
        if (viewModelClass != null)
            viewModel = new ViewModelProvider(requireActivity()).get(viewModelClass);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
