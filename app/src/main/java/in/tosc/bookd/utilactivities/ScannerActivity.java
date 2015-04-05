package in.tosc.bookd.utilactivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import in.tosc.bookd.R;
import in.tosc.bookd.Utils;
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

        setContentView(R.layout.scannerview);
        mScannerView = (ZBarScannerView) findViewById(R.id.scannerView);
        mScannerView.setAutoFocus(true);
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
        if (Utils.LOG_V) Log.v(TAG, "Result : " + rawResult.getContents()); // Prints scan results
        if (Utils.LOG_V) Log.v(TAG, "Result format : " + rawResult.getBarcodeFormat().getName()); // Prints scan results
        if (rawResult.getBarcodeFormat() == BarcodeFormat.ISBN10) {
            Intent result = new Intent();
            if (Utils.LOG_V) Log.v(TAG, "Converted Result : " + Utils.ISBN10toISBN13(rawResult.getContents()));
            result.putExtra("ISBN", Utils.ISBN10toISBN13(rawResult.getContents()));
            setResult(AddBookLibraryActivity.ADD_BOOK_LIBRARY_RESULT, result);
            finish();

        } else if (rawResult.getBarcodeFormat() == BarcodeFormat.ISBN13) {
            Intent result = new Intent();
            result.putExtra("ISBN", rawResult.getContents());
            setResult(AddBookLibraryActivity.ADD_BOOK_LIBRARY_RESULT, result);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "This is not a valid ISBN of a book", Toast.LENGTH_LONG).show();
        }
    }
}