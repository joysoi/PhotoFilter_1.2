package com.example.nikola.photofilter11;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;
    ImageView image_Upload;
    ImageView image_Result;
    Button filter_Button;
    Button upload_Button;
    Bitmap bitmap_Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image_Upload = (ImageView) findViewById(R.id.IVUpload);
        image_Result = (ImageView) findViewById(R.id.resultImage);
        upload_Button = (Button)findViewById(R.id.uploadButton);
        filter_Button = (Button)findViewById(R.id.BFilter);

        filter_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap_Image = ((BitmapDrawable) image_Upload.getDrawable()).getBitmap();
                Bitmap filter = invertImage(bitmap_Image);
                image_Result.setImageBitmap(filter);
            }
        });


    }

    public void uploadImage(View view){
//        How to get resources from local storage:
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            image_Upload.setImageURI(selectedImage);
        }
       }

    public static Bitmap invertImage(Bitmap original){

        Bitmap finalImage = Bitmap.createBitmap(original.getWidth(), original.getHeight(), original.getConfig());
        int A, R, G, B;
        int pixelColor;
        int height = original.getHeight();
        int wight = original.getWidth();


        for (int y = 0; y < height; y++){
            for (int x = 0; x < wight; x++){
                pixelColor = original.getPixel(x,y);
                A = Color.alpha(pixelColor);
                R = 255 - Color.red(pixelColor);
                G = 255 - Color.green(pixelColor);
                B = 255 - Color.blue(pixelColor);
                finalImage.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }
        return finalImage;
    }

}
