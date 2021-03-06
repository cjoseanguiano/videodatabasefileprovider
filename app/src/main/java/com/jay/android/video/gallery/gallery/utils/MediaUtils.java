package com.jay.android.video.gallery.gallery.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.jay.android.video.gallery.gallery.activity.VideoGalleryActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.jay.android.video.gallery.gallery.GalleryApp.FILE_SAVE_FORMATE;
import static com.jay.android.video.gallery.gallery.GalleryApp.TAG;

public final class MediaUtils {

    private MediaUtils() {}

    public static Uri getOutputMediaFileUri(Context context) {
        return Uri.fromFile(getOutputMediaFile(context));
    }

    public static File getOutputMediaFile(Context context) {
//        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES), "camera");
        File mediaStorageDir = new File(context.getExternalCacheDir(),"DIRECTORY_MOVIES");

        createMediaStorageDir(mediaStorageDir);

        return createFile(mediaStorageDir);
    }

    public static String saveToInternalStorage(Context context, Uri sourceUri) {
        InputStream in = null;
        OutputStream out = null;

        File sourceExternalImageFile = new File(sourceUri.getPath());
        File destinationInternalImageFile = new File(getOutputInternalMediaFile(context).getPath());

        try {
            destinationInternalImageFile.createNewFile();

            in = new FileInputStream(sourceExternalImageFile);
            out = new FileOutputStream(destinationInternalImageFile);

            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } catch (IOException e) {
            Log.e(TAG, "Problem while copying to internal storage");
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    in.close();
                }
            } catch (IOException e) {
                Log.e(TAG, "Problem while copying to internal storage");
            }
        }
        sourceExternalImageFile.delete();
        return destinationInternalImageFile.getPath();
    }

    private static File getOutputInternalMediaFile(Context context) {
        File mediaStorageDir = new File(context.getExternalCacheDir(), "myGallery");

        createMediaStorageDir(mediaStorageDir);

        return createFile(mediaStorageDir);
    }

    private static void createMediaStorageDir(File mediaStorageDir) {
        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdirs(); // Used to be 'mediaStorage.mkdirs();'
        }
    }

    private static File createFile(File mediaStorageDir) {
        String timeStamp = new SimpleDateFormat(FILE_SAVE_FORMATE).format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "Video_" + timeStamp + ".mp4");
        return mediaFile;
    }
}
