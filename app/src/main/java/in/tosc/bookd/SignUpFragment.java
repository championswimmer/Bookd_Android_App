package in.tosc.bookd;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;


public class SignUpFragment extends Fragment {

    Bundle mBundle;

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
        return rootView;
    }

    private void pushToParse(){

    }


}
