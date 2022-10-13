package com.zorro.utils.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;

import com.zorro.utils.CustomizeView.QQStepView;
import com.zorro.utils.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        QQStepView qqStepView = findViewById(R.id.step_view);
        qqStepView.setStepMax(4000);

        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0,3000);
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float currentStep = (float) valueAnimator.getAnimatedValue();
                qqStepView.setCurrentStep((int) currentStep);
            }
        });
        valueAnimator.start();
    }
}