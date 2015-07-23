package rs.multitelekom.dtv2go.ui.video;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.simple.SmallBinaryRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import rs.multitelekom.dtv2go.R;
import rs.multitelekom.dtv2go.ui.BaseActivity;

public class VideoActivity extends BaseActivity {

    public static final String TAG = "VideoActivity";
    public static final String NAME_EXTRA_KEY = "name_extra";
    public static final String VIDEO_URI_EXTRA_KEY = "video_uri_extra";
    public static final String SUBTITLE_URI_EXTRA_KEY = "subtitle_uri_extra";

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
        String subtitleUri = getIntent().getExtras().getString(SUBTITLE_URI_EXTRA_KEY, null);

        if (!LibsChecker.checkVitamioLibs(this) || TextUtils.isEmpty(videoUri)) {
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

        if (!TextUtils.isEmpty(subtitleUri)) {
            getSpiceManager().execute(new SmallBinaryRequest(subtitleUri), new FileDownloadRequestListener());
        }
    }

    private class FileDownloadRequestListener implements RequestListener<InputStream> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            if (spiceException.getMessage() != null) {
                Log.e(TAG, spiceException.getMessage());
            }
        }

        @Override
        public void onRequestSuccess(InputStream is) {

            try {

                File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
                File file = new File(dir, "tmp.srt");

                FileOutputStream fos = new FileOutputStream(file);

                byte[] buffer = new byte[1024];
                int len1;
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);
                }
                fos.close();
                is.close();

                if (mVideoView != null) {
                    mVideoView.addTimedTextSource(file.getPath());
                    mVideoView.setTimedTextShown(true);
                }
            } catch (Exception e) {
                if (e.getMessage() != null) {
                    Log.e(TAG, e.getMessage());
                }
            }
        }
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
