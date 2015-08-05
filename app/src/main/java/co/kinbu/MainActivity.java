package co.kinbu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jedabero.kinbu.R;

public class MainActivity extends Activity {

    public static String SERVER_URL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AlertDialog.Builder adBuilder = new AlertDialog.Builder(this);
        View dvRoot = getLayoutInflater().inflate(R.layout.server_ip_dialog, null);
        adBuilder.setTitle("Enter the ip").setView(dvRoot);
        final TextView tvByte3 = (TextView) dvRoot.findViewById(R.id.byte3);
        final TextView tvByte4 = (TextView) dvRoot.findViewById(R.id.byte4);
        adBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String byte3 = tvByte3.getText().toString();
                String byte4 = tvByte4.getText().toString();
                StringBuilder sb = new StringBuilder("http://").append(getString(R.string.half_ip));
                sb.append(byte3).append(".").append(byte4).append("/");
                sb.append(getString(R.string.app_name_lc)).append("/");
                SERVER_URL = sb.toString();
            }
        });
        adBuilder.show();

    }

    public void startSignUp(View view) {
        startActivity(new Intent(getApplicationContext(), SignUpActivity.class));

    }

    public void startSignIn(View view) {
        startActivity(new Intent(getApplicationContext(), SignInActivity.class));
    }


}
