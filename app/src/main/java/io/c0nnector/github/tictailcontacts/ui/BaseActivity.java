package io.c0nnector.github.tictailcontacts.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.f2prateek.dart.Dart;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.c0nnector.github.tictailcontacts.R;
import io.c0nnector.github.tictailcontacts.util.Val;

/**
 * Base class to be extended by all other activities
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * Initialized with onCreate(). Use it to setup your variables
     */
    protected void initVariables(){}

    /**
     * Initialized with setContentView(), after we bind our views with butterknife.
     */
    protected void afterViews(){}

    /**
     * Initialized with setContentView(). Use it to validate you have the right data.
     * E.g check for @extras or get data from cache/net etc...
     */
    protected void validate(){}

    /**
     * @return root view for activity
     */
    public View getContentView(){
        return this.findViewById(android.R.id.content);
    }


    @Nullable
    @Bind(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //inject extras
        Dart.inject(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        bindViews();

        initVariables();

        validate();

        afterViews();
    }

    /**
     * Bind views with butterknife
     */
    private void bindViews(){

        ButterKnife.bind(this);

        setupToolbar();
    }

    @SuppressWarnings("ConstantConditions")
    protected void setupToolbar(){

        if (Val.notNull(toolbar)) {

            setSupportActionBar(toolbar);
        }
    }
}
