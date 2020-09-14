package svetozarradic.ferit.hr.rma_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.feritLogo)
    ImageView feritLogo;
    @BindView(R.id.SplashRunLogo)
    ImageView splashRunLogo;
    @BindView(R.id.tvSplash)
    TextView tvSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        startAnimation();
    }

    private void startAnimation() {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animationsplash);
        feritLogo.startAnimation(animation);
        splashRunLogo.startAnimation(animation);
        tvSplash.startAnimation(animation);

        Thread timer = new Thread() {

            @Override
            public void run() {

                try {
                    sleep(4000);
                    startActivity(new Intent(SplashActivity.this, LogInActivity.class));
                    finish();
                    super.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

        timer.start();
    }
}
