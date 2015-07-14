package rs.multitelekom.dtv2go.ws.request;

import android.content.ContentValues;
import android.content.Context;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rs.multitelekom.dtv2go.db.DatabaseContract;
import rs.multitelekom.dtv2go.util.DateUtils;
import rs.multitelekom.dtv2go.util.SharedPreferencesUtils;
import rs.multitelekom.dtv2go.ws.model.Channel;
import rs.multitelekom.dtv2go.ws.model.GetChannelsResponse;

public class GetChannelsRequest extends SpringAndroidSpiceRequest<Integer> {

    private Context context;
    private String url;

    public GetChannelsRequest(Context context, String url) {
        super(Integer.class);
        this.context = context;
        this.url = url;
    }

    @Override
    public Integer loadDataFromNetwork() throws Exception {

        ResponseEntity<GetChannelsResponse> responseEntity = getRestTemplate().exchange(url, HttpMethod.GET, null, GetChannelsResponse.class);

        GetChannelsResponse getChannelsResponse = responseEntity.getBody();
        int result = 0;
        if (getChannelsResponse != null && getChannelsResponse.getChannels().size() > 0) {
            List<Channel> channels = getChannelsResponse.getChannels();
            if (channels != null && channels.size() > 0) {
                List<ContentValues> contentValuesList = new ArrayList<>();
                ContentValues contentValues;
                for (Channel channel : channels) {
                    contentValues = new ContentValues();
                    contentValues.put(DatabaseContract.Channels.CHANNEL_ID, channel.getId());
                    contentValues.put(DatabaseContract.Channels.CHANNEL_NAME, channel.getCaption());
                    contentValues.put(DatabaseContract.Channels.CHANNEL_VIDEO_URI, channel.getStreaming_url());
                    contentValues.put(DatabaseContract.Channels.CHANNEL_ICON_URI, channel.getIcon_path());
                    contentValuesList.add(contentValues);
                }
                // clear all channels
                context.getContentResolver().delete(DatabaseContract.Channels.CONTENT_URI, null, null);
                // insert new channels (there is no unique identifier from the server to update)
                result = context.getContentResolver().bulkInsert(DatabaseContract.Channels.CONTENT_URI, contentValuesList.toArray(new ContentValues[contentValuesList.size()]));
                SharedPreferencesUtils.saveLastSyncDate(context, DateUtils.formatDate(new Date()));
            }
        }
        return result;
    }
}
