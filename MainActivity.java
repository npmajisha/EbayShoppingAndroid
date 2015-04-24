package com.example.majisha.ebayshopping;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.FacebookSdk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class MainActivity extends ActionBarActivity {
    private EditText input_keyword;
    private EditText input_pricefrom;
    private EditText input_priceto;
    private TextView t_result;
    private Spinner s_sortby;



    /**
     * Background Async Task to get all results by making HTTP Request
     * */
    class getSearchResult extends AsyncTask<String, String, JSONObject> {


        /**
         * getting All products from url
         * */
        @Override
        protected JSONObject doInBackground(String... args) {
            JSonParser jParser = new JSonParser();
            // Getting JSON from URL
            String url = null;
            String sort_order = "";
            try {

                String keyword = URLEncoder.encode(input_keyword.getText().toString(), "UTF-8");
                String pagination = "&results_per_page=5";
                String selectedSortby = s_sortby.getSelectedItem().toString();

                if(selectedSortby.equals("Best Match")){
                    sort_order =  "&sort_by=BestMatch";
                }
                else if(selectedSortby.equals("Price:highest first")){
                    sort_order = "&sort_by=CurrentPriceHighest";
                }
                else if(selectedSortby.equals("Price+Shipping:highest first")){
                    sort_order = "&sort_by=PricePlusShippingHighest";
                }
                else if(selectedSortby.equals("Price+Shipping:lowest first")){
                    sort_order = "&sort_by=PricePlusShippingLowest";
                }

                String pricefrom = input_pricefrom.getText().toString();
                String priceto = input_priceto.getText().toString();
                String item_filter = "";
                if(!pricefrom.isEmpty()){
                    item_filter = "&low_price_range="+pricefrom;
                }

                if(!priceto.isEmpty()){
                    item_filter +="&high_price_range="+priceto;
                }

                url = "http://searchebay-env.elasticbeanstalk.com/my_index.php?keywords=" + keyword + pagination + sort_order + item_filter;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return jParser.getJSONResults(url);
        }

        protected void onPostExecute(JSONObject json) {
            try {
                String result = json.getString("ack");

                if(result.equals("Success")){

                    Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                    intent.putExtra("JSON_OBJECT", json.toString());
                    intent.putExtra("SEARCH_KEYWORD",input_keyword.getText().toString());
                    startActivity(intent);
                }
                else {
                    t_result = (TextView) findViewById(R.id.t_result);
                    t_result.setText(result);
                }

            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_main);

        input_keyword = (EditText) findViewById(R.id.i_keyword);
        input_pricefrom = (EditText) findViewById(R.id.i_fromprice);
        input_priceto = (EditText) findViewById(R.id.i_toprice);
        t_result = (TextView) findViewById(R.id.t_result);
        s_sortby = (Spinner) findViewById(R.id.dropdown_sortby);

        //add onclick for clear button
        findViewById(R.id.button_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                input_keyword.setText("");
                input_pricefrom.setText("");
                input_priceto.setText("");
                t_result.setText("");
                input_keyword.setError(null);
                input_pricefrom.setError(null);
                input_priceto.setError(null);
                s_sortby.setSelection(0);

            }
        });


        //add onclick for search button
        findViewById(R.id.button_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean v_result = validate_input(v);
                //validation was successful, no errors -> send request to php server with parameters
                if (v_result){
                    new getSearchResult().execute();
                }

            }
        });
    }


    public boolean validate_input(View v){


        if (input_keyword.getText().toString().isEmpty()){

            input_keyword.setError("Please enter a keyword");
            return false;
        }

        if(!input_pricefrom.getText().toString().isEmpty() && !input_priceto.getText().toString().isEmpty() ) {
            if (Float.parseFloat(input_pricefrom.getText().toString()) > Float.parseFloat(input_priceto.getText().toString())) {

                input_pricefrom.setError("Price From should be less than or equal to Price To");
                return false;
            }
        }

        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
