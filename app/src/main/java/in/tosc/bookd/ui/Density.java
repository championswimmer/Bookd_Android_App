package in.tosc.bookd.ui;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by yogesh on 04/04/15.
 */
class Density {
    public static int dp2px(Context context, float dp) {
        Resources r = context.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return (int) px;
    }
}
