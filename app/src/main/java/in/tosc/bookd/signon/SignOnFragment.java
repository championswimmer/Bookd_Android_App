package in.tosc.bookd.signon;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;

import java.util.Arrays;

import in.tosc.bookd.MainActivity;
import in.tosc.bookd.ParseTables;
import in.tosc.bookd.R;

/**
 * Created by prempal on 3/4/15.
 */
public class SignOnFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "SignOnFragment";
    private EditText mUsername;
    private EditText mPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParseUser pUser = ParseUser.getCurrentUser();
        if ((pUser != null)
                && (pUser.isAuthenticated())
                && (pUser.getSessionToken() != null)) {
            Log.d(TAG, pUser.getUsername() + pUser.getSessionToken());
            Intent i = new Intent(getActivity(), MainActivity.class);
            startActivity(i);
            getActivity().finish();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_signon, container, false);
        mUsername = (EditText) rootView.findViewById(R.id.et_username);
        mPassword = (EditText) rootView.findViewById(R.id.et_password);
        Button signup = (Button) rootView.findViewById(R.id.btn_signup);
        Button signin = (Button) rootView.findViewById(R.id.btn_signin);
        Button fb_login = (Button) rootView.findViewById(R.id.btn_facebook);
        Button twitter_login = (Button) rootView.findViewById(R.id.btn_twitter);

        signup.setOnClickListener(this);
        signin.setOnClickListener(this);
        fb_login.setOnClickListener(this);
        twitter_login.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_signup:
                signUp();
                break;
            case R.id.btn_signin:
                signIn();
                break;
            case R.id.btn_facebook:
                signInFB();
                break;
            case R.id.btn_twitter:
                signInTwitter();
                break;
        }
    }

    private void signInTwitter() {
        ParseTwitterUtils.logIn(getActivity(), new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (parseUser == null) {
                    Log.d(TAG, "Uh oh. The user cancelled the Twitter login.");
                } else if (parseUser.isNew()) {
                    Log.d(TAG, "User signed up and logged in through Twitter!");
                    showSignupDataFragment(null);
                } else {
                    Log.d(TAG, "User logged in through Twitter!");
                    Intent i = new Intent(getActivity(), MainActivity.class);
                    startActivity(i);
                    getActivity().finish();
                }
            }
        });
    }

    private void signInFB() {
        ParseFacebookUtils.logInWithReadPermissionsInBackground(getActivity(), Arrays.asList("email", "user_about_me"), new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (parseUser.isNew()) {
                    showSignupDataFragment(null);
                } else {
                    Intent i = new Intent(getActivity(), MainActivity.class);
                    startActivity(i);
                    getActivity().finish();
                }
            }
        });
    }

    private void signIn() {
        ParseUser.logInInBackground(
                mUsername.getText().toString(),
                mPassword.getText().toString(),
                new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {
                        if (parseUser != null) {
                            Intent i = new Intent(getActivity(), MainActivity.class);
                            startActivity(i);
                        } else {
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Login failed")
                                    .setCancelable(true)
                                    .setMessage("Try again!")
                                    .show();
                        }
                    }
                }
        );
    }

    private void signUp() {
        Bundle b = new Bundle();
        b.putString(ParseTables.Users.USERNAME, mUsername.getText().toString());
        b.putString(ParseTables.Users.PASSWORD, mPassword.getText().toString());
        showSignupDataFragment(b);
    }

    public SignUpFragment showSignupDataFragment(Bundle b) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        SignUpFragment signUpFragment = SignUpFragment.newInstance(b);
        transaction.replace(R.id.signon_container, signUpFragment).commit();
        return signUpFragment;
    }
}