package rs.multitelekom.dtv2go.ui;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.GridView;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.VideoView;
import rs.multitelekom.dtv2go.CustomMediaController;
import rs.multitelekom.dtv2go.R;

public class VideoActivity extends BaseActivity {

    private int mVideoLayout = 0;

    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!LibsChecker.checkVitamioLibs(this))
            return;
        setContentView(R.layout.activity_video);

        mVideoView = (VideoView) findViewById(R.id.surface_view);
        mVideoView.setVideoURI(Uri.parse("http://techslides.com/demos/sample-videos/small.mp4"));
        mVideoView.setMediaController(new CustomMediaController(this));
        mVideoView.requestFocus();

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setPlaybackSpeed(1.0f);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_video, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
