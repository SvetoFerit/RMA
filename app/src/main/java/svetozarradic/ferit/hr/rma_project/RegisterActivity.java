package svetozarradic.ferit.hr.rma_project;

import android.content.Intent;
import android.database.Cursor;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import svetozarradic.ferit.hr.rma_project.database.UserDbHelper;
import svetozarradic.ferit.hr.rma_project.utils.PreferenceUtils;
import svetozarradic.ferit.hr.rma_project.models.User;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.tvSignUp)
    TextView tvSignUp;
    @BindView(R.id.etUsernameSignUpActivity)
    EditText etUsernameSignUpActivity;
    @BindView(R.id.etEmailSignUpActivity)
    EditText etEmailSignUpActivity;
    @BindView(R.id.etPasswordSignUpActivity)
    EditText etPasswordSignUpActivity;
    @BindView(R.id.etConfirmPassword)
    EditText etConfirmPassword;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.tvAlreadySignUp)
    TextView tvAlreadySignUp;

    UserDbHelper userDbHelper;

    String username, email, password, confirmpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        button.setOnClickListener(this);
        tvAlreadySignUp.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        userDbHelper = new UserDbHelper(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        userDbHelper.close();
    }

    @Override
    public void onClick(View v) {
        username = etUsernameSignUpActivity.getText().toString();
        email = etEmailSignUpActivity.getText().toString();
        password = etPasswordSignUpActivity.getText().toString();

        switch (v.getId()) {
            case R.id.button:
                if (validate()) {
                    boolean adduser = false;
                    int count = 0;
                    User user = new User(username, email, password);
                    Cursor cursor = userDbHelper.getUser();
                    if (cursor.getCount() == 0) {
                        adduser = userDbHelper.addUser(user);
                    } else {
                        while (cursor.moveToNext()) {

                            if (email.equals(cursor.getString(2))) {
                                count++;
                            }
                        }
                    }
                    if (count == 0) {
                        adduser = userDbHelper.addUser(user);
                    } else {
                        Toast.makeText(this, "User already exists!", Toast.LENGTH_SHORT).show();
                        break;
                    }

                    if (adduser) {
                        Toast.makeText(this, "You are successfully signed up", Toast.LENGTH_SHORT).show();
                        PreferenceUtils.clearHeightPreference(RegisterActivity.this);
                        PreferenceUtils.clearCounterPreference(RegisterActivity.this);
                        startActivity(new Intent(RegisterActivity.this, LogInActivity.class));
                    } else {
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.tvAlreadySignUp:
                startActivity(new Intent(this, LogInActivity.class));
                break;
        }
    }

    private Boolean validate() {
        Boolean result = false;

        username = etUsernameSignUpActivity.getText().toString();
        email = etEmailSignUpActivity.getText().toString();
        password = etPasswordSignUpActivity.getText().toString();
        confirmpassword = etConfirmPassword.getText().toString();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmpassword.isEmpty()) {
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        } else if (!confirmpassword.equals(password)) {
            Toast.makeText(this, "Incorrect confirmed password", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }
        return result;
    }
}
