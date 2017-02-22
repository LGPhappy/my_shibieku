package com.xdja.zdsb;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        PackageInfo packageInfo;
        String version;
        try {

            packageInfo = getApplication().getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0);

            version = packageInfo.versionName;

        } catch (NameNotFoundException e) {
            e.printStackTrace();
            version = "version: 1.5.0";
        }

        TextView xdja_version = (TextView)findViewById(R.id.xdja_version);
        xdja_version.setText("version: " + version);  
    }
    
}
