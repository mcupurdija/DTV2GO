package rs.multitelekom.dtv2go.ui.video;

import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

import rs.multitelekom.dtv2go.R;
import rs.multitelekom.dtv2go.ui.BaseActivity;

public class TestVideoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_video);

        String videoUri = getIntent().getExtras().getString("video_uri_extra", null);

        VideoView vidView = (VideoView)findViewById(R.id.myVideo);
        vidView.setVideoURI(Uri.parse(videoUri));
        vidView.start();
    }
}
