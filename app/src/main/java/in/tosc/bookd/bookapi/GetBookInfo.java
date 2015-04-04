package in.tosc.bookd.bookapi;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

/**
 * Created by championswimmer on 4/4/15.
 */
public class GetBookInfo extends AsyncTask<String, Void, BookObject> {

    //TODO: This AsyncTask takes a ISBN and gets back a BookObject

    Context context;
    String baseURL = "http://tosc.in:9095/bookd/api/?isbn=";
    String response;
    BookObject bookObject;
    public GetBookInfo(Context context) {
        this.context = context;
    }

    @Override
    protected BookObject doInBackground(String... isbn) {
        String url = baseURL + isbn[0];
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);

        try {
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            response = httpclient.execute(httpget, responseHandler);
            Log.d("Response", response);
            JSONObject jsonObject = new JSONObject(response);
            bookObject = new BookObject();
            bookObject.setAuthor(jsonObject.getString("author"));
            bookObject.setCategory(jsonObject.getString("category"));
            bookObject.setImage(jsonObject.getString("image"));
            bookObject.setIsbn(jsonObject.getString("isbn"));
            bookObject.setPublisher(jsonObject.getString("publisher"));
            bookObject.setRating(jsonObject.getString("rating"));
            bookObject.setSummary(jsonObject.getString("summary"));
            bookObject.setTitle(jsonObject.getString("title"));
        } catch (Exception e){

        }
        return bookObject;
    }
}
