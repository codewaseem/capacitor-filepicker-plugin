package com.codewaseem.filepickerplugin;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import com.codewaseem.utils.FileUtils;
import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;

import java.io.File;

import static android.app.Activity.RESULT_OK;

@NativePlugin(
        requestCodes = { FilePicker.REQUEST_CODE},
        name = "FilePicker",
        permissions={
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.CAMERA
        }
)
public class FilePicker extends Plugin {

    private static final String TAG = FilePicker.class.getSimpleName();
    static final int REQUEST_CODE = 3786;


    @PluginMethod()
    public void pick(PluginCall call) {

        if(this.hasRequiredPermissions()){
            startPickFileIntent(call);
        } else {
            this.pluginRequestPermissions(
                    new String[] {
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, REQUEST_CODE
            );
        }


    }

    private void startPickFileIntent(PluginCall call) {
        saveCall(call);

        Intent target = FileUtils.createGetContentIntent();
        // Create the chooser Intent
        Intent intent = Intent.createChooser(
                target, "Select a file.");
        try {
            startActivityForResult(call, intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            Log.e(TAG, "[FilePickerPlugin] error");
            call.error(("ActivityNotFoundException thrown."));
        }
    }

    @Override
    protected void handleRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.handleRequestPermissionsResult(requestCode, permissions, grantResults);

        PluginCall savedCall = getSavedCall();
        if (savedCall == null) {
            Log.i(TAG,"No stored plugin call for permissions request result");
            return;
        }

        for(int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                Log.i(TAG,"User denied permission");
                savedCall.error("User denied permission");
                return;
            }
        }

        if (requestCode == REQUEST_CODE) {
            startPickFileIntent(savedCall);
        }
    }

    @Override
    protected void handleOnActivityResult(int requestCode, int resultCode, Intent data) {
        super.handleOnActivityResult(requestCode, resultCode, data);

        PluginCall savedCall = getSavedCall();

        if (savedCall == null) {
            Log.i(TAG, "savedCall is null");
            return;
        }
        if (requestCode == REQUEST_CODE) {
           if(resultCode == RESULT_OK) {
               if(data != null) {
                   final Uri uri = data.getData();
                   Log.i(TAG, "Uri = " + uri.toString());
                   JSObject result = new JSObject();
                   result.put("uri", uri.toString());
                  savedCall.resolve(result);
               }

           }
        }
    }
}
