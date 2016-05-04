package malexan.ru.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import maxalexan.ru.project.R;

public class RegistrationUser extends AppCompatActivity {

    private Button button = null;
    public static String LOG_TAG = "my_log";
    private AutoCompleteTextView edituname=null;
    private EditText editpassword = null;
    private EditText editpasswordconf=null;
    private String editunameStr;
    private String editpasswordStr;
    private String editpasswordconfStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_user);
        Button button = (Button) findViewById(R.id.register);
        edituname = (AutoCompleteTextView) findViewById(R.id.reg_email);
        editpassword = (EditText) findViewById(R.id.pword);
        editpasswordconf = (EditText) findViewById(R.id.password1);
        Intent gettingInt = getIntent();
        String logEMail = gettingInt.getStringExtra("login");
        edituname.setText(logEMail.toString());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editunameStr = edituname.getText().toString();
                editpasswordStr = editpassword.getText().toString();
                editpasswordconfStr = editpasswordconf.getText().toString();
                View focusView = null;
                // Check for a valid password, if the user entered one.
                if (!TextUtils.isEmpty(editpasswordStr) && !isPasswordValid(editpasswordStr)) {
                    editpassword.setError(getString(R.string.error_invalid_password));
                    focusView = editpassword;
                }else {
                    if (!TextUtils.isEmpty(editunameStr)&& !isEmailValid(editunameStr)) {
                    edituname.setError(getString(R.string.error_invalid_email));
                    Toast.makeText(getApplication(), R.string.error_invalid_email, Toast.LENGTH_LONG).show();
                } else if (editpasswordStr.equals(editpasswordconfStr) && !TextUtils.isEmpty(editpasswordStr)) {
                    Intent intent = new Intent();
                    intent.putExtra("name", editunameStr);
                    intent.putExtra("pass", editpasswordconfStr);
                    Log.d(LOG_TAG, "Логин и пароль впередаем из регистрации " + editunameStr + " и " + editpasswordconfStr);
                    setResult(RESULT_OK, intent);
                   finish();
                 } else {
                        Toast.makeText(getApplication(), R.string.error_incorrect_password, Toast.LENGTH_LONG).show();
                    }
                        }
            }
        });
   }
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
}
