package com.ready.sport.inmatch.Fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ready.sport.inmatch.Activity.MainActivity;
import com.ready.sport.inmatch.Activity.SplashActivity;
import com.ready.sport.inmatch.R;
import com.ready.sport.inmatch.RealmClass.MatchModel;
import com.ready.sport.inmatch.RealmClass.PlayersModel;
import com.ready.sport.inmatch.RealmClass.UserModel;
import com.ready.sport.inmatch.util.AutoCompleteTextViewPlus;
import com.ready.sport.inmatch.util.ConfigUrls;
import com.ready.sport.inmatch.util.Constants;
import com.ready.sport.inmatch.util.EditTextPlus;
import com.ready.sport.inmatch.util.ToastCustom;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.realm.Realm;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * Created by l.martelloni on 25/08/2017.
 */

public class LoginFragment extends Fragment {
    private Realm realm;
    private UserModel model;
    private String token;
    private PlayersModel pl;
    private MatchModel ma;
    private boolean IsSuccessSave = false;
    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextViewPlus mEmailView;
    private EditTextPlus mPasswordView;
    private View mProgressView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        mEmailView = (AutoCompleteTextViewPlus) rootView.findViewById(R.id.email);
        //populateAutoComplete();

        mPasswordView = (EditTextPlus) rootView.findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) rootView.findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mProgressView = rootView.findViewById(R.id.login_progress);

        realm = Realm.getDefaultInstance();

        return rootView;
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        //getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (getActivity().checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            /*mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });*/

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            /*mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);*/
        }
    }

    /*@Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), getActivity().ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }*/

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(getActivity(),
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

//            for (String credential : DUMMY_CREDENTIALS) {
//                String[] pieces = credential.split(":");
//                if (pieces[0].equals(mEmail)) {
//                    // Account exists, return true if the password matches.
//                    return pieces[1].equals(mPassword);
//                }
//            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {
                try {
                    // Simulate network access.
                    //Thread.sleep(2000);

                    AndroidNetworking.post(ConfigUrls.BASE_URL + ConfigUrls.TOKEN)
                            .addBodyParameter("grant_type", "password")
                            .addBodyParameter("password", mPassword)
                            .addBodyParameter("email", mEmail)
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    try {
                                        token = response.get("access_token").toString();
                                        String username = response.get("userName").toString();
                                        Constants.TOKEN = token;
                                        model = new UserModel();
                                        model.setEmailUser(mEmail);
                                        model.setToken(token);
                                        model.setUserName(username);
                                        realm.executeTransaction(new Realm.Transaction() {
                                            @Override
                                            public void execute(Realm realm) {
                                                try {
                                                    realm.copyToRealmOrUpdate(model);
                                                } catch (Exception e) {
                                                    Log.e("TAG", "ADD_USER: " + e.getMessage(), e);
                                                    Toast.makeText(getContext(), "Errore di salvataggio. Riprova", Toast.LENGTH_SHORT).show();
                                                } finally {
                                                    Log.d("TAG", "ADD_USER: FINALLY");
                                                    //IsSuccessSave = true;
                                                    onSuccessRealm();
                                                }

                                            }
                                        });

                                        showProgress(false);


                                    } catch (Exception e) {
                                        Log.e("ErrorParse", e.getMessage());
                                    }

                                    // do anything with response
                                }

                                @Override
                                public void onError(ANError error) {
                                    try {
                                        showProgress(false);
                                        JSONObject str = new JSONObject(error.getErrorBody().toString());
                                        //Toast.makeText(getContext(), "Errore: " + str.get("error_description"), Toast.LENGTH_SHORT).show();
                                        ToastCustom toast = new ToastCustom(getContext(), getResources().getDrawable(R.drawable.ic_error_cloud),"Errore: " + str.get("Message").toString());
                                        toast.show();
                                    } catch (Exception e) {
                                        showProgress(false);
                                        ToastCustom toast = new ToastCustom(getContext(), getResources().getDrawable(R.drawable.ic_error_cloud),getString(R.string.error_default));
                                        toast.show();
                                        Log.e("ErrorPost", e.getMessage());
                                    }
                                    // handle error
                                }
                            });

                } catch (Exception e) {
                    Log.e("ErrorCallNetwork", e.getMessage());
                }



            } else {
                showProgress(false);
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }

        public void onSuccessRealm() {
            AndroidNetworking.get(ConfigUrls.BASE_URL + ConfigUrls.USER_DETAIL)
                    .addHeaders("Authorization", "bearer " + token)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {

                                model.setUserName(response.get("UserName").toString());
                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        try {
                                            realm.copyToRealmOrUpdate(model);
                                        } catch (Exception e) {
                                            Log.e("TAG", "ADD_USER: " + e.getMessage(), e);
                                            Toast.makeText(getContext(), "Errore di salvataggio. Riprova", Toast.LENGTH_SHORT).show();
                                        } finally {
                                            Log.d("TAG", "ADD_USER: FINALLY");
                                            //IsSuccessSave = true;
                                            onSuccessRealm();
                                        }

                                    }
                                });
                                JSONArray players = response.getJSONArray("players");
                                JSONArray matchs = response.getJSONArray("matchs");
                                for (int i = 0; i < players.length(); i++) {
                                    try{
                                        JSONObject player = players.getJSONObject(i);
                                        Gson gson = new GsonBuilder().create();
                                        pl = gson.fromJson(player.toString(), PlayersModel.class);

                                        realm.executeTransaction(new Realm.Transaction() {
                                            @Override
                                            public void execute(Realm realm) {
                                                try {
                                                    realm.copyToRealmOrUpdate(pl);
                                                } catch (Exception e) {
                                                    Log.e("TAG", "ADD_PLAYER: " + e.getMessage(), e);
                                                } finally {
                                                    Log.d("TAG", "ADD_PLAYER: FINALLY");

                                                }

                                            }
                                        });

                                    }catch(Exception e){
                                        Log.e("ErrorParse", e.getMessage());
                                    }
                                }
                                for (int i = 0; i < matchs.length(); i++) {
                                    try{
                                        JSONObject match = matchs.getJSONObject(i);
                                        Gson gson = new GsonBuilder().create();
                                        ma = gson.fromJson(match.toString(), MatchModel.class);
                                        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
                                        try {
                                            Date date = dateFormat.parse(match.getString("d_StartDateUtc"));//You will get date object relative to server/client timezone wherever it is parsed
                                            DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS"); //If you need time just put specific format for time like 'HH:mm:ss'
                                            String dateStr = formatter.format(date);
                                            ma.setStartDateUtc(dateStr);
                                        } catch (Exception e) {
                                            Log.e("Error Data:", e.getMessage());
                                        }
                                        realm.executeTransaction(new Realm.Transaction() {
                                            @Override
                                            public void execute(Realm realm) {
                                                try {
                                                    realm.copyToRealmOrUpdate(ma);
                                                } catch (Exception e) {
                                                    Log.e("TAG", "ADD_PLAYER: " + e.getMessage(), e);
                                                } finally {
                                                    Log.d("TAG", "ADD_PLAYER: FINALLY");

                                                }

                                            }
                                        });

                                    }catch(Exception e){
                                        Log.e("ErrorParse", e.getMessage());
                                    }
                                }

                                realm.close();
                                startActivity(new Intent(getActivity(), MainActivity.class));
                                getActivity().finish();
                            } catch (Exception e) {
                                Log.e("ErrorParse", e.getMessage());
                            }

                            // do anything with response
                        }

                        @Override
                        public void onError(ANError error) {
                            try {
                                showProgress(false);
                                JSONObject str = new JSONObject(error.getErrorBody().toString());
                                //Toast.makeText(getContext(), "Errore: " + str.get("error_description"), Toast.LENGTH_SHORT).show();
                                ToastCustom toast = new ToastCustom(getContext(), getResources().getDrawable(R.drawable.ic_error_cloud),"Errore: " + str.get("Message").toString());
                                toast.show();
                            } catch (Exception e) {
                                showProgress(false);
                                ToastCustom toast = new ToastCustom(getContext(), getResources().getDrawable(R.drawable.ic_error_cloud),getString(R.string.error_default));
                                toast.show();
                                Log.e("ErrorPost", e.getMessage());
                            }
                            // handle error
                        }
                    });
        }
    }


}
