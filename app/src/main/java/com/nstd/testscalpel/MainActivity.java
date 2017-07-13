package com.nstd.testscalpel;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.github.nstd.scalpelhelper.ScalpelHelper;
import com.nstd.testscalpel.databinding.ActivityMainBinding;


public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";

    ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Toast.makeText(this, "mBinding is null = " + (mBinding == null), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);

        new ScalpelHelper().injectScalpel(this);
    }
}
