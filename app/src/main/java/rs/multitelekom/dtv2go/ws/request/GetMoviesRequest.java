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
import rs.multitelekom.dtv2go.ws.model.GetMoviesResponse;
import rs.multitelekom.dtv2go.ws.model.Movie;

public class GetMoviesRequest extends SpringAndroidSpiceRequest<Integer> {

    private Context context;

    public GetMoviesRequest(Context context, String url) {
        super(Integer.class);
        this.context = context;
    }

    @Override
    public Integer loadDataFromNetwork() throws Exception {

        ResponseEntity<GetMoviesResponse> responseEntity = getRestTemplate().exchange(AppConstants.MOVIES_URL, HttpMethod.GET, null, GetMoviesResponse.class);

        GetMoviesResponse getMoviesResponse = responseEntity.getBody();
        int result = 0;
        if (getMoviesResponse != null && getMoviesResponse.getMovies().size() > 0) {
            List<Movie> movies = getMoviesResponse.getMovies();
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
                // clear all channels
                context.getContentResolver().delete(DatabaseContract.Movies.CONTENT_URI, null, null);
                // insert new channels (there is no unique identifier from the server to update)
                result = context.getContentResolver().bulkInsert(DatabaseContract.Movies.CONTENT_URI, contentValuesList.toArray(new ContentValues[contentValuesList.size()]));
                SharedPreferencesUtils.saveLastMoviesSyncDate(context, DateUtils.formatDate(new Date()));
            }
        }
        return result;
    }
}
