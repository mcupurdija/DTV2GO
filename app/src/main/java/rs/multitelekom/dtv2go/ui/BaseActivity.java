package rs.multitelekom.dtv2go.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.octo.android.robospice.SpiceManager;

import rs.multitelekom.dtv2go.ws.XmlSpiceService;

public class BaseActivity extends AppCompatActivity {

    private SpiceManager spiceManager = new SpiceManager(XmlSpiceService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();

        spiceManager.start(this);
    }

    @Override
    protected void onStop() {
        if (spiceManager.isStarted()) {
            spiceManager.shouldStop();
        }
        super.onStop();
    }

    public SpiceManager getSpiceManager() {
        return spiceManager;
    }

}
