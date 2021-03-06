package svetozarradic.ferit.hr.rma_project;

import android.content.Intent;
import android.database.Cursor;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import svetozarradic.ferit.hr.rma_project.database.UserDbHelper;
import svetozarradic.ferit.hr.rma_project.utils.PreferenceUtils;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.ivRegisterActivity)
    ImageView ivRegisterActivity;
    @BindView(R.id.btnLogIn)
    Button btnLogIn;
    @BindView(R.id.btnSignUp)
    Button btnSignUp;
    @BindView(R.id.checkBox)
    CheckBox checkBox;
    @BindView(R.id.textView)
    TextView textView;

    String email, password;

    UserDbHelper userDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        ButterKnife.bind(this);
        btnSignUp.setOnClickListener(this);
        btnLogIn.setOnClickListener(this);
        userDbHelper = new UserDbHelper(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setIcon(R.drawable.feritos);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        if (PreferenceUtils.getEmail(this) != null) {
            Intent intent = new Intent(LogInActivity.this, UserBodyParameters.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignUp:
                startActivity(new Intent(LogInActivity.this, RegisterActivity.class));
                break;
            case R.id.btnLogIn:
                if (validate()) {
                    if (checkBox.isChecked()) {
                        PreferenceUtils.saveEmail(email, this);
                        PreferenceUtils.savePassword(password, this);
                    }
                    startActivity(new Intent(LogInActivity.this, UserBodyParameters.class));
                }
                break;
        }
    }

    private boolean validate() {
        Boolean result = false;
        int size = 0;
        int countEmail = 0;
        int countPassword = 0;

        email = etEmail.getText().toString();
        password = etPassword.getText().toString();

        Cursor cu = userDbHelper.getUser();
        if (cu.getCount() == 0) {
            Toast.makeText(this, "You must first sign up!", Toast.LENGTH_SHORT).show();

        } else {
            while (cu.moveToNext()) {
                size++;
                if (email.equals(cu.getString(2)) && password.equals(cu.getString(3))) {
                    result = true;
                }
                if (!email.equals(cu.getString(2))) {
                    countEmail++;
                }
                if (!password.equals(cu.getString(3))) {
                    countPassword++;
                }
            }
            if (countEmail == size && countPassword == size) {
                Toast.makeText(this, "Incorrect email and password!", Toast.LENGTH_SHORT).show();
            } else if (countEmail == size || countEmail > countPassword) {
                Toast.makeText(this, "Incorrect email!", Toast.LENGTH_SHORT).show();
            } else if (countPassword == size || countEmail < countPassword) {
                Toast.makeText(this, "Incorrect password!", Toast.LENGTH_SHORT).show();
            }
        }

        cu.close();
        return result;
    }
}
