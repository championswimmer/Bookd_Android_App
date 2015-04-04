package in.tosc.bookd.signon;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.drawee.view.SimpleDraweeView;
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
        SimpleDraweeView profile = (SimpleDraweeView) rootView.findViewById(R.id.profile);
        if(mBundle.getString(ParseTables.Users.IMAGE)!=null){
            Log.d("sfg","dsgsdgsdg");
            Uri profileUri = Uri.parse(mBundle.getString(ParseTables.Users.IMAGE));
            profile.setImageURI(profileUri);
        }
        SimpleDraweeView cover = (SimpleDraweeView) rootView.findViewById(R.id.cover);
        if(mBundle.getString(ParseTables.Users.COVER)!=null){
            Uri coverUri = Uri.parse(mBundle.getString(ParseTables.Users.COVER));
            cover.setImageURI(coverUri);
        }
        if(mBundle.get(ParseTables.Users.USERNAME) == null){
            username.setVisibility(View.GONE);
            password.setVisibility(View.GONE);
        }
        else{
            username.setText(mBundle.getString(ParseTables.Users.USERNAME));
            password.setText(mBundle.getString(ParseTables.Users.PASSWORD));
        }
        if(mBundle.getString(ParseTables.Users.NAME) != null)
            mName.setText(mBundle.getString(ParseTables.Users.NAME));
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
        user.put(ParseTables.Users.COVER,mBundle.getString(ParseTables.Users.COVER));
        user.put(ParseTables.Users.IMAGE,mBundle.getString(ParseTables.Users.IMAGE));
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
