package rs.multitelekom.dtv2go.ui.video;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import io.vov.vitamio.widget.MediaController;
import rs.multitelekom.dtv2go.R;

public class CustomMediaController extends MediaController {

    private Context mContext;

    public CustomMediaController(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected View makeControllerView() {
        return ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.mediacontroller_custom, this);
    }
}