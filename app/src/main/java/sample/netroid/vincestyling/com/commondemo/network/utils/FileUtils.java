package sample.netroid.vincestyling.com.commondemo.network.utils;

import android.content.Context;
import android.os.Parcelable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by haipingguo on 16-12-12.
 */
public class FileUtils {
    public static String getSaveFilePath(Context context,String sourceUrl) {
        //返回String 对象内第一次出现子字符串的字符位置
        int start = sourceUrl.lastIndexOf("/");
        //返回String 对象中子字符串最后出现的位置
        int end = sourceUrl.indexOf(".zip");
        return context.getFilesDir().getAbsoluteFile()+"/"+sourceUrl.substring(start+1,end);
    }

    public static boolean saveObject(Context context, Parcelable parcelable, String mSoltId, int index) {
        String fileName = mSoltId + "_" + index;
        String path = context.getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(path, fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(parcelable);
            objectOutputStream.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                objectOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
