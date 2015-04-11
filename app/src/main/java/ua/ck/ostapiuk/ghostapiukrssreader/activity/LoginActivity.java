package ua.ck.ostapiuk.ghostapiukrssreader.activity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.gson.Gson;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ua.ck.ostapiuk.ghostapiukrssreader.R;
import ua.ck.ostapiuk.ghostapiukrssreader.entity.User;
import ua.ck.ostapiuk.ghostapiukrssreader.util.constant.Constants;
import ua.ck.ostapiuk.ghostapiukrssreader.util.parser.FacebookUserParser;
import ua.ck.ostapiuk.ghostapiukrssreader.util.parser.GooglePlusUserParser;

/**
 * Created by Vova on 10.04.2015.
 */
public class LoginActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    private UiLifecycleHelper uiLifecycleHelper;
    private static final int RC_SIGN_IN = 0;
    private GoogleApiClient mGoogleApiClient;
    private boolean mIntentInProgress;
    private boolean mSignInClicked;
    private ConnectionResult mConnectionResult;
    private User currentUser;
    private SharedPreferences mPreferences;
    private ImageView mUserImage;
    private TextView mUsername;
    private TextView mLocation;
    private TextView mBirthday;
    private TextView mLink;
    private TextView mLanguages;
    private SignInButton mSignInButton;
    private LoginButton mFacebookLoginButton;
    private Button mSignOutButton;
    private Session.StatusCallback facebookCallback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onFacebookSessionStateChange(session, state, exception);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mPreferences = getSharedPreferences(Constants.PREFERENCE_NAME
            , Context.MODE_PRIVATE);
        currentUser = new User();
        uiLifecycleHelper = new UiLifecycleHelper(this,facebookCallback);
        uiLifecycleHelper.onCreate(savedInstanceState);
        mFacebookLoginButton = (LoginButton)findViewById(R.id.facebook_login);
        mFacebookLoginButton.setReadPermissions("public_profile");
        mUserImage = (ImageView)findViewById(R.id.user_image);
        mUsername = (TextView)findViewById(R.id.username);
        mBirthday = (TextView)findViewById(R.id.birthday);
        mLocation = (TextView)findViewById(R.id.location);
        mLanguages = (TextView)findViewById(R.id.languages);
        mLink = (TextView)findViewById(R.id.link);
        mSignInButton =(SignInButton)findViewById(R.id.google_login);
        mSignInButton.setOnClickListener(this);
        mSignOutButton = (Button)findViewById(R.id.sign_out_button);
        mSignOutButton.setOnClickListener(this);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(Plus.API)
            .addScope(Plus.SCOPE_PLUS_LOGIN)
            .build();


    }
    private void onFacebookSessionStateChange
        (Session session, SessionState state, Exception exception) {
        if (session.isOpened()) {
            Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
                @Override
                public void onCompleted(GraphUser user, Response response) {
//                    SharedPreferences.Editor editor = mPreferences.edit();
//                    Gson gson = new Gson();
                    currentUser = (new FacebookUserParser()).parse(user);
//                    String jsonUser = gson.toJson(currentUser);
//                    editor.putString(Constants.USER_KEY, jsonUser);
//                    editor.apply();
                    mSignInButton.setVisibility(View.GONE);
                    mUserImage.setVisibility(View.VISIBLE);
                    if (currentUser.getImage()!=null) {
                        Log.i("Login", currentUser.getImage());
                        Picasso.with(mUserImage.getContext())
                            .load(currentUser.getImage()).into(mUserImage);
                    }
                    mUsername.setVisibility(View.VISIBLE);
                    mUsername.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
                    if (currentUser.getLocation()!=null){
                        mLocation.setVisibility(View.VISIBLE);
                        mLocation.setText("Location :" + " "+ currentUser.getLocation());
                    }
                    if (currentUser.getLink()!=null){
                        mLink.setVisibility(View.VISIBLE);
                        mLink.setText("Link :"+" "+currentUser.getLink());
                    }
                    if (currentUser.getBirthday()!=null){
                        mBirthday.setVisibility(View.VISIBLE);
                        mBirthday.setText("Birthday :" + " " + currentUser.getBirthday());
                    }
                    if (currentUser.getLanguages()!=null){
                    if (currentUser.getLanguages().size()>0){
                        for (String language : currentUser.getLanguages()) {
                            mLanguages.append(language + "<b>");
                        }
                    }
                }
                }
            });
        } else if (session.isClosed()){
            mSignInButton.setVisibility(View.VISIBLE);
           hideUserInformationView();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            mIntentInProgress = false;
            if (resultCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
        uiLifecycleHelper.onActivityResult(requestCode, resultCode, data);

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        uiLifecycleHelper.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        uiLifecycleHelper.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        Session session = Session.getActiveSession();
        if (session != null &&
            (session.isOpened()) || (session.isClosed())
            ) {
            onFacebookSessionStateChange(session, session.getState(), null);
        }
        uiLifecycleHelper.onResume();

    }

    @Override
    public void onConnected(Bundle bundle) {
        mSignInClicked = false;
        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient)!=null){
            Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            currentUser = (new GooglePlusUserParser()).parse(currentPerson);
            mSignInButton.setVisibility(View.GONE);
            mFacebookLoginButton.setVisibility(View.GONE);
            mSignOutButton.setVisibility(View.VISIBLE);
            mUserImage.setVisibility(View.VISIBLE);
            if (currentUser.getImage()!=null) {
                Picasso.with(mUserImage.getContext())
                    .load(currentUser.getImage()).into(mUserImage);
            }
            mUsername.setVisibility(View.VISIBLE);
            mUsername.setText(currentUser.getName());
            if (currentUser.getLocation()!=null){
                mLocation.setVisibility(View.VISIBLE);
                mLocation.setText("Location :" + " " + currentUser.getLocation());
            }
            if (currentUser.getLink()!=null){
                mLink.setVisibility(View.VISIBLE);
                mLink.setText("Link :"+" "+currentUser.getLink());
            }
            if (currentUser.getBirthday()!=null){
                mBirthday.setVisibility(View.VISIBLE);
                mBirthday.setText("Birthday :" + " " + currentUser.getBirthday());
            }

        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (!mIntentInProgress && connectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                startIntentSenderForResult(connectionResult.getResolution().getIntentSender(),
                    RC_SIGN_IN, null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }

        if (!mIntentInProgress) {
            mConnectionResult = connectionResult;
            if (mSignInClicked) {
                resolveSignInError();
            }
        }
    }

    private void resolveSignInError() {
        if (mConnectionResult != null) {
            if (mConnectionResult.hasResolution()) {
                try {
                    mIntentInProgress = true;
                    startIntentSenderForResult(mConnectionResult.getResolution().getIntentSender(),
                        RC_SIGN_IN, null, 0, 0, 0);
                } catch (IntentSender.SendIntentException e) {
                    // The intentwas canceled before it was sent.  Return to the default
                    // state and attempt to connect to get an updated ConnectionResult.
                    mIntentInProgress = false;
                    mGoogleApiClient.connect();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.google_login && !mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            mGoogleApiClient.connect();
            resolveSignInError();
        } else
        if (v.getId()==R.id.sign_out_button){
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            hideUserInformationView();
            mFacebookLoginButton.setVisibility(View.VISIBLE);
            mSignInButton.setVisibility(View.VISIBLE);
            mSignOutButton.setVisibility(View.GONE);
        }
    }
    private void hideUserInformationView(){
        mUserImage.setVisibility(View.GONE);
        mUsername.setVisibility(View.GONE);
        mLocation.setVisibility(View.GONE);
        mLink.setVisibility(View.GONE);
        mLanguages.setVisibility(View.GONE);
        mBirthday.setVisibility(View.GONE);
    }
}
