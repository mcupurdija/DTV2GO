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
import rs.multitelekom.dtv2go.ws.model.ChannelsData;
import rs.multitelekom.dtv2go.ws.model.Movie;
import rs.multitelekom.dtv2go.ws.model.MoviesData;

public class GetDataRequest extends SpringAndroidSpiceRequest<Integer> {

    private Context context;

    public GetDataRequest(Context context) {
        super(Integer.class);
        this.context = context;
    }

    @Override
    public Integer loadDataFromNetwork() throws Exception {

        ResponseEntity<ChannelsData> channelsResponseEntity = getRestTemplate().exchange(AppConstants.CHANNELS_SD_URL, HttpMethod.GET, null, ChannelsData.class);

        // clear all data (there is no unique identifier from the server to update)
        context.getContentResolver().delete(DatabaseContract.Channels.CONTENT_URI, null, null);

        int result = 0;
        ChannelsData channelsData = channelsResponseEntity.getBody();
        if (channelsData != null) {
            List<Channel> channels = channelsData.getChannels();
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
                SharedPreferencesUtils.saveLastSyncDate(context, DateUtils.formatDate(new Date()));
            }
        }

        channelsResponseEntity = getRestTemplate().exchange(AppConstants.CHANNELS_HD_URL, HttpMethod.GET, null, ChannelsData.class);
        channelsData = channelsResponseEntity.getBody();
        if (channelsData != null) {
            List<Channel> channels = channelsData.getChannels();
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
                SharedPreferencesUtils.saveLastSyncDate(context, DateUtils.formatDate(new Date()));
            }
        }

        ResponseEntity<MoviesData> moviesResponseEntity = getRestTemplate().exchange(AppConstants.MOVIES_URL, HttpMethod.GET, null, MoviesData.class);

        MoviesData moviesData = moviesResponseEntity.getBody();
        if (moviesData != null) {
            List<Movie> movies = moviesData.getMovies();
            if (movies != null && movies.size() > 0) {
                List<ContentValues> contentValuesList = new ArrayList<>();
                ContentValues contentValues;
                for (Movie movie : movies) {
                    contentValues = new ContentValues();
                    contentValues.put(DatabaseContract.Movies.MOVIE_TITLE, movie.getTitle());
                    contentValues.put(DatabaseContract.Movies.MOVIE_DESCRIPTION, movie.getDescription());
                    contentValues.put(DatabaseContract.Movies.MOVIE_DURATION, movie.getDuration());
                    contentValues.put(DatabaseContract.Movies.MOVIE_GENRE, movie.getGenre());
                    contentValues.put(DatabaseContract.Movies.MOVIE_POSTER, movie.getPoster());
                    contentValues.put(DatabaseContract.Movies.MOVIE_VIDEO_URI, movie.getUrl());
                    contentValues.put(DatabaseContract.Movies.MOVIE_SUBTITLE, movie.getSubtitle());
                    contentValuesList.add(contentValues);
                }
                context.getContentResolver().delete(DatabaseContract.Movies.CONTENT_URI, null, null);
                result += context.getContentResolver().bulkInsert(DatabaseContract.Movies.CONTENT_URI, contentValuesList.toArray(new ContentValues[contentValuesList.size()]));
            }
        }

        return result;
    }
}