package rs.fon.dontforgetyourmedicine.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import rs.fon.dontforgetyourmedicine.R;
import rs.fon.dontforgetyourmedicine.activities.menu.ReminderActivity;
import rs.fon.dontforgetyourmedicine.data.DBOpenHelper;
import rs.fon.dontforgetyourmedicine.data.managers.UserManager;
import rs.fon.dontforgetyourmedicine.data.model.UserModel;
import rs.fon.dontforgetyourmedicine.util.MedicineConstants;
import rs.fon.dontforgetyourmedicine.util.SharedPrefs;

/**
 * Created by radovan.bogdanic on 3/6/2017.
 */

public class LoginActivity extends AppCompatActivity {

    ProgressDialog mProgressDlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        if(SharedPrefs.getPreferences().isVisitor()) {
            autoSignIn();
        }

        findViewById(R.id.btnSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHomeActivity();
                SharedPrefs.getPreferences().setVisitor(true);
            }
        });
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == RC_SIGN_IN) {
//
//                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//                GoogleSignInAccount account = result.getSignInAccount();
//
//            final FirebaseDatabase database = FirebaseDatabase.getInstance();
//            DatabaseReference ref = database.getReference("server/saving-data");
//
//                try {
//                    String name = account.getDisplayName();
//                    String email = account.getEmail();
//                    String profPic = account.getPhotoUrl().toString();
//                    user = new UserModel(name, email, profPic);
//                    user.setVisitor(false);
//                    UserManager.insert(user, DBOpenHelper.getSQLiteDatabase(this));
//
//                    DatabaseReference userRef = ref.child("users");
//
//                    Map<String, UserModel> users = new HashMap<>();
//                    users.put("logged", new UserModel(name,email,profPic));
//                    userRef.setValue(users);
//
//                    Intent intent = new Intent(this, ReminderActivity.class);
//
//                   intent.putExtra(MedicineConstants.LOGIN_NAME, name);
//                  intent.putExtra(MedicineConstants.LOGIN_MAIL, email);
//                    intent.putExtra(MedicineConstants.LOGIN_IMAGE, profPic);
//
//                    startActivity(intent);
//                    setResult(RESULT_OK);
//                    dismissWaitDialog();
//                    finish();
//                } catch (Exception e) {
//                    Toast.makeText(this, getString(R.string.login_failed), Toast.LENGTH_LONG).show();
//                    dismissWaitDialog();
//                }
//        }
//    }

    public void showWaitDialog(String title, String msg) {
        if (this.mProgressDlg == null) {
            this.mProgressDlg = new ProgressDialog(this);
            this.mProgressDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            if (!TextUtils.isEmpty(title)) this.mProgressDlg.setTitle(title);
            if (!TextUtils.isEmpty(msg)) this.mProgressDlg.setMessage(msg);
            this.mProgressDlg.setIndeterminate(true);
            this.mProgressDlg.setCancelable(false);
            this.mProgressDlg.show();
        }
    }

    public void dismissWaitDialog() {
        if (this.mProgressDlg != null) {
            if (this.mProgressDlg.isShowing()) {
                this.mProgressDlg.dismiss();
            }
            this.mProgressDlg = null;
        }
    }


    private void autoSignIn() {
        showWaitDialog("", getString(R.string.login_sign));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismissWaitDialog();
                startHomeActivity();
            }
        }, 1000);
    }

    private void startHomeActivity() {
        Intent i = new Intent(LoginActivity.this,ReminderActivity.class);
        startActivity(i);
        finish();
    }

}
