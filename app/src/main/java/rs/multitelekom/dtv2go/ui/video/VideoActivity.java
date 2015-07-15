package rs.multitelekom.dtv2go.ui.video;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import rs.multitelekom.dtv2go.R;
import rs.multitelekom.dtv2go.ui.BaseActivity;

public class VideoActivity extends BaseActivity {

    public static final String NAME_EXTRA_KEY = "name_extra";
    public static final String VIDEO_URI_EXTRA_KEY = "video_uri_extra";

    private int mVideoLayout = 1;
    private boolean mute = false;

    private VideoView mVideoView;

    private MaterialDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        String channelName = getIntent().getExtras().getString(NAME_EXTRA_KEY, null);
        String videoUri = getIntent().getExtras().getString(VIDEO_URI_EXTRA_KEY, null);

        if (!LibsChecker.checkVitamioLibs(this) || videoUri == null) {
            finish();
        }

        mVideoView = (VideoView) findViewById(R.id.surface_view);
        mVideoView.setVideoURI(Uri.parse(videoUri));
        mVideoView.setMediaController(new CustomMediaController(this, channelName));
        mVideoView.requestFocus();

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                progressDialog.dismiss();
            }
        });
        mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                progressDialog.dismiss();
                return false;
            }
        });

        progressDialog = new MaterialDialog.Builder(this).title("Kanal se učitava").content("Molimo sačekajte...").progress(true, 0).show();
    }

    private void closeActivity() {
        mVideoView.stopPlayback();
        finish();
    }

    private void mute(View view) {
        mute = !mute;
        if (mute) {
            mVideoView.setVolume(0, 0);
            view.setSelected(true);
        } else {
            mVideoView.setVolume(1, 1);
            view.setSelected(false);
        }
    }

    private void changeLayout(View view) {
        mVideoLayout++;
        if (mVideoLayout == 4) {
            mVideoLayout = 0;
        }
        switch (mVideoLayout) {
            case 0:
                mVideoLayout = VideoView.VIDEO_LAYOUT_ORIGIN;
                ((Button) view).setText("Origin");
                break;
            case 1:
                mVideoLayout = VideoView.VIDEO_LAYOUT_SCALE;
                ((Button) view).setText("Scale");
                break;
            case 2:
                mVideoLayout = VideoView.VIDEO_LAYOUT_STRETCH;
                ((Button) view).setText("Stretch");
                break;
            case 3:
                mVideoLayout = VideoView.VIDEO_LAYOUT_ZOOM;
                ((Button) view).setText("Zoom");
                break;
        }
        mVideoView.setVideoLayout(mVideoLayout, 0);
    }

    private class CustomMediaController extends MediaController {

        private Context mContext;
        private String mChannelName;

        public CustomMediaController(Context context, String channelName) {
            super(context);
            mContext = context;
            mChannelName = channelName;
        }

        @Override
        protected View makeControllerView() {
            View view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.mediacontroller_custom, this);
            ((TextView) findViewById(R.id.tvChannelName)).setText(mChannelName);

            ImageButton bStop = (ImageButton) view.findViewById(R.id.bStop);
            bStop.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    closeActivity();
                }
            });

            ImageButton bMute = (ImageButton) view.findViewById(R.id.bMute);
            bMute.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mute(v);
                }
            });

            Button bAspect = (Button) view.findViewById(R.id.bAspect);
            bAspect.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeLayout(v);
                }
            });

            return view;
        }
    }
}
