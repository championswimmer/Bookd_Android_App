package in.tosc.bookd;


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
