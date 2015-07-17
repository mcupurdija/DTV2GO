package rs.multitelekom.dtv2go.ws.request;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import org.springframework.http.ContentCodingType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.nio.charset.Charset;
import java.util.Collections;

import rs.multitelekom.dtv2go.util.AppConstants;
import rs.multitelekom.dtv2go.ws.model.Registration;

public class PutRegistrationRequest extends SpringAndroidSpiceRequest<String> {

    private String username;
    private Registration registration;

    public PutRegistrationRequest(String username, Registration registration) {
        super(String.class);
        this.username = username;
        this.registration = registration;
    }

    @Override
    public String loadDataFromNetwork() throws Exception {

        String url = String.format(AppConstants.REGISTRATION_URL, username);

        HttpHeaders requestHeaders = new HttpHeaders();
        MediaType mediaType = new MediaType("application", "json", Charset.forName("UTF-8"));
        requestHeaders.setContentType(mediaType);
        requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        requestHeaders.setAcceptEncoding(ContentCodingType.GZIP);

        HttpEntity<Registration> requestEntity = new HttpEntity<>(registration, requestHeaders);

        ResponseEntity<String> responseEntity = getRestTemplate().exchange(url, HttpMethod.PUT, requestEntity, String.class);

        return responseEntity.getBody();
    }
}
