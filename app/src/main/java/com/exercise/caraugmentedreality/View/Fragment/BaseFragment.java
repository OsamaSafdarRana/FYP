package com.exercise.caraugmentedreality.View.Fragment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends android.app.Fragment {

    protected View mRootView;

    // to unbind ButterKnife on view destroy
    protected Unbinder mUnbinder = null;

    // to hold UI while processing
    protected Dialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        mRootView = onPreStart(inflater, container, savedInstanceState);
//
        // ButterKnife data binding
        mUnbinder = ButterKnife.bind(this, mRootView);
//
        initListeners();
        onPostStart(savedInstanceState);

        // on screen keyboard
//        KeyboardUtils.SetupHidableKeyboard(mRootView, getActivity());

       return mRootView;
    }

    /*
     * view inflation for fragment
     */
    protected View onPreStart(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /*
     * registering listeners
     */
    protected void initListeners() {
        //todo register moveToHomeScreen() listener here
    }

    /*
     * final call before closing onCreate
     */
    protected void onPostStart(Bundle savedInstanceState) {
//        if(DataManager.getInstance().getUserLanguageLocale().equalsIgnoreCase("en")){
//            setLocale("en");
//        } else if(DataManager.getInstance().getUserLanguageLocale().equalsIgnoreCase("af")){
//            setLocale("af");
//        }

    }

    protected void showProgress(String message) {
        if (mProgressDialog == null) {
//            mProgressDialog = DialogUtil.ShowProgress(getActivity(), message);
            mProgressDialog = new Dialog(getActivity());

            mProgressDialog.setCancelable(false);
        }
        if (!mProgressDialog.isShowing())
            mProgressDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null)
            mUnbinder.unbind();
        // if (mProgressDialog != null)
        // mProgressDialog.cancel();
    }

    /**
     * Shows a {@link Toast} message.
     *
     //* @param message An string representing a message to be shown.
     */

    protected void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    public void focusOnNextEditableView(final EditText current, final EditText next, final int size, final EditText pervious) {

        current.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.length() == 0) {
                    pervious.requestFocus(View.FOCUS_LEFT);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(current.getText().toString().length()==size)
                {
                    next.requestFocus();
                }
            }
        });
    }



    public void changeColor(int resourseColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getActivity().getApplicationContext(), resourseColor));
        }
    }
}
