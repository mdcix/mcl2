package com.example.labtuto;
//Change this to your package name !!!!!!!!!!

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {

    private EditText num1, num2;
    private Spinner opselect;
    private Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        opselect = findViewById(R.id.opselect);
        bt = findViewById(R.id.button);

        // Set up the spinner for selecting operation
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Addition", "Subtraction", "Multiplication", "Division"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        opselect.setAdapter(adapter);

        // Set the button click listener
        bt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                calculateResult();
            }
        });

    }

    private void calculateResult() {
        // Get input numbers
        String num1Str = num1.getText().toString();
        String num2Str = num2.getText().toString();

        // Check if inputs are empty
        if (num1Str.isEmpty() || num2Str.isEmpty()) {
            Toast.makeText(this, "Please enter both numbers", Toast.LENGTH_SHORT).show();
            return;
        }

        // Parse numbers
        double number1 = Double.parseDouble(num1Str);
        double number2 = Double.parseDouble(num2Str);

        // Get selected operation
        String operation = opselect.getSelectedItem().toString();
        double result = 0;

        // Perform the calculation based on the selected operation
        switch (operation) {
            case "Addition":
                result = number1 + number2;
                break;
            case "Subtraction":
                result = number1 - number2;
                break;
            case "Multiplication":
                result = number1 * number2;
                break;
            case "Division":
                if (number2 != 0) {
                    result = number1 / number2;
                } else {
                    Toast.makeText(this, "Cannot divide by zero", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            default:
                Toast.makeText(this, "Invalid operation selected", Toast.LENGTH_SHORT).show();
                return;
        }

        // Show the result as a notification and toast message
        Toast.makeText(this, "Result: " + result, Toast.LENGTH_SHORT).show();
    }

}
