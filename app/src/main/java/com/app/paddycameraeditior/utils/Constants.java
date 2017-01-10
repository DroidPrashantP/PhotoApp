package com.app.paddycameraeditior.utils;

public class Constants {

	public static final String EXTRA_KEY_IMAGE_SOURCE = "e1";
	public static final String EXTRA_KEY_EFFECT = "e2";
	
	public static final String KEY_BITMAP = "a1";
	public static final String KEY_URL = "a2";
	public static final String KEY_EFFECTS_LIST = "a3";
	public static final String KEY_SOURCE_ID = "a5";
	
	public static final int REQUEST_CAMERA = 0;
	public static final int REQUEST_GALLERY = 1;
	public static final int CROP_IMAGE = 5;
	public static final int REQUEST_MIRROR = 3;

	public interface Bundle {
		String TEXT = "text";
		String COLOR_CODE = "color_code";
	}

	public interface CurrentFunction{
		String CAMERA = "camera";
		String GALLERY = "gallery";
		String COLLAGE = "collage";
		String BLUR = "blur";
		String MIRROR = "mirror";
		String RATE = "rate";
	}

	public interface FilterFunction{
		String BRIGHTNESS = "brightness";
		String CONTRAST = "contrast";
		String SATURATION = "saturation";
		String BLUR = "blur";
		String TINT = "tint";
		String SHARPEN = "sharpen";
		String FRAME = "frame";
		String FX = "fx";
	}

}
