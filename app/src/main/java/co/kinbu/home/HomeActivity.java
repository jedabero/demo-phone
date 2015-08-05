package co.kinbu.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jedabero.kinbu.R;

public class HomeActivity extends FragmentActivity {

    private static final float SWIPE_THRESHOLD_VELOCITY = 200;
    private static final float SWIPE_MIN_DISTANCE = 120;
    private GestureDetector gdc;
    private int currentTab = 0;
    private int maxTabs = 5;

    private FragmentTabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(getApplicationContext(), getSupportFragmentManager(), android.R.id.tabcontent);

        //TODO
        tabHost.addTab(tabHost.newTabSpec("Tab1").setIndicator("Tit1"), TabFragment.class, new Bundle());
        tabHost.addTab(tabHost.newTabSpec("Tab2").setIndicator("Tit2"), TabFragment.class, new Bundle());
        tabHost.addTab(tabHost.newTabSpec("Tab3").setIndicator("Tit3"), TabFragment.class, new Bundle());
        tabHost.addTab(tabHost.newTabSpec("Tab4").setIndicator("Tit4"), TabFragment.class, new Bundle());
        tabHost.addTab(tabHost.newTabSpec("Tab5").setIndicator("Tit5"), TabFragment.class, new Bundle());

        tabHost.setCurrentTab(currentTab);

        gdc = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                   float velocityY) {
                //FIN-fucking-ALLY
                float dX = e2.getX() - e1.getX();
                float dY = e2.getY() - e1.getY();


                if (Math.abs(dX) >= Math.abs(dY)) { //if fling was horizontal
                    if(Math.abs(dX) > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                        if (dX < 0 && currentTab >= 0 && currentTab < 4) { //towards left

                            currentTab++;

                        } else if (dX > 0 && currentTab > 0 && currentTab <= 4) { //towards right

                            currentTab--;

                        }
                        tabHost.setCurrentTab(currentTab);
                    }

                } else { //if fling was vertical
                    if(Math.abs(dY) > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                        if (dY < 0) { //upwards
                            Toast.makeText(getApplicationContext(), "up", Toast.LENGTH_SHORT).show();

                        } else if (dY > 0) { //downwards
                            Toast.makeText(getApplicationContext(), "down", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                                    float distanceY) {
                return false;
            }
        });

        LinearLayout rl = (LinearLayout) findViewById(R.id.home_linear_layout);
        rl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gdc.onTouchEvent(event);
                return true;
            }
        });

    }

    public static class TabFragment extends Fragment {
        static int tab = 0;
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_tab_home, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.fragment_home_text);
            textView.setText("this is " + ++tab);
            return rootView;
        }
    }

}
