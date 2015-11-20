package com.example.eandreje.androidapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GoogleApiAvailability;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
//import com.google.android.gms.common.api.PendingResult;
//import com.google.android.gms.common.api.ResultCallback;
//import com.google.android.gms.drive.Drive;
//import com.google.android.gms.drive.DriveApi;
//import com.google.android.gms.drive.DriveContents;
//import com.google.android.gms.drive.DriveFile;
//import com.google.android.gms.drive.DriveFolder;
//import com.google.android.gms.drive.DriveId;
//import com.google.android.gms.drive.DriveResource;
//import com.google.android.gms.drive.MetadataChangeSet;
//import com.google.android.gms.drive.OpenFileActivityBuilder;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements CreateActivityFragment.CreateActivityFragmentListener,
        CreateDocumentFragment.CreateDocumentFragmentListener
{
    private CreateDocumentFragment documentFragment;
   // private GoogleApiClient googleApiClient;
    private static final int REQUEST_CODE_RESOLUTION = 1;
    private static final int REQUEST_CODE_FILE = 2;
    private String EXISTING_FILE_ID = "CAESHDBCMFc3TzZQRUxtcTRUakl3VnpaWFduTmtUSGMY3A0g_OXH6Z5SKAA";
  //  DriveId driveId;


    private static final String TAG = "MainActivity";

    private DocItem activeObject;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("ParentAct", activeObject);
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        googleApiClient = new GoogleApiClient.Builder(this)
//                .addApi(Drive.API)
//                .addScope(Drive.SCOPE_FILE)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .build();

        setContentView(R.layout.activity_main);
        if(savedInstanceState == null) {
            CreateActivityFragment activityFragment = new CreateActivityFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_activity_layout, activityFragment)
                    .commit();
            this.setTitle("Aktiviteter");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (googleApiClient == null)
//        {
//            googleApiClient = new GoogleApiClient.Builder(this)
//                    .addApi(Drive.API)
//                    .addScope(Drive.SCOPE_FILE)
//                    .addConnectionCallbacks(this)
//                    .addOnConnectionFailedListener(this)
//                    .build();
//        }
//        googleApiClient.connect();

    }


    @Override
    protected void onPause() {
//        if(googleApiClient != null)
//        {
//            googleApiClient.disconnect();
//        }
        super.onPause();
    }



    //activeObject recieves the clicked listobject(activities) which is forwarded
    //to the static newInstance method.
    @Override
    public void activeObject(ListItem listItem) {
        documentFragment = CreateDocumentFragment.newInstance(listItem);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_activity_layout, documentFragment)
                .addToBackStack(null).commit();
    }

    //docObject recieves the clicked listobject(document)
    @Override
    public void docObjectClicked(DocItem doc) {
        activeObject = doc;
        LeftsideDocumentFragment leftsideDocumentFragment = LeftsideDocumentFragment.newInstance(doc);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_activity_layout, leftsideDocumentFragment)
                .addToBackStack(null).commit();

    }

//    @Override
//    public void launchGoogleDrive() {
//
//        IntentSender i = Drive.DriveApi.newOpenFileActivityBuilder().setMimeType(new String[]{ "text/csv"}).build(googleApiClient);
//        try {
//            startIntentSenderForResult(i, REQUEST_CODE_FILE, null, 0, 0, 0);
//        } catch (IntentSender.SendIntentException e) {
//            e.printStackTrace();
//        }
//    }



//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//    switch(requestCode)
//    {
//        case REQUEST_CODE_FILE:
//
//            if (resultCode == RESULT_OK && googleApiClient.isConnected()) {
//
//                driveId = data.getParcelableExtra(OpenFileActivityBuilder.EXTRA_RESPONSE_DRIVE_ID);
//                DriveFile file = driveId.asDriveFile();
//                getFile(driveId,file);
//
//
//
//
//
//
//                //   final PendingResult<DriveResource.MetadataResult> metaDataResult = file.getMetadata(googleApiClient);
//              //  final PendingResult<DriveApi.DriveContentsResult> contentResult = file.open(googleApiClient, DriveFile.MODE_READ_ONLY, null);
//                //DriveFile file = Drive.DriveApi.getFile(getGooleApiClient(), driveId);
////                Uri uri = data.getData();
//
//                //DriveApi.DriveContentsResult contentresult = file.open(getGooleApiClient(), DriveFile.MODE_READ_ONLY, null).await();
//
//                //file.open(googleApiClient, DriveFile.MODE_READ_ONLY, null).setResultCallback(contentOpenedCallback);
//                //BufferedInputStream reader = new BufferedInputStream(contentresult.getDriveContents().getInputStream());
//
//            }
//            break;
//        case REQUEST_CODE_RESOLUTION:
//            if (resultCode == RESULT_OK) {
//                googleApiClient.connect();
//            }
//            break;
//        default:
//            super.onActivityResult(requestCode, resultCode, data);
//            break;
//    }}
//    public void getFile(DriveId id, DriveFile file)
//    {
//        file.open(googleApiClient, DriveFile.MODE_READ_ONLY, null)
//                .setResultCallback(contentsOpenedCallback);
//    }


//    @Override
//    public void onConnected(Bundle bundle) {
//        Log.i(TAG, "GoogleApiClient connected");
//        if(driveId == null)
//            launchGoogleDrive();
//        else {
//            DriveFile file = driveId.asDriveFile();
//            file.open(googleApiClient, DriveFile.MODE_READ_ONLY, null).setResultCallback(contentsOpenedCallback);
//        }
//
//    }

//    @Override
//    public void onConnectionSuspended(int i) {
//        Log.i(TAG, "GoogleApiClient connection suspended");
//    }

//    @Override
//    public void onConnectionFailed(ConnectionResult connectionResult) {
//        Log.i(TAG, "GoogleApiClient connection failed: " + connectionResult.toString());
//        if(!connectionResult.hasResolution())
//        {
//            GoogleApiAvailability.getInstance().getErrorDialog(this, connectionResult.getErrorCode(), 0).show();
//            return;
//        }
//        try {
//            connectionResult.startResolutionForResult(this, REQUEST_CODE_RESOLUTION);
//        }
//        catch (IntentSender.SendIntentException e) {
//            Log.e(TAG, "Exception while starting resolution activity", e);
//        }
//    }
//    public GoogleApiClient getGoogleApiClient()
//    {
//        return googleApiClient;
//    }
//
//    final ResultCallback<DriveApi.DriveContentsResult> contentsOpenedCallback =
//            new ResultCallback<DriveApi.DriveContentsResult>() {
//                @Override
//                public void onResult(DriveApi.DriveContentsResult driveContentsResult) {
//                    if (!driveContentsResult.getStatus().isSuccess()) {
//                        Toast.makeText(MainActivity.this, "FIEL CANT BE OPENED", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    DriveContents contents = driveContentsResult.getDriveContents();
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(contents.getInputStream()));
//                    StringBuilder builder = new StringBuilder();
//                    String line;
//                    try {
//                        while ((line = reader.readLine()) != null) {
//                            builder.append(line);
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    String contentsAsString = builder.toString();
//                }
//
//
//            };

}
