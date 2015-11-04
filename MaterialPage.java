package com.example.josephsmith.materialpage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;


public class MaterialPage extends ActionBarActivity {

    CustomPageAdapter mCustomPageAdapter;
    ViewPager mViewPager;
    View mDummyView;
    LinearLayout mLayout;
    ScrollView mScrollView;
    String objectID = "tG2zpJsmna";
    TextView nameTextView, textTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_page);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        // Initialize Parse
        Parse.initialize(this, "Ry81veGB1n2Ly8K4qa22iYJCohAoS2U4B1ITsY1k", "FLzJTZZ0QBLQpjOEra6dY9p1v3UcIK7tTjmmGRzT");

             getInfo(objectID);


//        mCustomPageAdapter = new CustomPageAdapter(this, mResources);

//        mViewPager = (ViewPager) findViewById(R.id.pager);
        //      mViewPager.setAdapter(mCustomPageAdapter);
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        textTextView = (TextView) findViewById(R.id.textTextView);
        mScrollView = (ScrollView) findViewById(R.id.scrollView);

        mDummyView = findViewById(R.id.dummy);
        mDummyView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mScrollView.scrollTo(0, 0);
                mViewPager.dispatchTouchEvent(motionEvent);
                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_material_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void getInfo(String objectID) {
        //Get Parse object with objectID
        //Locate the Materials Library class
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Arup_Material_Samples");
        //Locate the objectID in this class


        query.getInBackground(objectID, new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject object, ParseException e) {

                String materialName = object.getString("materialName");
                nameTextView.setText(materialName);

                String materialText = object.getString("Text");
                textTextView.setText(materialText);

                //Get all Image file objects
                ParseFile fileObjectOne = (ParseFile) object.get("Image");

                //Convert all image file objects to Bitmaps in a background task
                //When done create a Bitmap[] array

                fileObjectOne.getDataInBackground(new GetDataCallback() {
                                                      @Override
                                                      public void done(byte[] data, ParseException e) {
                                                          if (e == null) {
                                                              //Decode the Bytes into a Bitmap
                                                              final Bitmap bitmap1 = BitmapFactory.decodeByteArray(data, 0, data.length);

                                                              Bitmap[] mResources = {bitmap1};

                                                              mCustomPageAdapter = new CustomPageAdapter(MaterialPage.this, mResources);
                                                              mViewPager = (ViewPager) findViewById(R.id.pager);
                                                              mViewPager.setAdapter(mCustomPageAdapter);

                                                          } else {
                                                              Log.d("test", "error#1");
                                                          }
                                                      }
                                                  }

                );
            }
        });


    }
}




