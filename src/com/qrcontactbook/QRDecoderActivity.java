package com.qrcontactbook;

import com.qrcontactbook.db.Contact;
import com.qrcontactbook.db.ContactData;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class QRDecoderActivity extends Activity {
    private Camera mCamera;
    private CameraPreview mPreview;
    private Handler autoFocusHandler;

    TextView scanText;
    Button scanButton;
    Button saveButton;
    
    private String saveResult = null;

    ImageScanner scanner;

    private boolean barcodeScanned = false;
    private boolean previewing = true;

    static {
        System.loadLibrary("iconv");
    } 

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.qr_decoder_activity);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        autoFocusHandler = new Handler();
        mCamera = getCameraInstance();

        /* Instance barcode scanner */
        scanner = new ImageScanner();
        scanner.setConfig(0, Config.X_DENSITY, 3);
        scanner.setConfig(0, Config.Y_DENSITY, 3);

        mPreview = new CameraPreview(this, mCamera, previewCb, autoFocusCB);
        FrameLayout preview = (FrameLayout)findViewById(R.id.cameraPreview);
        preview.addView(mPreview);

        scanText = (TextView)findViewById(R.id.scanText);

        scanButton = (Button)findViewById(R.id.ScanButton);
        scanButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (barcodeScanned) {
                        barcodeScanned = false;
                        scanText.setText("Scanning...");
                        mCamera.setPreviewCallback(previewCb);
                        mCamera.startPreview();
                        previewing = true;
                        mCamera.autoFocus(autoFocusCB);
                    }
                }
            });
        
        saveButton = (Button)findViewById(R.id.SaveButton);
        saveButton.setEnabled(false);
        saveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				saveResult();
			}
        });
    }

    public void onPause() {
        super.onPause();
        releaseCamera();
    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e){
        	Log.e(QRDecoderActivity.class.getName(), e.getMessage(), e);
        }
        return c;
    }

    private void releaseCamera() {
        if (mCamera != null) {
            previewing = false;
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }
    
    private String formatResult(String res) {
    	String result = res;
    	
    	if(res.contains("QRCodeContact.type")) {
    		String name = res.substring(res.indexOf("name=") + 5);
    		name = name.substring(0, name.indexOf(";"));
    		
    		result = "Found contact: " + name;
    		saveResult = res;
    		saveButton.setEnabled(true);
    	}
    	
    	return result;
    }
    
    private void saveResult() {
    	
		saveButton.setEnabled(false);
    	if(saveResult == null) {
    		return;
    	}
    	
    	String[] res =saveResult.split(";");
    	long contact_id = 0;
    	for(String s : res) {
			try {
				if(s.contains("name=")) {
    				((ContactApp) this.getApplication()).getContactManager()
    				.create(new Contact(s.substring(s.indexOf("=")+1)));
    				contact_id = ((ContactApp) this.getApplication())
    						.getContactManager().getLast().getId();
    				continue;
				}
				if(s.contains("=")) {
					((ContactApp) this.getApplication()).getContactDataManager()
					.create(new ContactData(contact_id,
							s.substring(0, s.indexOf("=")),
							s.substring(s.indexOf("=")+1)));
				}
			} catch(java.sql.SQLException ex) {
				Log.e(QRDecoderActivity.class.getName(), 
						ex.getMessage(), ex);
			}
    	}
    	Toast.makeText(this, "Contact Saved", Toast.LENGTH_LONG).show();
    }

    private Runnable doAutoFocus = new Runnable() {
            public void run() {
                if (previewing)
                    mCamera.autoFocus(autoFocusCB);
            }
        };

    PreviewCallback previewCb = new PreviewCallback() {
            public void onPreviewFrame(byte[] data, Camera camera) {
                Camera.Parameters parameters = camera.getParameters();
                Size size = parameters.getPreviewSize();

                Image barcode = new Image(size.width, size.height, "Y800");
                barcode.setData(data);

                int result = scanner.scanImage(barcode);
                
                if (result != 0) {
                    previewing = false;
                    mCamera.setPreviewCallback(null);
                    mCamera.stopPreview();
                    
                    SymbolSet syms = scanner.getResults();
                    for (Symbol sym : syms) {
                        scanText.setText(formatResult(sym.getData()));
                        barcodeScanned = true;
                    }
                }
            }
        };

    // Mimic continuous auto-focusing
    AutoFocusCallback autoFocusCB = new AutoFocusCallback() {
            public void onAutoFocus(boolean success, Camera camera) {
                autoFocusHandler.postDelayed(doAutoFocus, 1000);
            }
        };

}
