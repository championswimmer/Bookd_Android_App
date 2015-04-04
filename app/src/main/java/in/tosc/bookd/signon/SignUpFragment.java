package in.tosc.bookd.signon;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import in.tosc.bookd.MainActivity;
import in.tosc.bookd.ParseTables;
import in.tosc.bookd.R;

import static in.tosc.bookd.R.id.et_passwd;


public class SignUpFragment extends Fragment {

    Bundle mBundle;
    EditText mName;
    EditText mPhone;

    public static SignUpFragment newInstance(Bundle bundle) {
        SignUpFragment fragment = new SignUpFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBundle = getArguments();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_sign_up, container, false);
        mPhone = (EditText) rootView.findViewById(R.id.et_mobile);
        mName = (EditText) rootView.findViewById(R.id.et_name);
        EditText username = (EditText) rootView.findViewById(R.id.et_email);
        EditText password = (EditText) rootView.findViewById(et_passwd);
        if(mBundle == null){
            username.setVisibility(View.GONE);
            password.setVisibility(View.GONE);
        }
        else{
            username.setText(mBundle.getString(ParseTables.Users.USERNAME));
            password.setText(mBundle.getString(ParseTables.Users.PASSWORD));
        }

        Button parsePush = (Button) rootView.findViewById(R.id.btn_parsepush);
        parsePush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushToParse();
            }
        });
        return rootView;
    }

    private void pushToParse(){
        ParseUser user = ParseUser.getCurrentUser();
        if(user == null)
            user = new ParseUser();
        user.setEmail(mBundle.getString(ParseTables.Users.USERNAME));
        user.setUsername(mBundle.getString(ParseTables.Users.USERNAME));
        user.setPassword(mBundle.getString(ParseTables.Users.PASSWORD));
        user.put(ParseTables.Users.NAME, mName.getText().toString());
        user.put(ParseTables.Users.MOBILE, mPhone.getText().toString());
        user.put(ParseTables.Users.FULLY_REGISTERED, true);
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Intent i = new Intent(getActivity(), MainActivity.class);
                    startActivity(i);
                    getActivity().finish();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }


}
