package rs.multitelekom.dtv2go.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.PendingRequestListener;

import rs.multitelekom.dtv2go.R;
import rs.multitelekom.dtv2go.ui.BaseActivity;
import rs.multitelekom.dtv2go.ui.MainActivity;
import rs.multitelekom.dtv2go.util.DialogUtils;
import rs.multitelekom.dtv2go.ws.model.Registration;
import rs.multitelekom.dtv2go.ws.request.PutRegistrationRequest;

public class RegistrationActivity extends BaseActivity {

    public static final String NAME_EXTRA = "name";
    public static final String USERNAME_EXTRA = "username";
    public static final String PASSWORD_EXTRA = "password";
    public static final String DEV_ID_EXTRA = "devId";

    private String username, password, devId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Bundle extras = getIntent().getExtras();
        String name = extras.getString(NAME_EXTRA);
        username = extras.getString(USERNAME_EXTRA);
        password = extras.getString(PASSWORD_EXTRA);
        devId = extras.getString(DEV_ID_EXTRA);

        TextView tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        Button bAccept = (Button) findViewById(R.id.bAccept);
        Button bRefuse = (Button) findViewById(R.id.bRefuse);

        if (TextUtils.isEmpty(name)) {
            tvWelcome.setText(getString(R.string.welcome));
        } else {
            tvWelcome.setText(String.format("%s, %s", getString(R.string.welcome), name));
        }

        bAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PutRegistrationRequest putRegistrationRequest = new PutRegistrationRequest(username, new Registration(password, devId, "android", "new", "1"));
                getSpiceManager().execute(putRegistrationRequest, PutRegistrationRequest.class.getSimpleName(), DurationInMillis.ONE_MINUTE, new PutRegistrationRequestListener());
            }
        });

        bRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        getSpiceManager().addListenerIfPending(String.class, PutRegistrationRequest.class.getSimpleName(), new PutRegistrationRequestListener());
    }

    private class PutRegistrationRequestListener implements PendingRequestListener<String> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            DialogUtils.showBasicInfoDialog(RegistrationActivity.this, R.string.error_title, spiceException.getMessage());
        }

        @Override
        public void onRequestSuccess(String s) {
            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
        }

        @Override
        public void onRequestNotFound() {
        }
    }
}
