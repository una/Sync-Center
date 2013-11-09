package com.example.hackyaleandroid;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;


public class Sync_Center extends Activity 
{
	
	EditText text;
	Button plusButton; 
	Button deleteButton;
	
	//temp 
	Button[] buttons = new Button[10];  
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync__center);
        
        TextView text = new TextView(this);
        text.setText("hackYale");
        
        plusButton = (Button) findViewById(R.id.plus);
        plusButton.setOnClickListener(new OnClickListener()
        {
        	public void onClick(View v)
        	{
        		Toast.makeText(getApplicationContext(), "Button Pressed", Toast.LENGTH_SHORT).show();
        	}
        });
        
        
        deleteButton = (Button) findViewById(R.id.delete);
        
        ScrollView buttonslayout = new ScrollView(this);
        buttonslayout.setOrientation(ScrollView.VERTICAL);
        
        for(int i=0; i < buttons.length; i++)
        {
        	Button button = new Button(this);
        	button.setId(i+1);
        	button.setText("NEW Buttons"+(i+1));
        	
        	button.setOnClickListener(new OnClickListener()
        	{
        		public void onClick(View v)
        		{
        			Toast.makeText(getApplicationContext(), "Button Pressed", Toast.LENGTH_SHORT).show();
        		}
        	});
        	buttonslayout.addView(button);
        }
        //((ViewGroup) findViewById(R.id.scrollView)).addView(buttonslayout);
        
        //setContentView(findViewById(R.id.scrollView));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sync__center, menu);
        return true;
    }
    
}
