package com.example.android.phonebook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EditActivity extends Activity {
    static int REQUEST_IMAGE_CAPTURE;
    private Uri mImageCaptureUri;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICK_FROM_FILE = 3;
    private AlertDialog dialog;


    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        EditText tname = (EditText)findViewById(R.id.name_edit_view);
        EditText tnumber = (EditText)findViewById(R.id.number_edit_view);
        tname.setText(intent.getExtras().getString("name"));
        tnumber.setText(intent.getExtras().getString("number"));
        int position = intent.getExtras().getInt("posisi");
        Bitmap x = MainActivity.contactsList.get(position).getFoto();


        if(x!=null){


            ImageView photoImageView = (ImageView)findViewById(R.id.edit_image_view);
            photoImageView.setImageBitmap(x);

        }

    }

    public void cancelButton(View view){
//        int position = intent.getExtras().getInt("posisi");
//        TextView posisisi = (TextView)findViewById(R.id.posisi);
//        posisisi.setText(position + "");
        finish();
    }

    public void submitEdit(View view){
        int position = intent.getExtras().getInt("posisi");
        REQUEST_IMAGE_CAPTURE = position;
        EditText name = (EditText)findViewById(R.id.name_edit_view);
        EditText number = (EditText)findViewById(R.id.number_edit_view);
        ImageView imageView = (ImageView) findViewById(R.id.edit_image_view);
        Bitmap bm=((BitmapDrawable)imageView.getDrawable()).getBitmap();

        MainActivity.editContact(view, position, name.getText().toString(), number.getText().toString(),bm);
        Intent ix = new Intent(this,MainActivity.class);
        startActivity(ix);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView iv = (ImageView)findViewById(R.id.edit_image_view);
        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case PICK_FROM_CAMERA:
                /**
                 * After taking a picture, do the crop
                 */
                doCrop();

                break;

            case PICK_FROM_FILE:
                /**
                 * After selecting image from files, save the selected path
                 */
                mImageCaptureUri = data.getData();

                doCrop();

                break;

            case CROP_FROM_CAMERA:
                Bundle extras = data.getExtras();
                /**
                 * After cropping the image, get the bitmap of the cropped image and
                 * display it on imageview.
                 */
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");

                    iv.setImageBitmap(photo);
                }

                File f = new File(mImageCaptureUri.getPath());
                /**
                 * Delete the temporary image
                 */
                if (f.exists())
                    f.delete();

                break;

        }
    }

    private void doCrop() {
        final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();
        /**
         * Open image crop app by starting an intent
         * ‘com.android.camera.action.CROP‘.
         */
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");

        /**
         * Check if there is image cropper app installed.
         */
        List<ResolveInfo> list = getPackageManager().queryIntentActivities(
                intent, 0);

        int size = list.size();

        /**
         * If there is no image cropper app, display warning message
         */
        if (size == 0) {

            Toast.makeText(this, "Can not find image crop app",
                    Toast.LENGTH_SHORT).show();

            return;
        } else {
            /**
             * Specify the image path, crop dimension and scale
             */
            intent.setData(mImageCaptureUri);

            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 200);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);
            /**
             * There is posibility when more than one image cropper app exist,
             * so we have to check for it first. If there is only one app, open
             * then app.
             */

            if (size == 1) {
                Intent i = new Intent(intent);
                ResolveInfo res = list.get(0);

                i.setComponent(new ComponentName(res.activityInfo.packageName,
                        res.activityInfo.name));

                startActivityForResult(i, CROP_FROM_CAMERA);
            } else {
                /**
                 * If there are several app exist, create a custom chooser to
                 * let user selects the app.
                 */
                for (ResolveInfo res : list) {
                    final CropOption co = new CropOption();

                    co.title = getPackageManager().getApplicationLabel(
                            res.activityInfo.applicationInfo);
                    co.icon = getPackageManager().getApplicationIcon(
                            res.activityInfo.applicationInfo);
                    co.appIntent = new Intent(intent);

                    co.appIntent
                            .setComponent(new ComponentName(
                                    res.activityInfo.packageName,
                                    res.activityInfo.name));

                    cropOptions.add(co);
                }

                CropOptionAdapter adapter = new CropOptionAdapter(
                        getApplicationContext(), cropOptions);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Choose Crop App");
                builder.setAdapter(adapter,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                startActivityForResult(
                                        cropOptions.get(item).appIntent,
                                        CROP_FROM_CAMERA);
                            }
                        });

                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {

                        if (mImageCaptureUri != null) {
                            getContentResolver().delete(mImageCaptureUri, null,
                                    null);
                            mImageCaptureUri = null;
                        }
                    }
                });

                AlertDialog alert = builder.create();

                alert.show();
            }
        }
    }

    public void captureImageInitialization(View view){
        final String[] items = new String[]{"Take from camera","Select from gallery"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,items);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Select Image");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    mImageCaptureUri = Uri.fromFile(new File(Environment
                            .getExternalStorageDirectory(), "tmp_avatar_"
                            + String.valueOf(System.currentTimeMillis())
                            + ".jpg"));

                    intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
                            mImageCaptureUri);

                    try {
                        intent.putExtra("return-data", true);
                        startActivityForResult(intent, PICK_FROM_CAMERA);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }

                } else {
                    Intent intent = new Intent();

                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);

                    startActivityForResult(Intent.createChooser(intent,
                            "Complete action using"), PICK_FROM_FILE);
                }
            }
        });

        dialog = builder.create();
        dialog.show();
    }


//    public void editImage(View view){
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
//
//    }

    Bitmap photo;
    File photofile;

//    protected void onActivityResult(int requestCode,int resultCode, Intent data){
//        super.onActivityResult(requestCode,resultCode,data);
//
//        if(requestCode==REQUEST_IMAGE_CAPTURE){
//            try{
//                photo = (Bitmap) data.getExtras().get("data");
//
//            }catch(NullPointerException ex){
//                photo = BitmapFactory.decodeFile(photofile.getAbsolutePath());
//            }
//            Save s = new Save();
//            if(photo!=null){
//
//                ImageView iv = (ImageView)findViewById(R.id.edit_image_view);
//
//                iv.setImageBitmap(photo);
//                s.SaveImage(this, photo);
//            }else{
//
//                Toast.makeText(this, "oops cant save photo", Toast.LENGTH_LONG).show();
//
//            }
//        }
//
//
//    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
