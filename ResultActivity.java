package com.example.majisha.ebayshopping;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;


public class ResultActivity extends ActionBarActivity {


    private String url_search_php = "http://searchebay-env.elasticbeanstalk.com/my_index.php?";
    private JSONObject json = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_result);

        TextView t_resultHeader = (TextView) findViewById(R.id.t_resultHeader);
        t_resultHeader.setText("Results for '"+getIntent().getStringExtra("SEARCH_KEYWORD")+"'");

        try {
            json = new JSONObject(getIntent().getStringExtra("JSON_OBJECT"));

            Iterator<?> jsonKeys = json.keys();

            while(jsonKeys.hasNext()){
                String key = (String) jsonKeys.next();
                if(json.get(key) instanceof JSONObject){
                    if(key.toString().startsWith("item") && !key.toString().equals("itemCount")){

                        JSONObject item = json.getJSONObject(key);
                        JSONObject basicInfo = item.getJSONObject("basicInfo");

                        //Setting the title
                        int textid = getResources().getIdentifier("textView"+key.charAt(4), "id", "com.example.majisha.ebayshopping");
                        TextView t = (TextView)findViewById(textid);
                        t.setText(basicInfo.getString("title"));

                        //Set the image URL
                        int imgid = getResources().getIdentifier("imageView"+key.charAt(4), "id", "com.example.majisha.ebayshopping");
                        ImageView imgView = (ImageView)findViewById(imgid);
                        String imageUrl = basicInfo.getString("galleryURL");
                        new ImageLoader(imageUrl, imgView).execute();

                        //Set the pricing details
                        int priceid = getResources().getIdentifier("priceText"+key.charAt(4),"id","com.example.majisha.ebayshopping");
                        TextView pText = (TextView) findViewById(priceid);

                        if (basicInfo.getString("shippingServiceCost").isEmpty() || Float.parseFloat(basicInfo.getString("shippingServiceCost")) == 0.0){
                            String price_detail = "Price: $"+basicInfo.getString("convertedCurrentPrice")+"(FREE Shipping)";
                            pText.setText(price_detail);
                        }
                        else{
                            pText.setText("Price: $"+basicInfo.getString("convertedCurrentPrice")+"(+$"+basicInfo.getString("shippingServiceCost")+" for shipping)");
                        }

                    }

                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void openDetails(View v) throws JSONException{

        int id = v.getId();
        String ID = "";
        String title = "";
        String price_shipping = "";
        TextView title_text;
        TextView price_text;
        switch(id){
            case R.id.textView0:
                ID = "item0";

                title_text = (TextView)findViewById(R.id.textView0);
                title = title_text.getText().toString();
                price_text = (TextView)findViewById(R.id.priceText0);
                price_shipping = price_text.getText().toString();
                break;
            case R.id.textView1:
                ID = "item1";

                title_text = (TextView)findViewById(R.id.textView1);
                title = title_text.getText().toString();
                price_text = (TextView)findViewById(R.id.priceText1);
                price_shipping = price_text.getText().toString();
                break;
            case R.id.textView2:
                ID = "item2";

                title_text = (TextView)findViewById(R.id.textView2);
                title = title_text.getText().toString();
                price_text = (TextView)findViewById(R.id.priceText2);
                price_shipping = price_text.getText().toString();
                break;
            case R.id.textView3:
                ID = "item3";

                title_text = (TextView)findViewById(R.id.textView3);
                title = title_text.getText().toString();
                price_text = (TextView)findViewById(R.id.priceText3);
                price_shipping = price_text.getText().toString();
                break;
            case R.id.textView4:
                ID = "item4";

                title_text = (TextView)findViewById(R.id.textView4);
                title = title_text.getText().toString();
                price_text = (TextView)findViewById(R.id.priceText4);
                price_shipping = price_text.getText().toString();
                break;

        }


        Intent intent = new Intent(ResultActivity.this, DetailsActivity.class);
        intent.putExtra("ITEM_INFO", json.getJSONObject(ID).toString());
        intent.putExtra("ITEM_TITLE",title);
        intent.putExtra("ITEM_PRICE",price_shipping);
        startActivity(intent);


    }
    public void openWebPage(View v) throws JSONException {

        int id = v.getId();
        String ID = "";
        switch (id){
            case R.id.imageView0:
                ID = "item0";
                break;
            case R.id.imageView1:
                ID = "item1";
                break;
            case R.id.imageView2:
                ID = "item2";
                break;
            case R.id.imageView3:
                ID = "item3";
                break;
            case R.id.imageView4:
                ID = "item4";
                break;
        }
        String url = json.getJSONObject(ID).getJSONObject("basicInfo").getString("viewItemURL");
        Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(url));
        startActivity(viewIntent);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
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
