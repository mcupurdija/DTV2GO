package rs.multitelekom.dtv2go.util;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class AssetUtils {

	public static boolean exists(String fileName, String path, AssetManager assetManager) throws IOException {
		for (String currentFileName : assetManager.list(path)) {
			if (currentFileName.equals(fileName)) {
				return true;
			}
		}
		return false;
	}

	public static String[] list(String path, AssetManager assetManager) throws IOException {
		String[] files = assetManager.list(path);
		Arrays.sort(files);
		return files;
	}

	public static Bitmap getBitmapFromAsset(String strName, AssetManager assetManager)
	{
		try {
            return BitmapFactory.decodeStream(assetManager.open(strName));
		} catch (Exception e) {
			return null;
		}
	}

	public static String computeMD5Hash(String password) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(password.getBytes());
			byte messageDigest[] = digest.digest();

			StringBuffer MD5Hash = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++)
			{
				String h = Integer.toHexString(0xFF & messageDigest[i]);
				while (h.length() < 2)
					h = "0" + h;
				MD5Hash.append(h);
			}
			return MD5Hash.toString();
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}