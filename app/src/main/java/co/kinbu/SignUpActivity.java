package co.kinbu;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jedabero.kinbu.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jedabero on 7/09/14.
 */
public class SignUpActivity extends Activity {

    private ProgressBar pbsu;
    private LinearLayout signUpForm;
    private AsyncTask<String, Integer, Boolean> registrador;

    private String fullname;
    private String email;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        pbsu = (ProgressBar) findViewById(R.id.progress_bar_sign_up);
        pbsu.setMax(5);
        signUpForm = (LinearLayout) findViewById(R.id.sign_up_form);

        registrador = new AsyncTask<String, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(String... params) {
                boolean success = false;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(MainActivity.SERVER_URL);

                    pbsu.incrementProgressBy(1);

                    List<NameValuePair> postParams = new ArrayList<NameValuePair>();
                    String secureAndroidId =
                            Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                    postParams.add(new BasicNameValuePair("secure_android_id", secureAndroidId));
                    postParams.add(new BasicNameValuePair("build_id", Build.ID));
                    postParams.add(new BasicNameValuePair("build_display", Build.DISPLAY));
                    postParams.add(new BasicNameValuePair("build_product", Build.PRODUCT));
                    postParams.add(new BasicNameValuePair("build_device", Build.DEVICE));
                    postParams.add(new BasicNameValuePair("build_board", Build.BOARD));
                    postParams.add(new BasicNameValuePair("build_cpu_abi", Build.CPU_ABI));
                    postParams.add(new BasicNameValuePair("build_cpu_abi2", Build.CPU_ABI2));
                    postParams.add(new BasicNameValuePair("build_manufacturer", Build.MANUFACTURER));
                    postParams.add(new BasicNameValuePair("build_brand", Build.BRAND));
                    postParams.add(new BasicNameValuePair("build_model", Build.MODEL));
                    postParams.add(new BasicNameValuePair("build_bootloader", Build.BOOTLOADER));
                    postParams.add(new BasicNameValuePair("build_hardware", Build.HARDWARE));
                    postParams.add(new BasicNameValuePair("build_serial", Build.SERIAL));
                    postParams.add(new BasicNameValuePair("build_version_incremental", Build.VERSION.INCREMENTAL));
                    postParams.add(new BasicNameValuePair("build_version_release", Build.VERSION.RELEASE));
                    postParams.add(new BasicNameValuePair("build_version_sdk_int", String.valueOf(Build.VERSION.SDK_INT)));
                    postParams.add(new BasicNameValuePair("build_version_codename", Build.VERSION.CODENAME));
                    postParams.add(new BasicNameValuePair("build_fingerprint", Build.FINGERPRINT));

                    postParams.add(new BasicNameValuePair("action", "RegisterNewUser"));
                    postParams.add(new BasicNameValuePair("username", username));
                    postParams.add(new BasicNameValuePair("email", email));
                    postParams.add(new BasicNameValuePair("password", password));
                    postParams.add(new BasicNameValuePair("fullname", fullname));

                    pbsu.incrementProgressBy(1);

                    httpPost.setEntity(new UrlEncodedFormEntity(postParams));
                    pbsu.incrementProgressBy(1);
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    pbsu.incrementProgressBy(1);
                    HttpEntity httpEntity = httpResponse.getEntity();
                    String user_id = EntityUtils.toString(httpEntity);
                    pbsu.incrementProgressBy(1);
                    success = true;
                } catch (UnsupportedEncodingException uee) {
                    uee.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return success;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {

            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (result) {
                    Toast.makeText(getApplicationContext(), getString(R.string.account_created), Toast.LENGTH_SHORT);
                } else {
                    signUpForm.setVisibility(View.VISIBLE);
                    pbsu.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), getString(R.string.account_not_created), Toast.LENGTH_SHORT);
                }
            }
        };

    }

    public void createAccount(View view) {
        fullname = ((EditText) findViewById(R.id.user_full_name)).getText().toString();
        email = ((EditText) findViewById(R.id.user_email)).getText().toString();
        username = ((EditText) findViewById(R.id.user_name)).getText().toString();
        password = ((EditText) findViewById(R.id.user_password)).getText().toString();

        signUpForm.setVisibility(View.GONE);
        pbsu.setVisibility(View.VISIBLE);

        registrador.execute();

    }

}
