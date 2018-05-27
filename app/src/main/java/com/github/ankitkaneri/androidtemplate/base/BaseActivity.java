package com.github.ankitkaneri.androidtemplate.base;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.ankitkaneri.androidtemplate.constants.AppConstants;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean isOneItemClicked;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        initialiseView();
        bindListeners();
    }

    protected abstract void initialiseView();

    protected abstract void bindListeners();


    @Override
    public void onClick(View v) {
        if (!isOneItemClicked) {
            onClicked(v);
            isOneItemClicked = true;

            v.postDelayed(() -> isOneItemClicked = false,
                    AppConstants.DISABLE_DURATION);
        }
    }

    protected abstract void onClicked(View v);


}
