package no.hit.activitytracker.RestFul;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import java.io.UnsupportedEncodingException;

/**
 * Created by Jo on 2/15/2017.
 * <p>
 * Extension of a Volley JsonRequest
 * It's purpose is to be able to post JSON requests (as Strings) and
 * accept raw strings as return values
 */

public class JsonStringRequest extends JsonRequest<String> {
    public JsonStringRequest(int method, String url, String requestBody, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString =
                    new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(jsonString,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }
}
