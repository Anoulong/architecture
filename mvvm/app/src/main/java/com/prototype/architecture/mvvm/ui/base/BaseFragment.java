package com.prototype.architecture.mvvm.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import com.mvvm.core.common.utils.ViewUtils;
import com.prototype.architecture.mvvm.RcaApplication;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseFragment extends Fragment {

    private CompositeDisposable fragmentSubscriptions;

    protected abstract String getFragmentTitle();

    //region Overriding Methods to handle android lifecycle
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RcaApplication.getApplicationComponent(getActivity()).inject(this);
        Log.d(getFragmentTag(), getFragmentTag() + " Created");
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupCloseKeyboardOnTouchOutside(view);//close keyboard on touch outside
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(getFragmentTag(), getFragmentTag() + " Started");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(getFragmentTag(), getFragmentTag() + " Resumed");
        closeKeyboard();
        setupObservables();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(getFragmentTag(), getFragmentTag() + " Stopped");
        clearObservables();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(getFragmentTag(), getFragmentTag() + " Destroyed");
        closeKeyboard();
        clearObservables();
    }

    public abstract String getFragmentTag();

    @Override
    public void onPause() {
        super.onPause();
        closeKeyboard();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }
    //endregion

    //region Utility Methods to manage Rx Observable in Fragments
    protected final void addDisposable(Disposable subscription) {
        fragmentSubscriptions.add(subscription);
    }

    protected final void clearObservables() {
        Log.d(getFragmentTag(), getFragmentTag() + " clearObservables");
        if (fragmentSubscriptions != null && !fragmentSubscriptions.isDisposed()) {
            fragmentSubscriptions.dispose();
        }
    }

    /**
     * Make sure to call super.setupObservables() when overriding this.
     */
    protected void setupObservables() {
        fragmentSubscriptions = new CompositeDisposable();
    }
    //endregion

    //region Utility Methods to manage keyboard
    protected void closeKeyboard() {
        ViewUtils.closeSoftInput(getActivity());
    }

    protected final View.OnFocusChangeListener mOnFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                closeKeyboard();
            }
        }
    };

    //Allow to close keyboard when touch outside
    public void setupCloseKeyboardOnTouchOutside(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                   closeKeyboard();
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupCloseKeyboardOnTouchOutside(innerView);
            }
        }
    }
    //endregion

    //region Utility Methods to manage loading
    protected void showProgressDialog() {
        ((BaseActivity)getActivity()).showProgressDialog();
    }

    protected void dismissProgressDialog() {
        ((BaseActivity)getActivity()).dismissProgressDialog();
    }
    //endregion
}
