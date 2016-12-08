package com.nicksong.indicateprogress;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

public class MainActivity extends ActionBarActivity {
	
	private IndicateProgressView indicateProgressView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}
	
	private void initView() {
		indicateProgressView = (IndicateProgressView)findViewById(R.id.indicate_progress);
		indicateProgressView.setMax(100);
		indicateProgressView.setProgress(80);
	}

}
