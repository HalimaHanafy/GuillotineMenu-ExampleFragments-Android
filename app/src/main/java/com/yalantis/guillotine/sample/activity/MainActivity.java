package com.yalantis.guillotine.sample.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yalantis.guillotine.animation.GuillotineAnimation;
import com.yalantis.guillotine.interfaces.GuillotineListener;
import com.yalantis.guillotine.sample.FeedFragment;
import com.yalantis.guillotine.sample.ProfileFragment;
import com.yalantis.guillotine.sample.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dmytro Denysenko on 5/4/15.
 */
public class MainActivity extends AppCompatActivity {
    private static final long RIPPLE_DURATION = 250;


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.root)
    FrameLayout root;
    @BindView(R.id.content_hamburger)
    View contentHamburger;
    private GuillotineAnimation GuillotineBuilder;
    int menuItemID;
    View guillotineMenu;
    TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        ButterKnife.bind(this);


        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }


        toolbarTitle = (TextView) findViewById(R.id.tooltitlle);
        toolbarTitle.setText("PROFILE");
        switchFragments(new ProfileFragment());


        guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine, null);
        root.addView(guillotineMenu);

        View vProfile = guillotineMenu.findViewById(R.id.profile_group);
        View vFeed = guillotineMenu.findViewById(R.id.feed_group);

        //profile click listener
        vProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbarTitle.setText("PROFILE");
                GuillotineBuilder.close();
                switchFragments(new ProfileFragment());

            }
        });

        //profile click listener
        vFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbarTitle.setText("FEED");
                GuillotineBuilder.close();
                switchFragments(new FeedFragment());
            }
        });


        GuillotineBuilder=new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .setGuillotineListener(new GuillotineListener(){
                                            @Override public void onGuillotineOpened(){
//                                                mConnBtn.setClickable(false);
                                            }
                                            @Override public void onGuillotineClosed(){
//                                                mConnBtn.setClickable(true);
                                            }
                                        }
                ).build();
    }


    /**
     * Control showing fragments.
     *
     * @param fragment Fragment to switch to.
     */
    private void switchFragments(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
