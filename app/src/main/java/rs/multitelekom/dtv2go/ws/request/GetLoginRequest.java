package rs.multitelekom.dtv2go.ws.request;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import org.springframework.http.ContentCodingType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Collections;

import rs.multitelekom.dtv2go.util.AppConstants;
import rs.multitelekom.dtv2go.ws.model.Login;

public class GetLoginRequest extends SpringAndroidSpiceRequest<Login> {

    String username, password;

    public GetLoginRequest(String username, String password) {
        super(Login.class);
        this.username = username;
        this.password = password;
    }

    @Override
    public Login loadDataFromNetwork() throws Exception {

        username = URLEncoder.encode(username, "UTF-8");
        password = URLEncoder.encode(password, "UTF-8");

        HttpHeaders requestHeaders = new HttpHeaders();
        MediaType mediaType = new MediaType("application", "json", Charset.forName("UTF-8"));
        requestHeaders.setContentType(mediaType);
        requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        requestHeaders.setAcceptEncoding(ContentCodingType.GZIP);

        HttpEntity<Void> requestEntity = new HttpEntity<>(null, requestHeaders);

        String url = String.format(AppConstants.LOGIN_URL, username, password);
        ResponseEntity<Login> responseEntity = getRestTemplate().exchange(url, HttpMethod.GET, requestEntity, Login.class);

        return responseEntity.getBody();
    }
}
