package in.tosc.bookd;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

/**
 * Created by prempal on 3/4/15.
 */
public class ApplicationWrapper extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "Qkf0ETa2dogBX3ot1BgSswy09PR6fAK6vJEMGcof", "KqV8lTovHNQQPuZ819qzCHK617uXvJIkWpxaqLkf");
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
    }
}