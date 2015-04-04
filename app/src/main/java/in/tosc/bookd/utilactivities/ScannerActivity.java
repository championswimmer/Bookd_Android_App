package in.tosc.bookd.utilactivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import me.dm7.barcodescanner.zbar.BarcodeFormat;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;


/**
 * Created by prempal on 4/4/15.
 */
public class ScannerActivity extends Activity implements ZBarScannerView.ResultHandler {

    public static final String TAG = "ScannerActivity";

    private ZBarScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZBarScannerView(this);    // Programmatically initialize the scanner view
        mScannerView.setAutoFocus(true);
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
        mScannerView.setupScanner();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Log.v(TAG, "Result : " + rawResult.getContents()); // Prints scan results
        Log.v(TAG, "Result format : " + rawResult.getBarcodeFormat().getName()); // Prints scan results
        if ((rawResult.getBarcodeFormat() == BarcodeFormat.ISBN10)
                || (rawResult.getBarcodeFormat() == BarcodeFormat.ISBN13)) {
            Intent result = new Intent();
            result.putExtra("ISBN", rawResult.getContents());
            setResult(AddBookLibraryActivity.ADD_BOOK_LIBRARY_RESULT, result);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "This is not a valid ISBN of a book", Toast.LENGTH_LONG).show();
        }
    }
}