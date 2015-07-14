package rs.multitelekom.dtv2go.util;

import android.content.Context;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;

public class DialogUtils {

    private static final String TAG = "DialogUtils";

    public static void showBasicInfoDialog(Context context, int title, int message) {
        try {
            new MaterialDialog.Builder(context)
                    .title(title)
                    .content(message)
                    .positiveText("OK")
                    .show();
        } catch (Exception e) {
            String err = e.getMessage();
            if (err != null) Log.e(TAG, err);
        }
    }

    public static void showBasicInfoDialog(Context context, int title, String message) {
        try {
            new MaterialDialog.Builder(context)
                    .title(title)
                    .content(message)
                    .positiveText("OK")
                    .show();
        } catch (Exception e) {
            String err = e.getMessage();
            if (err != null) Log.e(TAG, err);
        }
    }

    public static void showBasicInfoDialog(Context context, String title, String message) {
        try {
            new MaterialDialog.Builder(context)
                    .title(title)
                    .content(message)
                    .positiveText("OK")
                    .show();
        } catch (Exception e) {
            String err = e.getMessage();
            if (err != null) Log.e(TAG, err);
        }
    }

}