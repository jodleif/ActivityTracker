package no.hit.activitytracker.RestFul;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Jo on 2/12/2017.
 */
public class NetworkFragment extends Fragment {
    private static final String URL_KEY = "url";
    private static final String TAG = "NetworkFragment";
    private String urlString;
    private AsyncRequest asyncRequest;


    public static NetworkFragment getInstance(FragmentManager fragmentManager, String url) {
        NetworkFragment networkFragment = new NetworkFragment();
        Bundle args = new Bundle();
        args.putString(URL_KEY, url);
        networkFragment.setArguments(args);
        fragmentManager.beginTransaction().add(networkFragment, TAG).commit();
        System.out.println("INIT NETWORK FRAGMENT");
        return networkFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        urlString = getArguments().getString(URL_KEY);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // TODO handle callback
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // TODO: clear reference to callback
    }

    @Override
    public void onDestroy() {
        cancelRequest();
        super.onDestroy();
    }

    public void sendRequest() {
        cancelRequest();
        asyncRequest = new AsyncRequest();
        asyncRequest.execute(urlString);
    }

    private void cancelRequest() {
        if (asyncRequest != null) {
            asyncRequest.cancel(true);
        }
    }

    private class AsyncRequest extends AsyncTask<String, Void, String> {
        private String url;

        @Override
        protected String doInBackground(String... params) {
            System.out.println("ASYNCTASK DO IN BACKGROUND");
            if (params.length == 1) url = params[0];
            RestTransaction rest = new RestTransaction();
            return rest.makeRequest(url);
        }

        @Override
        protected void onPostExecute(String s) {
            // todo whatever
        }


    }

    class RestTransaction {
        public String makeRequest(String urlStr) {
            if (urlStr == null) urlStr = "https://rest.jooivind.com/commit";
            HttpsURLConnection conn = null;
            OutputStream os = null;
            InputStream is = null;
            String result = "";
            try {
                URL url = new URL(urlStr);
                conn = (HttpsURLConnection) url.openConnection();
                conn.setConnectTimeout(1000);
                conn.setReadTimeout(1000);
                conn.setDoInput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                System.out.println("Connecting to URL:" + url.toString());
                JSONObject request = JSONHelper.createUser();

                String messageBody = "{}"; // defaults to empty json

                if (request != null) {
                    messageBody = request.toString();
                    System.out.println(String.format("Making request %s", messageBody));
                }

                os = conn.getOutputStream();
                os.write(messageBody.getBytes());
                os.flush();
                conn.connect();
                int responseCode = conn.getResponseCode();
                if (responseCode == 200) {
                    is = conn.getInputStream();
                }
                if (is != null) {
                    result = readStream(is, 500);
                }

            } catch (Exception e) {
                StringWriter writer = new StringWriter();
                PrintWriter printWriter = new PrintWriter(writer);
                e.printStackTrace(printWriter);
                printWriter.flush();

                String stackTrace = writer.toString();
                String log = String.format("Error during rest request\n%s\n%s\n%s", e.getMessage(), e.getCause(), stackTrace);
                System.out.println(log);
                return log;
            } finally {
                if (os != null) {
                    try {
                        os.close();
                    } catch (Exception e) {
                        return "Exception when closing os";
                    }
                }
                if (is != null) {
                    try {
                        is.close();
                    } catch (Exception e) {
                        return "Exception when closing is";
                    }
                }
                if (conn != null) {
                    conn.disconnect();
                }
            }
            return result;
        }

        /**
         * Converts the contents of an InputStream to a String.
         */
        private String readStream(InputStream stream, int maxLength) throws IOException {
            String result = null;
            // Read InputStream using the UTF-8 charset.
            InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
            // Create temporary buffer to hold Stream data with specified max length.
            char[] buffer = new char[maxLength];
            // Populate temporary buffer with Stream data.
            int numChars = 0;
            int readSize = 0;
            while (numChars < maxLength && readSize != -1) {
                numChars += readSize;
                int pct = (100 * numChars) / maxLength;
                //publishProgress(DownloadCallback.Progress.PROCESS_INPUT_STREAM_IN_PROGRESS, pct);
                readSize = reader.read(buffer, numChars, buffer.length - numChars);
            }
            if (numChars != -1) {
                // The stream was not empty.
                // Create String that is actual length of response body if actual length was less than
                // max length.
                numChars = Math.min(numChars, maxLength);
                result = new String(buffer, 0, numChars);
            }
            return result;
        }
    }
}
