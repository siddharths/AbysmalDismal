package com.yahoo.ads.reporting;

import java.util.ArrayList;
import java.util.List;

import com.yahoo.ads.reporting.util.StackedBarChart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity implements OnClickListener {

	private Button trendsButton;
	private Button vendorsButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		trendsButton = ((Button) findViewById(R.id.button1));
		vendorsButton = ((Button) findViewById(R.id.button2));

		trendsButton.setOnClickListener(this);
		vendorsButton.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.button1:
		//	intent = new Intent(this, TrendActivity.class);
		//	startActivity(intent);
			
			intent = new StackedBarChart().execute(this);
			startActivity(intent);
			break;
		case R.id.button2:
			intent = new Intent(this, VendorListActivity.class);

			ArrayList<String> vendors = new ArrayList<String>();
			vendors.add("vendor1");
			vendors.add("vendor2");
			vendors.add("vendor3");
			vendors.add("vendor4");
			vendors.add("vendor5");
			intent.putStringArrayListExtra("vendors", vendors);

			startActivity(intent);
			break;

		default:
			break;
		}

	}

}
