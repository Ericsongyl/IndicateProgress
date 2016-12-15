package com.nicksong.indicateprogress;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

@SuppressLint("DrawAllocation")
public class IndicateProgressView extends View {
	
	private int backgroundColor = 0xfff5f5f5;
	private int startProgressColor = 0xfff29310;
	private int endProgressColor = 0xfffd715a;
	private int indicateTextColor = 0xffef4f37;
	private int radius = 10; //进度条四个角的角度px
	private int indicatorRadius = 32; //进度指示器四个角的角度px
	private int defaultContentMargin = 2;
	private int defaultIndicateMargin = 30;
	private int max = 100;
	private int progress;
	private int textSize = 32;
	private String indicateText;
	private String PERCENT_STR = "100%";
	private Paint backPaint;
	private Paint progressPaint;
	private Paint indicateBackPaint;
	private Paint indicateTextPaint;

	public IndicateProgressView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public IndicateProgressView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		init(context, attributeSet);
	}
	
	private void init(Context context, AttributeSet attributeSet) {
		TypedArray taArray = context.obtainStyledAttributes(attributeSet, R.styleable.IndicateProgressView);
		if (taArray != null) {
			backgroundColor = taArray.getColor(R.styleable.IndicateProgressView_backgroundColor, backgroundColor);
			startProgressColor = taArray.getColor(R.styleable.IndicateProgressView_startProgressColor, startProgressColor);
			endProgressColor = taArray.getColor(R.styleable.IndicateProgressView_endProgressColor, endProgressColor);
			indicateTextColor = taArray.getColor(R.styleable.IndicateProgressView_indicateTextcolor, indicateTextColor);
			taArray.recycle();
		}
		//初始化进度条背景画笔
		backPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		backPaint.setColor(backgroundColor);
		backPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		//初始化进度条进度画笔
		progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//		progressPaint.setColor(startProgressColor);
		progressPaint.setStyle(Paint.Style.FILL);
		//初始化进度条指示器背景框画笔
		indicateBackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		indicateBackPaint.setColor(indicateTextColor);
		indicateBackPaint.setStyle(Paint.Style.FILL);
		//初始化进度条指示器文本画笔
		indicateTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		indicateTextPaint.setColor(indicateTextColor);
		indicateTextPaint.setTextSize(textSize);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		int width = getWidth();
		int height = getHeight();
		
		//画背景
		RectF backRectF = new RectF(0, height * 2 / 5, width, height * 3 / 5);
		backPaint.setColor(backgroundColor);
		canvas.drawRoundRect(backRectF, radius, radius, backPaint);
		
		//画进度
		RectF progressRectF = new RectF(0, height * 2 / 5, width * getScale(), height * 3 / 5);
		LinearGradient lGradient = new LinearGradient(0, height * 2 / 5, width * getScale(), height * 3 / 5, 
				startProgressColor, endProgressColor, Shader.TileMode.MIRROR);
		progressPaint.setShader(lGradient);
		canvas.drawRoundRect(progressRectF, radius, radius, progressPaint);
		
		//画指示器边框
		float left = width * getScale() - indicateTextPaint.measureText(PERCENT_STR) - defaultIndicateMargin;
		float right = width * getScale();
		if (left <= 0f) {
			left = 0f;
			right = indicateTextPaint.measureText(PERCENT_STR) + defaultIndicateMargin;
		}
		if (right <= indicateTextPaint.measureText(PERCENT_STR) + defaultIndicateMargin) {
			right = indicateTextPaint.measureText(PERCENT_STR) + defaultIndicateMargin;
		}
		RectF indicatorRectF = new RectF(left, height / 5, right, height * 4 / 5);
		indicateBackPaint.setColor(indicateTextColor);
		canvas.drawRoundRect(indicatorRectF, indicatorRadius, indicatorRadius, indicateBackPaint);
		
		//画指示器内部为白色
		RectF indicatorContentRectF = new RectF(left + defaultContentMargin, height / 5 + defaultContentMargin,
				right - defaultContentMargin, height * 4 / 5 - defaultContentMargin);
		indicateBackPaint.setColor(Color.WHITE);
		canvas.drawRoundRect(indicatorContentRectF, indicatorRadius, indicatorRadius, indicateBackPaint);
		
		//画指示器文本
		indicateTextPaint.setTextSize(height * 2 / 5);
		float textX = indicatorContentRectF.centerX() - indicateTextPaint.measureText(indicateText) / 2;
		float textY = backRectF.centerY() + height / 8;
		canvas.drawText(indicateText, textX, textY, indicateTextPaint);
	}
	
	public void setBackgroundColor(int color) {
		this.backgroundColor = color;
		backPaint.setColor(backgroundColor);
		postInvalidate();
	}
	
	public void setStartProgressColor(int color) {
		this.startProgressColor = color;
//		progressPaint.setColor(startProgressColor);
		postInvalidate();
	}
	
	public void setEndProgressColor(int color) {
		this.endProgressColor = color;
//		progressPaint.setColor(endProgressColor);
		postInvalidate();
	}
	
	public void setIndicateTextColor(int color) {
		this.indicateTextColor = color;
		indicateTextPaint.setColor(indicateTextColor);
		postInvalidate();
	}
	
	public void setMax(int max) {
		this.max = max;
	}
	
	public void setProgress(int progress) {
		this.progress = progress;
	}
	
	private float getScale() {
		float scale;
		if (max == 0) {
			scale = 0;
		} else {
			scale = (float)progress / (float)max;
		}
		setIndicateText((int)(scale * 100) + "%");
		return scale;
	}
	
	private void setIndicateText(String indicateStr) {
		this.indicateText = indicateStr;
	}

}
