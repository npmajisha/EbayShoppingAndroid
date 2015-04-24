package com.example.majisha.ebayshopping;

import android.app.ActionBar;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;


public class DetailsActivity extends ActionBarActivity {

    private static JSONObject itemInfo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ImageView imgView = (ImageView) findViewById(R.id.imgItem);
        TextView tView = (TextView)findViewById(R.id.textView_title);
        tView.setText(getIntent().getStringExtra("ITEM_TITLE"));
        tView = (TextView)findViewById(R.id.textView_price);
        tView.setText(getIntent().getStringExtra("ITEM_PRICE"));
        try {
            itemInfo = new JSONObject(getIntent().getStringExtra("ITEM_INFO"));
            new ImageLoader(itemInfo.getJSONObject("basicInfo").getString("pictureURLSuperSize"), imgView).execute();
            tView = (TextView) findViewById(R.id.textView_location);
            tView.setText(itemInfo.getJSONObject("basicInfo").getString("location"));

            imgView = (ImageView) findViewById(R.id.img_top);
            if(itemInfo.getJSONObject("basicInfo").getString("topRatedListing").equals("false")){

                imgView.setVisibility(View.INVISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TabHost tabhost = (TabHost)findViewById(android.R.id.tabhost);
        tabhost.setup();

        TabSpec tab1 = tabhost.newTabSpec("Basic Info");
        tab1.setIndicator("Basic Info");
        tab1.setContent(R.id.tab1);
        tabhost.addTab(tab1);

        TabSpec tab2 = tabhost.newTabSpec("Seller");
        tab2.setIndicator("Seller");
        tab2.setContent(R.id.tab2);
        tabhost.addTab(tab2);

        TabSpec tab3 = tabhost.newTabSpec("Shipping");
        tab3.setIndicator("Shipping");
        tab3.setContent(R.id.tab3);
        tabhost.addTab(tab3);


    }

    public void buyNow(View v) throws JSONException {

        String url = itemInfo.getJSONObject("basicInfo").getString("viewItemURL");
        Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(url));
        startActivity(viewIntent);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
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
}
