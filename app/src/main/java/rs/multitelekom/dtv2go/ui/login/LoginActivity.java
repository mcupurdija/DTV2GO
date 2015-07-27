package rs.multitelekom.dtv2go.ui.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.PendingRequestListener;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import rs.multitelekom.dtv2go.R;
import rs.multitelekom.dtv2go.ui.BaseActivity;
import rs.multitelekom.dtv2go.ui.MainActivity;
import rs.multitelekom.dtv2go.util.AppConstants;
import rs.multitelekom.dtv2go.util.DevIdGenerator;
import rs.multitelekom.dtv2go.util.DialogUtils;
import rs.multitelekom.dtv2go.util.SharedPreferencesUtils;
import rs.multitelekom.dtv2go.util.ToastUtils;
import rs.multitelekom.dtv2go.ws.model.Device1;
import rs.multitelekom.dtv2go.ws.model.Device2;
import rs.multitelekom.dtv2go.ws.model.Device3;
import rs.multitelekom.dtv2go.ws.model.Devices;
import rs.multitelekom.dtv2go.ws.model.Login;
import rs.multitelekom.dtv2go.ws.request.GetLoginRequest;

public class LoginActivity extends BaseActivity {

    private String devId, username, password;

    private EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPassword.setTypeface(Typeface.DEFAULT);
        etPassword.setTransformationMethod(new PasswordTransformationMethod());
        Button bLogin = (Button) findViewById(R.id.bLogin);
        Button bContact = (Button) findViewById(R.id.bContact);

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = etUsername.getText().toString();
                password = etPassword.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    ToastUtils.displayToast(LoginActivity.this, R.string.error_username_required);
                    etUsername.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    ToastUtils.displayToast(LoginActivity.this, R.string.error_password_required);
                    etPassword.requestFocus();
                    return;
                }

                GetLoginRequest getLoginRequest = new GetLoginRequest(username, password);
                getSpiceManager().execute(getLoginRequest, GetLoginRequest.class.getSimpleName(), DurationInMillis.ONE_MINUTE, new GetLoginRequestListener());
            }
        });

        bContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(String.format(AppConstants.CONTACT_URL, devId))));
            }
        });

        devId = SharedPreferencesUtils.getDevId(this);
        if (devId == null) {
            String realDevId = getDeviceId();
            String deviceSerial = getDeviceSerial();
            String code = DevIdGenerator.getCode();
            if (realDevId != null) {
                devId = realDevId;
            } else if (deviceSerial != null && !deviceSerial.equals("unknown")) {
                devId = deviceSerial;
            } else {
                devId = code;
            }
            SharedPreferencesUtils.saveDevId(this, devId);
        }

        String savedUsername = SharedPreferencesUtils.getUserId(this);
        if (savedUsername != null) {
            etUsername.setText(savedUsername);
            etPassword.requestFocus();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        getSpiceManager().addListenerIfPending(Login.class, GetLoginRequest.class.getSimpleName(), new GetLoginRequestListener());
    }

    private class GetLoginRequestListener implements PendingRequestListener<Login> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {

            etPassword.setText("");

            if (spiceException.getCause() instanceof HttpClientErrorException) {
                HttpClientErrorException exception = (HttpClientErrorException) spiceException.getCause();
                if (exception.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
                    DialogUtils.showBasicInfoDialog(LoginActivity.this, R.string.error_title, "Pogrešno korisničko ime i/ili lozinka.");
                    return;
                }
            }
            DialogUtils.showBasicInfoDialog(LoginActivity.this, R.string.error_title, spiceException.getMessage());
        }

        @Override
        public void onRequestSuccess(Login login) {
            if (login != null) {
                if (login.getActive() == 1) {

                    Devices devices = login.getDevices();
                    if (devices != null) {

                        Device1 device1 = devices.getDevice1();
                        Device2 device2 = devices.getDevice2();
                        Device3 device3 = devices.getDevice3();
                        if (device1 != null && device2 != null && device3 != null) {

                            String dev1Id = device1.getId();
                            String dev2Id = device2.getId();
                            String dev3Id = device3.getId();

                            String dev1Imei = device1.getImei();
                            String dev2Imei = device2.getImei();
                            String dev3Imei = device3.getImei();

                            if (dev1Id != null && dev2Id != null && dev3Id != null) {
                                if (dev1Imei.equals(devId) || dev2Imei.equals(devId) || dev3Imei.equals(devId)) {
                                    saveUsernameAndStartMain();
                                } else {
                                    DialogUtils.showBasicInfoDialog(LoginActivity.this, R.string.error_title, R.string.error_all_accounts_used);
                                }
                            } else {
                                if (dev1Imei != null && dev1Imei.equals(devId)) {
                                    saveUsernameAndStartMain();
                                    return;
                                }
                                if (dev2Imei != null && dev2Imei.equals(devId)) {
                                    saveUsernameAndStartMain();
                                    return;
                                }
                                if (dev3Imei != null && dev3Imei.equals(devId)) {
                                    saveUsernameAndStartMain();
                                    return;
                                }
                                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                                intent.putExtra(RegistrationActivity.NAME_EXTRA, login.getName());
                                intent.putExtra(RegistrationActivity.USERNAME_EXTRA, login.getId());
                                intent.putExtra(RegistrationActivity.PASSWORD_EXTRA, password);
                                intent.putExtra(RegistrationActivity.DEV_ID_EXTRA, devId);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            DialogUtils.showBasicInfoDialog(LoginActivity.this, R.string.error_title, R.string.error_login_disabled);
                        }
                    } else {
                        DialogUtils.showBasicInfoDialog(LoginActivity.this, R.string.error_title, R.string.error_login_disabled);
                    }
                } else {
                    DialogUtils.showBasicInfoDialog(LoginActivity.this, R.string.error_title, R.string.error_login_disabled);
                }
            } else {
                DialogUtils.showBasicInfoDialog(LoginActivity.this, R.string.error_title, R.string.error_login_invalid);
            }
        }

        @Override
        public void onRequestNotFound() {
        }
    }

    private void saveUsernameAndStartMain() {
        SharedPreferencesUtils.saveUserId(LoginActivity.this, username);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    private String getDeviceId() {
        TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        return tManager.getDeviceId();
    }

    private String getDeviceSerial() {
        try {
            return (String) Build.class.getField("SERIAL").get(null);
        } catch (IllegalArgumentException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        } catch (NoSuchFieldException e) {
            return null;
        }
    }
}
