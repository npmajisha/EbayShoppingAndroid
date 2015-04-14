package com.example.majisha.ebayshopping;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {
    private EditText input_keyword;
    private EditText input_pricefrom;
    private EditText input_priceto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input_keyword = (EditText) findViewById(R.id.i_keyword);
        input_pricefrom = (EditText) findViewById(R.id.i_fromprice);
        input_priceto = (EditText) findViewById(R.id.i_toprice);


        findViewById(R.id.button_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean v_result = validate_input(v);

            }
        });
    }


    public boolean validate_input(View v){


        if (input_keyword.getText().toString().isEmpty()){

            input_keyword.setError("Please enter a keyword");
            return false;
        }


        if (Float.parseFloat(input_pricefrom.getText().toString()) > Float.parseFloat(input_pricefrom.getText().toString())){

            input_pricefrom.setError("Price From should be less than or equal to Price To");
            return false;
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
