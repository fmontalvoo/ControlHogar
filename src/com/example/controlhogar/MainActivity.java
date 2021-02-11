package com.example.controlhogar;

import java.io.InputStream;
import java.io.OutputStream;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends ActionBarActivity {
Grafica mGraf;
DisplayMetrics mDisplay;
AssetManager mAssets;
BluetoothAdapter mBluetoothAdapter;
private static final int REQUEST_ENABLE_BT=3;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDisplay=new DisplayMetrics();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	    WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getWindowManager().getDefaultDisplay().getMetrics(mDisplay);
	    mAssets=getAssets();		
		encenderBluetooth();
		mGraf=new Grafica(MainActivity.this, mDisplay, mAssets, mBluetoothAdapter);
		setContentView(mGraf);

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

	
	public void encenderBluetooth(){
		mBluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
		if(!mBluetoothAdapter.isEnabled()){
			Intent enableBtIntent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		}else{

		}
			
		
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data ){
	if(requestCode==REQUEST_ENABLE_BT&& resultCode==RESULT_OK){		
		Toast.makeText(MainActivity.this, "Bluetooth Encendido", android.widget.Toast.LENGTH_LONG).show();
			}else{
				finish();
			}
	}
	
}
