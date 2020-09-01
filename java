package com.example.smubf08.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */


    public void increment(View view) {
        if (quantity==100){
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        }

        quantity = quantity + 1;
        displayQuantity(quantity);

    }

    public void decrement(View viw) {
        if (quantity==1){
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }

        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox= (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean haschocolate=chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream,haschocolate);
        String priceMessage =createOrderSummary(name,price,hasWhippedCream,haschocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just java order for" + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


        displayMessage(priceMessage);

    }

    private int calculatePrice(boolean addWhippedCream, boolean addchocolate) {
        int baseprice=5;
        if (addWhippedCream){
            baseprice +=1;
        }
        if (addchocolate) {
            baseprice +=2;
        }
        return quantity*baseprice;

    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addchocolate){
        String priceMessage = getString(R.string.order_summary_name,name);
        priceMessage += "\nAdd whipped cream? " + addWhippedCream;
        priceMessage += "\nAdd chocolate cream? "+addchocolate;
        priceMessage+= "\nQuantity: "+quantity;
        priceMessage+= "\nTotal: $" + price;
        priceMessage += "\n" +getString(R.string.thank_you);
        return priceMessage;
    }
}
