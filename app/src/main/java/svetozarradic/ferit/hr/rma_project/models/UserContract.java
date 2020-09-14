package svetozarradic.ferit.hr.rma_project.models;

import android.provider.BaseColumns;

public class UserContract {
    public UserContract() {
    }

    public static class UserTable implements BaseColumns {
        public UserTable() {

        }

        public static final String TABLE_NAME = "user_signup_data";
        public static final String COLUMN_USERNAME = "user_signup_name";
        public static final String COLUMN_EMAIL = "user_signup_email";
        public static final String COLUMN_PASSWORD = "user_signup_password";
    }
}
