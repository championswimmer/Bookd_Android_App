package in.tosc.bookd;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by prempal on 4/4/15.
 */
public class Utils {

    public static final boolean LOG_D = true;
    public static final boolean LOG_V = true;


    public static final String TAG = "Utils";

    public static final String PARENT_CACHE_DIRECTORY = "BookD/";
    protected static final String CACHE_DIRECTORY = "BookD/cover";

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

    public static void goToMainActivity(Activity activity){
        Intent i = new Intent(activity, MainActivity.class);
        activity.startActivity(i);
        activity.finish();
    }

    public static boolean addUriToCache(String Id,Uri uri) {
        File cacheDirectory;
        BufferedWriter bw = null;


        try {
            cacheDirectory = ensureCache();
        } catch (IOException e) {
            Log.e(TAG, "Could not create cache directory!");
            return false;
        }
    try {
        File coverFile = new File(cacheDirectory,Id);
        FileWriter fw = new FileWriter(coverFile.getAbsoluteFile());
        fw.write(uri.toString());
        fw.close();
        Log.d("lol",uri.toString());
    }catch (IOException e){

    }


        return true;
    }
    public static File ensureCache() throws IOException {
        File cacheDirectory = getCacheDirectory();
        if (!cacheDirectory.exists()) {
            if (!cacheDirectory.mkdirs()) {
                File parentCache = ensureParentCache();
                new File(parentCache, ".nomedia").createNewFile();
                cacheDirectory = getCacheDirectory();
                cacheDirectory.mkdirs();
            }
            new File(cacheDirectory, ".nomedia").createNewFile();
        }
        return cacheDirectory;
    }

    public static File getCacheDirectory() {
        return getExternalFile(CACHE_DIRECTORY);
    }

    public static File getExternalFile(String file) {
        return new File(Environment.getExternalStorageDirectory(), file);
    }
    public static File ensureParentCache() throws IOException {
        File cacheDirectory = new File(
                Environment.getExternalStorageDirectory(),
                PARENT_CACHE_DIRECTORY);
        if (!cacheDirectory.exists()) {
            cacheDirectory.mkdirs();
            new File(cacheDirectory, ".nomedia").createNewFile();
        }
        return cacheDirectory;
    }


    public static String readFileAsString(String id) {

        StringBuilder stringBuilder = new StringBuilder();
        String line;
        BufferedReader in = null;

        final File file = new File(getCacheDirectory(), id);

        try {
            in = new BufferedReader(new FileReader(file));
            while ((line = in.readLine()) != null) stringBuilder.append(line);
            Log.d("lol",stringBuilder.toString());

        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }

        return stringBuilder.toString();
    }



    }





