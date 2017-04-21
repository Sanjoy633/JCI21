package com.surflex.jci;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

public class Purchase2Activity extends AppCompatActivity implements View.OnClickListener {

    //Declaring views
    private Button buttonChoose;
    private Button buttonUpload;
    private ImageView imageView;
    public TextView backData,imageSize;
    PurchaseDatabaseHandler purchaseDatabaseHandler;
    //Image request code
    private int PICK_IMAGE_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    //Bitmap to get image from gallery
    private Bitmap bitmap;
    String formNo="",basis="",binNo="",juteVariety="",grossQuantity="",deductionQuantity="",
            netQuantity="",avgRate="",estGradeComp="",allgrade="",grades="",entryDate="";


    //Uri to store the image uri
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase2);
        purchaseDatabaseHandler=new PurchaseDatabaseHandler(this);
        //Requesting storage permission
        requestStoragePermission();

        //Initializing views
        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageSize = (TextView) findViewById(R.id.imageSize);
        backData = (TextView)findViewById(R.id.backData);
        Calendar c = Calendar.getInstance();

        /*String sDate = c.get(Calendar.YEAR) + "-"
                + c.get(Calendar.MONTH)
                + "-" + c.get(Calendar.DAY_OF_MONTH)
                + " at " + c.get(Calendar.HOUR_OF_DAY)
                + ":" + c.get(Calendar.MINUTE);*/

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            try {
                entryDate = extras.getString("entryDate");
                formNo = extras.getString("formNo");
                basis = extras.getString("basis");
                binNo = extras.getString("binNo");
                juteVariety = extras.getString("juteVariety");
                grossQuantity = extras.getString("grossQuantity");
                deductionQuantity = extras.getString("deductionQuantity");
                netQuantity = extras.getString("netQuantity");
                avgRate = extras.getString("avgRate");
                estGradeComp = extras.getString("estGradeComp");
                allgrade = extras.getString("allGrade");
                String[] grade= allgrade.split(",");
                for(int i=0;i<grade.length;i++){
                    grades = grades+"Grade "+(i+1)+" : "+grade[i]+"\r\n";
                }


            }catch (Exception e){
                Toast.makeText(this, "Oops ! Something went wrong", Toast.LENGTH_SHORT).show();
            }
            //The key argument here must match that used in the other activity
        }

        backData.setText("entryDate: "+entryDate
                +"\r\n"+"Form no: "+formNo
                +"\r\n"+"Basis: "+basis
                +"\r\n"+"Bin No: "+binNo
                +"\r\n"+"Jute Variety: "+juteVariety
                +"\r\n"+"Gross Quantity: "+grossQuantity
                +"\r\n"+"Deduction Quantity: "+deductionQuantity
                +"\r\n"+"Net Quantity: "+netQuantity
                +"\r\n"+"Estimated Grade Component:"+estGradeComp
                +"\r\n"+grades);

        //Setting clicklistener
        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);


    }



    /*
    * This is the method responsible for image upload
    * We need the full image path and the name for the image in this method
    * */

public  void insertIntoLocal(String imgPath){
    try {
        PurchaseDatabaseHandler purchaseDatabaseHandler = new PurchaseDatabaseHandler(this);
        // Inserting Contacts
        Log.d("Insert: ", "Inserting ..");
        purchaseDatabaseHandler.addPurchase(new PurchaseModel(formNo,basis,binNo,juteVariety,grossQuantity,deductionQuantity,
                netQuantity,avgRate,estGradeComp,allgrade,imgPath,entryDate));

            Toast.makeText(this, "Inserting ...", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Data Inserted.", Toast.LENGTH_SHORT).show();
        Intent i1 = new Intent(getApplicationContext(), ViewPurchaseActivity.class);
        startActivity(i1);

    }catch (Exception e){
        Toast.makeText(this, "Oops ! Something went wrong", Toast.LENGTH_LONG).show();
    }

}
    public void uploadMultipart() {
        //getting name for the image

        //getting the actual path of the image
        String path = getPath(filePath);
        File file = new File(path);
        long length = file.length() / 1024;
        imageSize.setText(length+" kb");


        //Uploading code
        try {
            if (length<100){
                throw new lengthGreaterThan100KB();
            }
            String uploadId = UUID.randomUUID().toString();
            Toast.makeText(this, "length: " + length + " kb", Toast.LENGTH_SHORT).show();
            //Creating a multi part request
            if(isInternetOn()){
                insertIntoLocal(path);
                new MultipartUploadRequest(this, uploadId, Constants.UPLOAD_URL)
                        .addFileToUpload(path, "image") //Adding file
                        //.addParameter("name", name) //Adding text parameter to the request
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(2)
                        .startUpload(); //Starting the upload


            }
            else{

                insertIntoLocal(path);


            }


        } catch(lengthGreaterThan100KB ex)
        {
            Toast.makeText(this, "Picture size should be larger than 100KB", Toast.LENGTH_SHORT).show();
        } catch (Exception exc) {
            //Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("Image error:",exc.getMessage());
        }
    }


    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //method to get the file path from uri
    public String getPath(Uri uri) {
        String path="";
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }


    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onClick(View v) {
        if (v == buttonChoose) {
            showFileChooser();
        }
        if (v == buttonUpload) {
            try {
                uploadMultipart();

            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }


        }
    }

    public final boolean isInternetOn() {
    try{
        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getActiveNetworkInfo().getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getActiveNetworkInfo().getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getActiveNetworkInfo().getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getActiveNetworkInfo().getState() == android.net.NetworkInfo.State.CONNECTED) {

            // if connected with internet

            Toast.makeText(this, "You have "+connec.getActiveNetworkInfo().getTypeName()+" connection.", Toast.LENGTH_LONG).show();
            return true;

        } else if (
                connec.getActiveNetworkInfo().getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getActiveNetworkInfo().getState() == android.net.NetworkInfo.State.DISCONNECTED) {

            Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }

    }catch (Exception e){
        return false;
    }
        return false;

    }
}

class lengthGreaterThan100KB extends Exception
{
    //Parameterless Constructor
    public lengthGreaterThan100KB() {}

    //Constructor that accepts a message
    public lengthGreaterThan100KB(String message)
    {
        super(message);
    }
}

