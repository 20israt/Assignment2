package com.example.assignment2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private CheckBox gluten_free, vegan, dairy_free;
    ArrayList<String> arr = new ArrayList<>();

    private RadioGroup radioGroup;
    private RadioButton radioButton;

    private TextView drTextView, tomTextView, main, level;
    private Button placeOrder;


    private AlertDialog.Builder builder;
    private SeekBar seekBar;
    private TextView textView;
    private Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        gluten_free = findViewById(R.id.Gfree);
        vegan = findViewById(R.id.Vegan);
        dairy_free = findViewById(R.id.Dfree);
        drTextView = findViewById(R.id.text);
        radioGroup = findViewById(R.id.radioGroup);

        placeOrder = findViewById(R.id.order_btn);
        builder = new AlertDialog.Builder(this);

        seekBar = findViewById(R.id.seekBar);
        textView = findViewById(R.id.value);
        aSwitch = findViewById(R.id.btn_switch);

        gluten_free.setOnCheckedChangeListener((buttonView, isChecked) -> {
            check(buttonView, isChecked);
        });
        vegan.setOnCheckedChangeListener((buttonView, isChecked) -> {
            check(buttonView, isChecked);
        });
        dairy_free.setOnCheckedChangeListener((buttonView, isChecked) -> {
            check(buttonView, isChecked);
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = findViewById(checkedId);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText("Spiciness Level: " + progress + " / " + seekBar.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    seekBar.setProgress(40);
                    textView.setText("Spiciness level: " + 40 + " / " + seekBar.getMax());
                } else {
                    seekBar.setProgress(60);
                    textView.setText("Spiciness level: " + 60 + " / " + seekBar.getMax());
                }
            }
        });



        placeOrder.setOnClickListener(v -> {
            try {
                if (arr.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Select Dietry Restrictions!!", Toast.LENGTH_SHORT).show();
                }
                String radioValue = radioButton.getText().toString();

                            builder.setMessage("Order Summary:\n" + "Dietry Restrictions: " + arr + "\nType of meal: " + radioValue  +  "\nspiciness level: " + seekBar.getProgress() +"\nThank you!!")
                            .setCancelable(false)
                            .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    drTextView.setText("");
                                    Intent intent = new Intent(MainActivity.this, RatingActivity.class);
                                    startActivity(intent);
                                    gluten_free.setChecked(false);
                                    vegan.setChecked(false);
                                    dairy_free.setChecked(false);
                                    radioGroup.clearCheck();
                                    seekBar.setProgress(0);
                                }
                            }).show();
            } catch (Exception e){
                Toast.makeText(getApplicationContext(), "Please Select Type of meal", Toast.LENGTH_SHORT).show();
            }
        });

    }
    void check(CompoundButton buttonView, Boolean isChecked){
        if (isChecked) {
            arr.add(buttonView.getText().toString());
            Log.d("array", String.valueOf(arr));
        } else{
            arr.remove(buttonView.getText().toString());
        }
        drTextView.setText(String.valueOf(arr));
    }
}
