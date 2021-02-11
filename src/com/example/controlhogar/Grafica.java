package com.example.controlhogar;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class Grafica extends View {

DisplayMetrics mDisplayMetrics;
AssetManager mAssets;
Paint mPaint; 
int Width, Height;
InputStream sFocoE, sFocoA, sMaqueta, sBluetooth1, sBluetooth2, sOpen, sClose, sUps, sOn1, sOn2, sOf1, sOf2, sBluetooth3, sBluetooth4;
int[] posx={205,475, 650, 180, 445, 630, 205, 445, 630};
int[] posy={340, 325, 325, 580, 535, 535, 745, 715, 715};
int[] sizefoco={60,60};
int[] sizemaqueta={800, 1280};
int sizeFocoX=0;
int sizeFocoY=0;
BluetoothAdapter mBluetoothAdapter;
BluetoothDevice dispositivo;
BluetoothSocket socket;
InputStream ins;
OutputStream ons;
Context mContext;
String estadoBoton="Desconectado";
boolean enable=false;
int posBlueX=300;
int posBlueY=1080;
int posPuertaX=135;
int posPuertaY=800;
int sizePuertaX;
int sizePuertaY;
int sizeBlueX;
int sizeBlueY;
int posUX=100;
int posUY=20;
int posOnX=150;
int posOnY=900;
int posOfX=450;
int posOfY=900;
int sizeOnX;
int sizeOnY;
int sizeOfX;
int sizeOfY;
boolean puertaAbierta=false;
boolean tOn=false;
boolean tOf=false;
boolean touchedBlue=false;
String[] encender={"a", "b", "c","d", "e", "f", "g", "h", "i"};
String[] apagar={"j", "k", "l","m", "n", "o", "p", "q", "r"};

boolean[] estados={false, false, false, false, false, false, false, false, false};
	
Bitmap focoE, focoA, maqueta, bluetooth1, bluetooth2, open, close, ups, on1, on2, of1, of2, bluetooth3, bluetooth4;
Bitmap rFocoA, rFocoE, rMaqueta, rBluetooth1, rBluetooth2, rOpen, rClose, rUps, rOn1, rOn2, rOf1, rOf2, rBluetooth3, rBluetooth4;
int touched=0; 

	public Grafica(Context context, DisplayMetrics mDisplay, AssetManager assets, BluetoothAdapter mAdapter) {
		super(context);
		mContext=context;
		mBluetoothAdapter=mAdapter;
		mDisplayMetrics=mDisplay;
		mAssets=assets;
		mPaint=new Paint();
		mPaint.setColor(Color.BLUE);
		mPaint.setAntiAlias(true);
		
		Width=mDisplayMetrics.widthPixels;
		Height=mDisplayMetrics.heightPixels;
		
		try {
			sFocoA=mAssets.open("foco1.png");
			focoA=BitmapFactory.decodeStream(sFocoA);
			sFocoE=mAssets.open("foco2.png");
			focoE=BitmapFactory.decodeStream(sFocoE);
			sMaqueta=mAssets.open("maqueta.png");
			maqueta=BitmapFactory.decodeStream(sMaqueta);
			sBluetooth1=mAssets.open("blue1.png");
			bluetooth1=BitmapFactory.decodeStream(sBluetooth1);
			sBluetooth2=mAssets.open("blue2.png");
			bluetooth2=BitmapFactory.decodeStream(sBluetooth2);
			sBluetooth3=mAssets.open("blue3.png");
			bluetooth3=BitmapFactory.decodeStream(sBluetooth3);
			sBluetooth4=mAssets.open("blue4.png");
			bluetooth4=BitmapFactory.decodeStream(sBluetooth4);
			sOpen=mAssets.open("open.png");
			open=BitmapFactory.decodeStream(sOpen);
			sClose=mAssets.open("close.png");
			close=BitmapFactory.decodeStream(sClose);
			sUps=mAssets.open("logo.png");
			ups=BitmapFactory.decodeStream(sUps);
			sOn1=mAssets.open("on1.png");
			on1=BitmapFactory.decodeStream(sOn1);
			sOn2=mAssets.open("on2.png");
			on2=BitmapFactory.decodeStream(sOn2);
			sOf1=mAssets.open("off1.png");
			of1=BitmapFactory.decodeStream(sOf1);
			sOf2=mAssets.open("off2.png");
			of2=BitmapFactory.decodeStream(sOf2);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		rMaqueta=Bitmap.createScaledBitmap(maqueta,Width, Height, false);
		rFocoA=Bitmap.createScaledBitmap(focoA, Width*60/800, Height*60/1280, false);
		rFocoE=Bitmap.createScaledBitmap(focoE, Width*60/800, Height*60/1280, false);
		rBluetooth1=Bitmap.createScaledBitmap(bluetooth1, Width*200/800, Height*200/1280, false);
		rBluetooth2=Bitmap.createScaledBitmap(bluetooth2, Width*200/800, Height*200/1280, false);
		rBluetooth3=Bitmap.createScaledBitmap(bluetooth3, Width*200/800, Height*200/1280, false);
		rBluetooth4=Bitmap.createScaledBitmap(bluetooth4, Width*200/800, Height*200/1280, false);
		rOpen=Bitmap.createScaledBitmap(open, Width*120/800, Height*60/1280, false);
		rClose=Bitmap.createScaledBitmap(close, Width*120/800, Height*60/1280, false);
		rUps=Bitmap.createScaledBitmap(ups, Width*600/800, Height*160/1280, false);
		
		rOn1=Bitmap.createScaledBitmap(on1, Width*200/800, Height*100/1280, false);
		rOn2=Bitmap.createScaledBitmap(on2, Width*200/800, Height*100/1280, false);
		rOf1=Bitmap.createScaledBitmap(of1, Width*200/800, Height*100/1280, false);
		rOf2=Bitmap.createScaledBitmap(of2, Width*200/800, Height*100/1280, false);
		
		sizeFocoX=rFocoE.getWidth();
		sizeFocoY=rFocoE.getHeight();
		sizeBlueX=rBluetooth1.getWidth();
		sizeBlueY=rBluetooth1.getHeight();
		sizePuertaX=rOpen.getWidth();
		sizePuertaY=rOpen.getHeight();
		sizeOnX=rOn1.getWidth();
		sizeOnY=rOn1.getHeight();
		sizeOfX=sizeOnX;
		sizeOfY=sizeOnY;
		
		posBlueX=Width*posBlueX/800;
		posBlueY=Height*posBlueY/1280;
		posPuertaX=Width*posPuertaX/800;
		posPuertaY=Height*posPuertaY/1280;
		posUX=Width*posUX/800;
		posUY=Height*posUY/1280;
		posOnX=Width*posOnX/800;
		posOnY=Height*posOnY/1280;
		posOfX=Width*posOfX/800;
		posOfY=Height*posOfY/1280;
		
		
		for(int i=0; i<9; i++){
			posx[i]=posx[i]*Width/800;
			posy[i]=posy[i]*Height/1280;
			
			posx[i]=posx[i]-(sizeFocoX/2);
			posy[i]=posy[i]-(sizeFocoY/2);
		}
		
	}

	public void onDraw(Canvas c){
		super.onDraw(c);
		//c.drawCircle(400, 400, 40,mPaint);
		c.drawBitmap(rMaqueta, 0, 0, mPaint);
		c.drawBitmap(rUps,posUX, posUY, mPaint);
		for(int i=0; i<9; i++){
			if(estados[i]==false){
			c.drawBitmap(rFocoA, posx[i], posy[i], mPaint);
			}else{
			c.drawBitmap(rFocoE, posx[i], posy[i], mPaint);	
			}
		}
		
		
		
		if(puertaAbierta==true){
			c.drawBitmap(rOpen, posPuertaX, posPuertaY, mPaint);
		}else{
			c.drawBitmap(rClose, posPuertaX, posPuertaY, mPaint);
		}
		
		if(enable==false){
			if(touchedBlue==false){
			c.drawBitmap(rBluetooth1, posBlueX, posBlueY, mPaint);
			}else{
			c.drawBitmap(rBluetooth2, posBlueX, posBlueY, mPaint);	
			}
		}else{
			if(touchedBlue==false){
			c.drawBitmap(rBluetooth3, posBlueX, posBlueY, mPaint);
			}else{
			c.drawBitmap(rBluetooth4, posBlueX, posBlueY, mPaint);	
			}
		}
		
		if(tOn==false){
			c.drawBitmap(rOn1, posOnX, posOnY, mPaint);
		}else{
			c.drawBitmap(rOn2, posOnX, posOnY, mPaint);
		}
		
		if(tOf==false){
			c.drawBitmap(rOf1, posOfX, posOfY, mPaint);
		}else{
			c.drawBitmap(rOf2, posOfX, posOfY, mPaint);
		}
		
		invalidate();
	}
	
	
	public boolean onTouchEvent(MotionEvent m){
		
		switch(m.getAction()){
		case MotionEvent.ACTION_DOWN:
			float x=m.getX();
			float y=m.getY();
			
			for(int i=0; i<9; i++){
			if(x>posx[i]&&x<posx[i]+sizeFocoX&&y>posy[i]&&y<posy[i]+sizeFocoY){
				
					if(estados[i]==true){
						estados[i]=false;
						
						if(enable==true){
							enviar(1, apagar[i]);							
						}
												
					}else{
						estados[i]=true;
						if(enable==true){
						enviar(1, encender[i]);
						}						
					}
					
					
				}
			}
			
			if(x>posPuertaX&&x<posPuertaX+sizePuertaX&&y>posPuertaY&&y<posPuertaY+sizePuertaY){
			
				if(puertaAbierta==true){
					puertaAbierta=false;
					if(enable==true){
						enviar(2, "x");
					}
				}else{
					puertaAbierta=true;
					if(enable==true){
						enviar(2, "y");
					}
				}
				
				
			}
			
			if(x>posBlueX&&x<posBlueX+sizeBlueX&&y>posBlueY&&y<posBlueY+sizeBlueY){
				
				
				touchedBlue=true;
				if(enable==false){
				Conectar mConectar=new Conectar();				
				mConectar.execute("22:13:04:12:50:38");
				}else{
				
					enviar(4, "/");
					try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				enable=false;
				estadoBoton="Desconectar";
				
				
				}

			}
			
			if(x>posOnX&&x<posOnX+sizeOnX&&y>posOnY&&y<posOnY+sizeOnY){
				tOn=true;
				
				for(int i=0; i<9; i++){
					estados[i]=true;
				}
				
				if(enable==true){
					enviar(3, "#");
				}
			}
			if(x>posOfX&&x<posOfX+sizeOfX&&y>posOfY&&y<posOfY+sizeOfY){
				tOf=true;
				for(int i=0; i<9; i++){
					estados[i]=false;
				}
				
				if(enable==true){
					enviar(4, "/");
				}
				
			}
			
			
		break;	
	
		
		case MotionEvent.ACTION_UP:
			
if(tOn==true){
	tOn=false;
}

if(tOf==true){
	tOf=false;
}

if(touchedBlue==true){
	touchedBlue=false;
}

		break;
		
		default:
			
		break;	
		
		}
		
		
		return true;
		
	}
	
	public class Conectar extends AsyncTask<String, String,String>{

		@SuppressLint("NewApi")
		@Override
		protected String doInBackground(String... disp) {
			String estado;
			
			String d=disp[0];
			dispositivo=mBluetoothAdapter.getRemoteDevice(d);
			
			
			try{
				socket=dispositivo.createInsecureRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
				socket.connect();
				ins=socket.getInputStream();
				ons=socket.getOutputStream();
				estado = "Conectado";
				return estado;
			}catch(Exception e){
				
			    estado = "Error";
			    return estado;
			}
		
		}
		
	protected void onPostExecute(String res){
		String mEstado= res;
			
			
			if (mEstado=="Conectado"){
				
				estadoBoton="Desconectar";		
				enable=true;			
					for(int i=0; i<9;i++){
						
						if(estados[i]==true){
							enviar(1, encender[i]);
						}else{
							enviar(1, apagar[i]);
						}
						
						SystemClock.sleep(30);
					}					
									
			}else{
				
				estadoBoton="Conectar";
				enable=false;
				Toast.makeText(mContext, "Ahorita no joven", android.widget.Toast.LENGTH_SHORT).show();	
			}
			
	}
		
	}

	
	
	@SuppressLint("NewApi")
	public void enviar(int function, String a){
		
		switch(function){
		
		case 1:
			try {
				ons.write(a.getBytes(Charset.forName("UTF-8")));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		break;
		
		case 2:
			String f;
			if(puertaAbierta==true){
				f="x";
			}else{
				f="y";
			}
			try {
				ons.write(f.getBytes(Charset.forName("UTF-8")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		break;
		
		case 3:
			String f1="#";
			try {
				ons.write(f1.getBytes(Charset.forName("UTF-8")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		break;
		
		case 4:
			
			String f2="/";
			try {
				ons.write(f2.getBytes(Charset.forName("UTF-8")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		break;
		
		
		}
		
	}
	
}


