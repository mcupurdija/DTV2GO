package rs.multitelekom.dtv2go.ui.video;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CursorAdapter;
import android.widget.GridView;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.VideoView;
import rs.multitelekom.dtv2go.R;
import rs.multitelekom.dtv2go.ui.BaseActivity;

public class VideoActivity extends BaseActivity {

    public static final String NAME_EXTRA_KEY = "name_extra";
    public static final String VIDEO_URI_EXTRA_KEY = "video_uri_extra";

    private int mVideoLayout = 0;

    private VideoView mVideoView;

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
        mVideoView.setMediaController(new CustomMediaController(this));
        mVideoView.requestFocus();

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                // TODO
            }
        });

        CursorAdapter adapter = new CursorAdapter(this, null, 0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return null;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {

            }
        };
        GridView gridView = new GridView(this);
        gridView.setAdapter(adapter);
    }

    public void changeLayout(View view) {
        mVideoLayout++;
        if (mVideoLayout == 4) {
            mVideoLayout = 0;
        }
        switch (mVideoLayout) {
            case 0:
                mVideoLayout = VideoView.VIDEO_LAYOUT_ORIGIN;
//                view.setBackgroundResource(R.drawable.mediacontroller_sreen_size_100);
                break;
            case 1:
                mVideoLayout = VideoView.VIDEO_LAYOUT_SCALE;
//                view.setBackgroundResource(R.drawable.mediacontroller_screen_fit);
                break;
            case 2:
                mVideoLayout = VideoView.VIDEO_LAYOUT_STRETCH;
//                view.setBackgroundResource(R.drawable.mediacontroller_screen_size);
                break;
            case 3:
                mVideoLayout = VideoView.VIDEO_LAYOUT_ZOOM;
//                view.setBackgroundResource(R.drawable.mediacontroller_sreen_size_crop);

                break;
        }
        mVideoView.setVideoLayout(mVideoLayout, 0);
    }
}
