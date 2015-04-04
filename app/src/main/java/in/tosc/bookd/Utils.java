package in.tosc.bookd;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by prempal on 4/4/15.
 */
public class Utils {

    public static final boolean LOG_D = true;
    public static final boolean LOG_V = true;


    public static final String TAG = "Utils";

    public static String getUserEmail(Context context) {
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        Account[] accounts = AccountManager.get(context).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                String possibleEmail = account.name;
                return possibleEmail;
            }
        }
        return "";
    }

    public static boolean isEditTextEmpty(EditText editText) {
        String text = editText.getText().toString();
        if (text.matches("")) {
            return true;
        }
        return false;
    }


    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,10}$";

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static String ISBN10toISBN13( String ISBN10 ) {
        String ISBN13  = ISBN10;
        ISBN13 = "978" + ISBN13.substring(0,9);
        if (LOG_D) Log.d(TAG, "ISBN13 without sum" + ISBN13);
        int d;

        int sum = 0;
        for (int i = 0; i < ISBN13.length(); i++) {
            d = ((i % 2 == 0) ? 1 : 3);
            sum += ((((int) ISBN13.charAt(i)) - 48) * d);
            if (LOG_D) Log.d(TAG, "adding " + ISBN13.charAt(i) + "x" + d + "=" + ((((int) ISBN13.charAt(i)) - 48) * d));
        }
        sum = 10 - (sum % 10);
        ISBN13 += sum;

        return ISBN13;
    }
}
