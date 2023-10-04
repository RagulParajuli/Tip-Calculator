package com.example.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText BillText, PeopleText;
    private ImageView iconView;
    private TextView mainText;
    private TextView result;
    private Spinner tipSpinner;
    private Button calculateButton;
    private ArrayList<String> tipList;
    private int tip;
    private ArrayAdapter<String> adapter;
    private static final String Tip_Spinner = "Tip";
    private static final int tip_max = 20;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BillText = findViewById(R.id.BillText);
        PeopleText = findViewById(R.id.PeopleText);
        iconView = findViewById(R.id.iconView);
        mainText = findViewById(R.id.mainText);
        calculateButton = findViewById(R.id.calculateButton);
        tipSpinner = findViewById(R.id.tipSpinner);
        result = findViewById(R.id.result);

        tipList = new ArrayList<>();
        addToSpinner();

        adapter = new ArrayAdapter<>(this, R.layout.spinner_layout, tipList);
        adapter.setDropDownViewResource(R.layout.spinner_drop_down);
        tipSpinner.setAdapter(adapter);

        tipSpinner.setOnItemSelectedListener(this);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateTotal();
            }
        });
    }

    // Calculate total bill, tip, and divide into each consumer;
    public void calculateTotal() {
        String billString = BillText.getText().toString();
        String peopleString = PeopleText.getText().toString();

        if (billString.isEmpty() || peopleString.isEmpty()) {
            Toast.makeText(this, "Enter Bill Amount and Number of People", Toast.LENGTH_SHORT).show();
            return;
        }

        double bill = Double.parseDouble(billString);
        int numOfPeople = Integer.parseInt(peopleString);
        DecimalFormat df2 = new DecimalFormat("0.00");
        double tipDollar = bill * (tip / 100.00);
        double totalBill = bill + tipDollar;

        String output = "Total Bill: " + df2.format(totalBill) + "\n" +
                "Tip: " + df2.format(tipDollar) + "\n" +
                "Each Pay: " + df2.format(totalBill / numOfPeople);

        result.setText(output);
    }

    // Add elements to the spinner
    public void addToSpinner() {
        tipList.add(Tip_Spinner);
        for (int i = 1; i <= tip_max; i++) {
            tipList.add(String.valueOf(i));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // This method will be called when an item is selected in the Spinner.
        tip = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // This method will be called when nothing is selected in the Spinner.
    }
}