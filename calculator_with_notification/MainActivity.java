package com.example.labtuto;
//Change this to your package name !!!!!!!!!!

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MainActivity extends AppCompatActivity {

    private EditText num1, num2;
    private Spinner opselect;
    private Button bt;
    private static final String CHANNEL_ID = "calculator_result";
    private static final int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create notification channel
        createNotificationChannel();

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

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Calculator Results";
            String description = "Channel for calculator results";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // Register the channel with the system
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showNotification(String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Calculator Result")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        try {
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            // Check for notification permission (required for Android 13+)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) ==
                        android.content.pm.PackageManager.PERMISSION_GRANTED) {
                    notificationManager.notify(NOTIFICATION_ID, builder.build());
                } else {
                    Toast.makeText(this, "Notification permission required", Toast.LENGTH_SHORT).show();
                }
            } else {
                notificationManager.notify(NOTIFICATION_ID, builder.build());
            }
        } catch (SecurityException e) {
            Toast.makeText(this, "Notification permission required", Toast.LENGTH_SHORT).show();
        }
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

        // Show the result in a notification
        String resultMessage = String.format("%s: %.2f %s %.2f = %.2f",
                operation, number1, getOperationSymbol(operation), number2, result);
        showNotification(resultMessage);
    }

    private String getOperationSymbol(String operation) {
        switch (operation) {
            case "Addition": return "+";
            case "Subtraction": return "-";
            case "Multiplication": return "×";
            case "Division": return "÷";
            default: return "";
        }
    }
}