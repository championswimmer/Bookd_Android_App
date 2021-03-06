package in.tosc.bookd.signon;


import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.drawee.view.SimpleDraweeView;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.UUID;

import in.tosc.bookd.MainActivity;
import in.tosc.bookd.ParseTables;
import in.tosc.bookd.R;
import in.tosc.bookd.Utils;
import in.tosc.bookd.ui.MaterialEditText;

import static in.tosc.bookd.R.id.et_passwd;


public class SignUpFragment extends Fragment {

    Bundle mBundle;
    MaterialEditText mName;
    MaterialEditText mPhone;
    private Typeface typeface;
    private Typeface secondTypface;

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
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Lobster-Regular.ttf");
        secondTypface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/RobotoCondensed-Regular.ttf");
        mPhone = (MaterialEditText) rootView.findViewById(R.id.et_mobile);
        mName = (MaterialEditText) rootView.findViewById(R.id.et_name);
        MaterialEditText username = (MaterialEditText) rootView.findViewById(R.id.et_email);
        MaterialEditText password = (MaterialEditText) rootView.findViewById(et_passwd);
        mPhone.setTypeface(typeface);
        mName.setTypeface(typeface);
        username.setTypeface(typeface);
        password.setTypeface(typeface);
        SimpleDraweeView profile = (SimpleDraweeView) rootView.findViewById(R.id.profile);
        if(mBundle.getString(ParseTables.Users.IMAGE)!=null){
            Uri profileUri = Uri.parse(mBundle.getString(ParseTables.Users.IMAGE));
            profile.setImageURI(profileUri);
            Utils.addUriToCache("profile",profileUri);

        }
        SimpleDraweeView cover = (SimpleDraweeView) rootView.findViewById(R.id.cover);
        if(mBundle.getString(ParseTables.Users.COVER)!=null){
            Uri coverUri = Uri.parse(mBundle.getString(ParseTables.Users.COVER));
            cover.setImageURI(coverUri);
            Utils.addUriToCache("cover",coverUri);
        }
        if(mBundle.get(ParseTables.Users.PASSWORD) == null){
            username.setVisibility(View.GONE);
            password.setVisibility(View.GONE);
        }
        else{
            username.setText(mBundle.getString(ParseTables.Users.USERNAME));
            password.setText(mBundle.getString(ParseTables.Users.PASSWORD));
        }
        if(mBundle.getString(ParseTables.Users.NAME) != null) {
            mName.setText(mBundle.getString(ParseTables.Users.NAME));
            Utils.addUriToCache("name", Uri.parse(mBundle.getString(ParseTables.Users.NAME)));
        }
        Button parsePush = (Button) rootView.findViewById(R.id.btn_parsepush);
        parsePush.setTypeface(secondTypface);
        parsePush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushToParse();
            }
        });
        return rootView;
    }

    private void pushToParse(){
        if(validateData()){
            ParseUser user = ParseUser.getCurrentUser();
            if(user == null)
                user = new ParseUser();
            if (mBundle.getString(ParseTables.Users.USERNAME) != null) {
                user.setUsername(mBundle.getString(ParseTables.Users.USERNAME));
            }
            if (mBundle.getString(ParseTables.Users.EMAIL) != null) {
                user.setEmail(mBundle.getString(ParseTables.Users.EMAIL));
            }
            if (mBundle.getString(ParseTables.Users.PASSWORD) != null) {
                user.setPassword(mBundle.getString(ParseTables.Users.PASSWORD));
            } else {
                user.setPassword(UUID.randomUUID().toString().substring(0,6));
            }
            user.put(ParseTables.Users.NAME, mName.getText().toString());
            user.put(ParseTables.Users.MOBILE, mPhone.getText().toString());
            user.put(ParseTables.Users.FULLY_REGISTERED, true);
            if (mBundle.getString(ParseTables.Users.COVER) != null) {
                user.put(ParseTables.Users.COVER, mBundle.getString(ParseTables.Users.COVER));
            }
            if (mBundle.getString(ParseTables.Users.IMAGE) != null) {
                user.put(ParseTables.Users.IMAGE, mBundle.getString(ParseTables.Users.IMAGE));
            }
            if (user.getSessionToken() != null) {
                user.saveInBackground(new SaveCallback() {
                    @Override
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
            } else {
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
    }

    private boolean validateData(){
        if(Utils.isEditTextEmpty(mName)){
            mName.setError("Required");
            return false;
        }
        if(Utils.isEditTextEmpty(mPhone)){
            mPhone.setError("Required");
            return false;
        }
        return true;
    }


}
