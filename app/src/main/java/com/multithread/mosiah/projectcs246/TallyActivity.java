package com.multithread.mosiah.projectcs246;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * <p>TallyActivity displays the goal completion streak for the user.</p>
 * <p>(It will reset to 0 if user misses a scheduled task during the day.)</p>
 * @author Peyton Dunnaway
 */
public class TallyActivity extends AppCompatActivity {

    private int tallyCount;
    private TextView tallyView;
    Button backButton;

    /**
     * Just added a back button to UI
     * @author Peyton Dunnaway
     * @param savedInstanceState new instance variable
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tally);

        backButton = (Button) findViewById(R.id.backToMain);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this brings us back to main activity
                Intent intent = new Intent(TallyActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * calls display tally method
     * @author Peyton Dunnaway
     */
    @Override
    protected void onStart() {
        super.onStart();
        getTaskTally();
    }

    /**
     * Gets tallyCount from sharedPreferences and displays it in a quick animated up-counter
     * @author Peyton Dunnaway
     */
    public void getTaskTally() {
        //access shared preferences tallyCount
        SharedPreferences tally = getSharedPreferences("tallyCount", MODE_PRIVATE);
        tallyCount = tally.getInt("tally", 0);
        animateTextView(0, tallyCount, tallyView = (TextView)findViewById(R.id.tallyAnimation));
    }

    /**
     * Animated Up-counter
     * @author Peyton Dunnaway
     * @param initialValue value to start animation at
     * @param finalValue value to end at
     * @param textview widget to show these values in
     */
    public void animateTextView(int initialValue, int finalValue, final TextView textview) {

        ValueAnimator valueAnimator = ValueAnimator.ofInt(initialValue, finalValue);
        valueAnimator.setDuration(2000);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                textview.setText(valueAnimator.getAnimatedValue().toString());
            }
        });
        valueAnimator.start();
    }
}
