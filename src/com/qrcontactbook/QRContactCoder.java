package com.qrcontactbook;

import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class QRContactCoder extends AsyncTask<String, Integer, ImageView> {
	private Activity act;
	private ProgressDialog dialog;
	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;
	private static final int ID = 34646456;
	
	public QRContactCoder(Activity act) {
		this.act = act;
	}

	@Override
	protected void onPreExecute() {
		dialog = new ProgressDialog(act);
		dialog.setTitle("Encoding");
		dialog.setMessage("Please, wait...");
		dialog.setCancelable(false);
		dialog.show();
		super.onPreExecute();
	}
	
	@Override
	protected ImageView doInBackground(String... params) {
		DisplayMetrics dm = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;
		ImageView image = new ImageView(act);
		try {
			BitMatrix matrix = new QRCodeWriter().encode(params[0],
					com.google.zxing.BarcodeFormat.QR_CODE, width, width);
			image.setImageBitmap(matrixToBitmap(matrix));
		} catch (WriterException e) {
			e.printStackTrace();
		}
		image.setId(ID);
		return image;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onPostExecute(ImageView image) {
		try {
			dialog.dismiss();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ImageView old = (ImageView) act.findViewById(ID);
		if (old != null) {
			((FrameLayout) old.getParent()).removeViewInLayout(old);
		}
		act.addContentView(image, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		super.onPostExecute(image);
	}

	private Bitmap matrixToBitmap(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		Bitmap image = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setPixel(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}
}
