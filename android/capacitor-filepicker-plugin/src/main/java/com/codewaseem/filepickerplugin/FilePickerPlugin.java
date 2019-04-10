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
        requestCodes = { FilePickerPlugin.REQUEST_CODE},
        name = "FilePicker",
        permissions={
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.CAMERA
        }
)
public class FilePickerPlugin extends Plugin {

    private static final String TAG = FilePickerPlugin.class.getSimpleName();
    static final int REQUEST_CODE = 3786;


    @PluginMethod()
    public void pick(PluginCall call) {

        if(this.hasRequiredPermissions()){
            Log.i(TAG, "Has required permissions. Good to go!");
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
        Log.i(TAG, "Starting pick intent");
        saveCall(call);
        Log.i(TAG, "saveCall called");

        Intent target = FileUtils.createGetContentIntent();
        // Create the chooser Intent
        Intent intent = Intent.createChooser(
                target, "Select a file.");
        try {
            Log.i(TAG, "startActivityForResult calling");
            startActivityForResult(call, intent, REQUEST_CODE);
            Log.i(TAG, "startActivityForResult called");

        } catch (ActivityNotFoundException e) {
            Log.e(TAG, "[FilePickerPlugin] error");
            call.error(("ActivityNotFoundException thrown."));
        }
    }

    @Override
    protected void handleRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.handleRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.i(TAG,"handling request perms result");
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

        Log.i(TAG, "received handleOnActivityREsult");
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

                   try {
                       Context context = getActivity().getApplicationContext();
                       File file = FileUtils.getFile(context, uri);

                       JSObject result = new JSObject();

                       result.put("uri", uri.toString());
                       result.put("size", file.length());
                       result.put("type",FileUtils.getMimeType(file));
                       result.put("name",file.getName());
                       result.put("lastModified", file.lastModified());

                       savedCall.resolve(result);


                   } catch (Exception e) {
                       Log.i(TAG, e.getMessage());
                        savedCall.reject("Something went wrong while processing file");
                   }
               }

           }
        }
    }
}
