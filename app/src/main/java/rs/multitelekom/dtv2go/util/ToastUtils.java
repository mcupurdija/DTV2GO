package rs.multitelekom.dtv2go.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

    public static void displayToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void displayToast(Context context, int res) {
        Toast.makeText(context, res, Toast.LENGTH_SHORT).show();
    }

    public static void displayPositionedToast(Context context, String message, int gravity) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(gravity, 0, 0);
        toast.show();
    }

    public static void displayPositionedToast(Context context, int res, int gravity) {
        Toast toast = Toast.makeText(context, res, Toast.LENGTH_SHORT);
        toast.setGravity(gravity, 0, 0);
        toast.show();
    }

}
