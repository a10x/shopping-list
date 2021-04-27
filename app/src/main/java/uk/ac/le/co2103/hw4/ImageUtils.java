package uk.ac.le.co2103.hw4;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class ImageUtils {

    public static byte[] BitmapToByteArray(Bitmap image){
        if(image == null) return null;
        ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, byteOutStream);
        byte[] bytes = byteOutStream.toByteArray();
        image.recycle();
        return bytes;
    }

    public static Bitmap ByteArrayToBitmap(byte[] imageBytes){
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }

}
