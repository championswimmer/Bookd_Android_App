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
            bookObject.author = jsonObject.getString("author");
            bookObject.category = jsonObject.getString("category");
            bookObject.image = jsonObject.getString("image");
            bookObject.isbn = jsonObject.getString("isbn");
            bookObject.publisher = jsonObject.getString("publisher");
            bookObject.rating = jsonObject.getString("rating");
            bookObject.summary = jsonObject.getString("summary");
            bookObject.title = jsonObject.getString("title");
        } catch (Exception e){

        }
        return bookObject;
    }
}
