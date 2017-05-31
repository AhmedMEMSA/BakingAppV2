package ahmedmemsa.nanodegree.baking.Utils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

/**
 * Created by Ahmed Magdy on 4/12/2017.
 */

public class Handler {

    public static String volleyErrorHandler(VolleyError error) {
        if (error instanceof TimeoutError) {
            return ("Connection Timeout Error.");
        } else if (error instanceof NoConnectionError) {
            return ("Internet Connection Error");
        } else if (error instanceof AuthFailureError) {
            return ("Connection AuthFailure Error");
        } else if (error instanceof ServerError) {
            return ("Internal Server Error");
        } else if (error instanceof NetworkError) {
            return ("Network Error");
        } else if (error instanceof ParseError) {
            return ("Parse Error");
        } else {
            return ("Unknown Error");
        }
    }
}
