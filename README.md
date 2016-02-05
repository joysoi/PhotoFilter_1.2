# PhotoFilter_1.2


Hello World!

Today I am going to show you how to select a picture from your gallery and how to invert the pixels on that picture.



First:

In the Main Activity we declare the following elements:    
    
In order to receive a result from the local gallery we have to declare the following variable	
    private static final int RESULT_LOAD_IMAGE = 1; 
Declaration of the Image Elements from the XML 
    ImageView image_Upload; 
    ImageView image_Result;
Declaration of the Button Elements from the XML 
    Button filter_Button;
    Button upload_Button;
Declaration of Bitmap
In order to manipulate with the pixels of a picture in Android we have to convert it into a Bitmap
    Bitmap bitmap_Image;

Second:

In the onCreate method we are linking the elements that we have declared earlier.
We give them a unique name in order the call them later on:
        image_Upload = (ImageView) findViewById(R.id.ImageView_U);
        image_Result = (ImageView) findViewById(R.id.ImageView_R);
        upload_Button = (Button)findViewById(R.id.Button_U);
        filter_Button = (Button)findViewById(R.id.Button_F);

Third:

Once we declared, linked and named all the required elements the next phase is putting them in action.
To put them in action we have to call the onClickListener method which is simply an order that has to be executed by a certain element:
There is two ways of doing that and I will give you an example of both:

1. Creating the onClick method throughout the onClickListener INSIDE of the onCreate method:

        filter_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap_Image = ((BitmapDrawable) image_Upload.getDrawable()).getBitmap(); 
                Bitmap filter = invertImage(bitmap_Image);
                image_Result.setImageBitmap(filter);
            }
        }); 

2. Creating the onClick method throughout the onClickListener OUTSIDE of the onCreate method:


	public void uploadImage(View view){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
    }

* In order to do this we have to add one extra line of code (attribute) in the XML part of the app, in the body of the element that we have chosen to do this upon 
	android:onClick="uploadImage"

Let's explain the previous code:
In the first example we are setting the onClickListner which is a minion used for good or evil by the onClick method that is called upon the filter_Button element
and it gives great power to it to take any JPEG or PNG image and transform it to Bitmap image and call the invertImage method on it and show the result image on the
image_Result element when it is all done. Really powerful!

The second example we have called the uploadImage method that is created in the body of the Button element with the name Button_U in the XML: android:onClick="uploadImage".
The purpose of the uploadImage method is getting a picture from the computer memory.
We do that we the help of an Intent object.
Intent is the remote control that helps you change the channels from a distance than you getting up from the comfort of your couch and doing it yourself.
Intent makes life way easier.
Unchangeable constant syntax that Google created:
//Choose the picture
Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
// Pass the picture
startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);


Forth:
In order to return the picture that we have chosen from the gallery we have to use the onActivityResult method.
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            image_Upload.setImageURI(selectedImage);
        }
       }
The purpose of the method is to check if we indeed have:
	- taken a picture (requestCode == RESULT_LOAD_IMAGE) 
	- we do not have any errors (resultCode == RESULT_OK)
	- made sure that an actual image is selected (data != null)
which are also the 3 parameters of the onActivityResult method
Now, if all is ok we are proceeding into displaying the image through calling the URI (Uniform Resource Identifier) and setting it in the image_Upload element frame.


Fifth:
Creating the invertImage method on any Bitmap image that we are going to use in the applications itself that goes under the name "original"  
  
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

But because we do not know the size of the particular Bitmap image (original) that we are going to call the invertImage method on, we create an variable called
"finalImage" and we store the "original" Bitmap in that variable. You can imagine the "finalImage" variable like a PLAIN CANVAS.
The "createBitmap" object takes 3 parameters, the "Width", "Height" and "Config"- (background information)

Every image is made from pixels and those pixels have RGB values and Alpha Value A (transparency).

The invertImage method works like this:
We take the "original" image and we loop thorough the columns and the rows (all the pixel, width and height) and inverting the RGB values of the pixels in two FOR loops 
(one for the column pixels and one for the row pixels).

Declarations:
We declare the Alpha, Red, Green, Blue values.
We declare "pixelColor" in order to store the new value of the pixels.
In order the FOR loop to know how much he needs to loop we have to declare/get the height and width of the "original" image.
int height = original.getHeight();
int width = original.getWidth();

Creating the loop:

A Loop within a Loop for every single pixel in the "original" picture:
for (int y = 0; y < height; y++){
            for (int x = 0; x < wight; x++)

After, we need to grab the first pixel so the loop can start and we store the new value to the "pixelColor" variable:
pixelColor = original.getPixel(x,y);

We need to brake the pixel in his values (ARGB):
 A = Color.alpha(pixelColor);
 R = Color.red(pixelColor);
 G = Color.green(pixelColor);
 B = Color.blue(pixelColor);

BUT, in order to get the inverse form of them we have subtracted 255 from all of them

 A = Color.alpha(pixelColor);
 R = 255 - Color.red(pixelColor);
 G = 255 - Color.green(pixelColor);
 B = 255 - Color.blue(pixelColor);

The ALPHA is the same because it is always TRANSPARRENT


Finally, we have the result - the "finalImage" that is created like this:
finalImage.setPixel(x, y, Color.argb(A, R, G, B));
This line of code in words would be:
The "finalImage" is created by pixels with new coordinates (x,y) and new values (A, R, G, B). 

At the end we have to return the return/display the final image.

And there is also one little thing we should add to the Manifest before we run the app. In the body of the Manifest we should add this attribute/permission:
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/> so that the app can freely take any data from our storage.




The XML CODE:

In ScrollView we have a RelativeLayout where we created two Image and two Button elements.


<ScrollView
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:android="http://schemas.android.com/apk/res/android">

    <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    app:layout_behavior="@string/appbar_scrolling_view_behavior"
    tools:context="com.example.nikola.photofilter11.MainActivity"
    tools:showIn="@layout/activity_main">

    <ImageView
    android:layout_width="300dp"
    android:layout_height="300dp"
    android:id="@+id/ImageView_U"
    android:layout_centerHorizontal="true"
    android:layout_marginTop="200dp"
    style="@style/border"    />

    <Button
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Upload"
    android:id="@+id/Button_U"
    android:layout_below="@+id/ImageView_U"
    android:layout_centerHorizontal="true"
    android:textSize="20dp"
    android:onClick="uploadImage"/> - THE onCLICK ATTRIBUTE

    <Button
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Filter"
    android:id="@+id/Button_F"
    android:layout_below="@+id/Button_U"
    android:layout_centerHorizontal="true"
    android:textSize="20dp"/>

    <ImageView
    android:layout_width="300dp"
    android:layout_height="300dp"
    android:id="@+id/ImageView_R"
    android:layout_alignParentTop="true"
    android:layout_alignStart="@+id/ImageView_U"
    android:layout_marginTop="200dp"/>

</RelativeLayout>
</ScrollView>
























