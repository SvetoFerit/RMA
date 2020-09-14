package svetozarradic.ferit.hr.rma_project.connection;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import svetozarradic.ferit.hr.rma_project.CalculationActivity;
import svetozarradic.ferit.hr.rma_project.R;
import svetozarradic.ferit.hr.rma_project.models.FitbitParameters;
import svetozarradic.ferit.hr.rma_project.utils.FitbitUtils;
import svetozarradic.ferit.hr.rma_project.utils.TextViewSyncCallback;

public class ConnectFitbitActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.btn_ConnectFitbit)
    Button btnConnectFitbit;

    public static String EXTRA_STEPS = "extra_steps";
    public static String EXTRA_CALORIES = "extra_calories";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_fitbit);
        ButterKnife.bind(this);
        btnConnectFitbit.setOnClickListener(this);
        getFitbitParameters();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ConnectFitbit: {
                if (FitbitUtils.connectedToInternet(this)) {
                    final ProgressDialog progressBar = new ProgressDialog(ConnectFitbitActivity.this);
                    progressBar.setMessage("Connecting....");
                    progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressBar.show();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.dismiss();
                            Toast.makeText(ConnectFitbitActivity.this, "You are successfully connected", Toast.LENGTH_SHORT).show();
                        }
                    }, 3000);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            connectToFitbit();
                        }
                    }, 4000);
                } else {
                    Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    public void connectToFitbit() {
        Intent intent = new Intent(ConnectFitbitActivity.this, CalculationActivity.class);
        intent.putExtra(EXTRA_STEPS, FitbitParameters.STEPS);
        intent.putExtra(EXTRA_CALORIES, FitbitParameters.CALORIES);
        startActivity(intent);
    }

    public void getFitbitParameters() {
        FitbitUtils.sync(this, new TextViewSyncCallback() {
            @Override
            public void textViewCallback() {
            }
        });

    }
}
