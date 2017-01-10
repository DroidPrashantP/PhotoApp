package com.app.paddycameraeditior.multitouchview; // 41 Post - Created by DimasTheDriver on May/24/2012 . Part of the 'Android - rendering a Path with a Bitmap fill' post. http://www.41post.com/?p=4766

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.app.paddycameraeditior.R;

public class PathRendererView extends View
{
	//Create a paint for the fill
	private Paint fillPaint;
	//Create a paint for the stroke
	private Paint strokePaint;
	
	//A Bitmap object that is going to be passed to the BitmapShader
	private Bitmap fillBMP;
	//The shader that renders the Bitmap
	private BitmapShader fillBMPshader;
	
	//A matrix object
	private Matrix m = new Matrix();
	
	//Two floats to store the touch position
	private float posX = 105;
	private float posY = 105;
	
	public PathRendererView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		
		//This View can receive focus, so it can react to touch events.
		this.setFocusable(true);
		
		//Initialize the strokePaint object and define some of its parameters
		strokePaint = new Paint();
		strokePaint.setDither(true);
		strokePaint.setColor(0xFFFFFF00);
		strokePaint.setStyle(Paint.Style.STROKE);    
		strokePaint.setAntiAlias(true);
		strokePaint.setStrokeWidth(3);
		
		//Initialize the bitmap object by loading an image from the resources folder
		fillBMP = BitmapFactory.decodeResource(context.getResources(), R.drawable.shape_heart);
		//Initialize the BitmapShader with the Bitmap object and set the texture tile mode
		fillBMPshader = new BitmapShader(fillBMP, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
		
		//Initialize the fillPaint object
		fillPaint = new Paint();
		fillPaint.setColor(0xFFFFFFFF);
		fillPaint.setStyle(Paint.Style.FILL);
		//Assign the 'fillBMPshader' to this paint
		fillPaint.setShader(fillBMPshader);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		//Store the position of the touch event at 'posX' and 'posY'
		if(event.getAction() == MotionEvent.ACTION_MOVE)
		{
			posX = event.getX();			
			posY = event.getY();	
			
			invalidate();
		}
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) 
	{
		//Invert the current matrix, so that the Bitmap background stays at the same position.
		//Only necessary if the canvas matrix is being transformed in some way.
		canvas.getMatrix().invert(m);
		//Assign the matrix to the BitmapShader. Again, not required to make this example work.
		fillBMPshader.setLocalMatrix(m);
		
		//Draw the fill
		canvas.drawCircle(posX, posY, 100, fillPaint);
		//Afterwards, draw the circle again, using the stroke paint
		canvas.drawCircle(posX, posY, 100, strokePaint);
	}
}
