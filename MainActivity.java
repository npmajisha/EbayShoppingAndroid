package com.example.majisha.ebayshopping;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends ActionBarActivity {
    private EditText input_keyword;
    private EditText input_pricefrom;
    private EditText input_priceto;
    private TextView t_result;



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
            String url = "http://searchebay-env.elasticbeanstalk.com/my_index.php?keywords=" + input_keyword.getText().toString();
            JSONObject json = jParser.getJSONResults(url);
            return json;
        }

        protected void onPostExecute(JSONObject json) {
            try {
                String result = json.getString("ack");
                t_result = (TextView) findViewById(R.id.t_result);
                t_result.setText(result);

            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input_keyword = (EditText) findViewById(R.id.i_keyword);
        input_pricefrom = (EditText) findViewById(R.id.i_fromprice);
        input_priceto = (EditText) findViewById(R.id.i_toprice);

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

            }
        });


        //add onclick for search button
        findViewById(R.id.button_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean v_result = validate_input(v);
                //validation was successful, no errors -> send request to php server with parameters
                if (v_result ==  true){
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
