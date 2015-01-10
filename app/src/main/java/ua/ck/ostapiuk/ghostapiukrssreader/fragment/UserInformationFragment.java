package ua.ck.ostapiuk.ghostapiukrssreader.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import ua.ck.ostapiuk.ghostapiukrssreader.R;
import ua.ck.ostapiuk.ghostapiukrssreader.entity.User;
import ua.ck.ostapiuk.ghostapiukrssreader.util.constant.Constants;

/**
 * Created by Vova on 09.01.2015.
 */
public class UserInformationFragment extends DialogFragment {
    private TextView mNameTextView;
    private TextView mUserNameTextView;
    private TextView mBirthDayTextView;
    private TextView mLanguagesTextView;
    private TextView mLocationTextView;
    private TextView mLinkTextView;
    private ImageView mUserImage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_information_fragment, container, false);
        mNameTextView = (TextView) view.findViewById(R.id.name);
        mUserNameTextView = (TextView) view.findViewById(R.id.username);
        mBirthDayTextView = (TextView) view.findViewById(R.id.birthday);
        mLanguagesTextView = (TextView) view.findViewById(R.id.languages);
        mLocationTextView = (TextView) view.findViewById(R.id.location);
        mLinkTextView = (TextView) view.findViewById(R.id.link);
        mUserImage = (ImageView) view.findViewById(R.id.user_image);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.user_information_fragment, null);
        mNameTextView = (TextView) view.findViewById(R.id.name);
        mUserNameTextView = (TextView) view.findViewById(R.id.username);
        mBirthDayTextView = (TextView) view.findViewById(R.id.birthday);
        mLanguagesTextView = (TextView) view.findViewById(R.id.languages);
        mLocationTextView = (TextView) view.findViewById(R.id.location);
        mLinkTextView = (TextView) view.findViewById(R.id.link);
        mUserImage = (ImageView) view.findViewById(R.id.user_image);

        return super.onCreateDialog(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences preferences = getActivity().getSharedPreferences(Constants.PREFERENCE_NAME
                , Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonObject = preferences.getString(Constants.USER_KEY, "");
        if (jsonObject != null) {
            User user = gson.fromJson(jsonObject, User.class);
            mNameTextView.setText("Name:" + user.getName());
            mBirthDayTextView.setText("BirthDay:" + user.getBirthday());
            mLinkTextView.setText("Link:" + user.getLink());
            mUserNameTextView.setText("Username" + user.getUsername());
            if (user.getLanguages() != null) {
                for (String language : user.getLanguages()) {
                    mLanguagesTextView.append(language + "<b>");
                }
                if (user.getImage() != null) {
                    Picasso.with(getActivity()).load(user.getImage()).into(mUserImage);
                }
            }
            mLocationTextView.setText("Location:" + user.getLocation());

        }
    }
}