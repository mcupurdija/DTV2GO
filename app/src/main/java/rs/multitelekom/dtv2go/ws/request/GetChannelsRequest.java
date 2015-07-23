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
import rs.multitelekom.dtv2go.util.AppConstants;
import rs.multitelekom.dtv2go.util.DateUtils;
import rs.multitelekom.dtv2go.util.SharedPreferencesUtils;
import rs.multitelekom.dtv2go.ws.model.Channel;
import rs.multitelekom.dtv2go.ws.model.GetChannelsResponse;

public class GetChannelsRequest extends SpringAndroidSpiceRequest<Integer> {

    private Context context;

    public GetChannelsRequest(Context context) {
        super(Integer.class);
        this.context = context;
    }

    @Override
    public Integer loadDataFromNetwork() throws Exception {

        ResponseEntity<GetChannelsResponse> responseEntity = getRestTemplate().exchange(AppConstants.CHANNELS_SD_URL, HttpMethod.GET, null, GetChannelsResponse.class);

        // clear all channels (there is no unique identifier from the server to update)
        context.getContentResolver().delete(DatabaseContract.Channels.CONTENT_URI, null, null);

        int result = 0;
        GetChannelsResponse getChannelsResponse = responseEntity.getBody();
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
                    contentValues.put(DatabaseContract.Channels.QUALITY, AppConstants.QUALITY_SD);
                    contentValuesList.add(contentValues);
                }
                result += context.getContentResolver().bulkInsert(DatabaseContract.Channels.CONTENT_URI, contentValuesList.toArray(new ContentValues[contentValuesList.size()]));
            }
        }

        responseEntity = getRestTemplate().exchange(AppConstants.CHANNELS_HD_URL, HttpMethod.GET, null, GetChannelsResponse.class);
        getChannelsResponse = responseEntity.getBody();
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
                    contentValues.put(DatabaseContract.Channels.QUALITY, AppConstants.QUALITY_HD);
                    contentValuesList.add(contentValues);
                }
                result += context.getContentResolver().bulkInsert(DatabaseContract.Channels.CONTENT_URI, contentValuesList.toArray(new ContentValues[contentValuesList.size()]));
            }
        }

        SharedPreferencesUtils.saveLastSyncDate(context, DateUtils.formatDate(new Date()));
        return result;
    }
}
