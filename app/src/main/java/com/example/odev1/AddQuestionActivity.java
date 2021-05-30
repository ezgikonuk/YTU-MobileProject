package com.example.odev1;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddQuestionActivity extends AppCompatActivity {
    private Button SaveQuestion;
    public String selectedLevel;
    private EditText Question;
    private EditText Option1;
    private EditText Option2;
    private EditText Option3;
    private EditText Option4;
    private RadioGroup answerSelectionGroup;
    private RadioButton answerRadio;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addquestion);
        Database db = new Database(this);
        Question = (EditText) findViewById(R.id.questionText);
        Option1 = (EditText) findViewById(R.id.option1);
        Option2 = (EditText) findViewById(R.id.option2);
        Option3 = (EditText) findViewById(R.id.option3);
        Option4 = (EditText) findViewById(R.id.option4);
        SaveQuestion = (Button) findViewById(R.id.saveQuestion);
        answerSelectionGroup = (RadioGroup) findViewById(R.id.radioGroup);

        SaveQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = (answerSelectionGroup.getCheckedRadioButtonId());
                answerRadio = (RadioButton) findViewById(selectedId);
                if (Question.getText().length() > 0 && Option1.getText().length() > 0 && Option2.getText().length() > 0 && Option3.getText().length() > 0 && Option4.getText().length() > 0 && selectedLevel != "0" && selectedId != 0) {
                    createQuestion(Question.getText().toString(), Option1.getText().toString(), Option2.getText().toString(), Option3.getText().toString(), Option4.getText().toString(), selectedLevel, answerRadio.getText().toString());
                } else {
                    Toast.makeText
                            (getApplicationContext(), "Lütfen tüm alanları doldurun.", Toast.LENGTH_SHORT)
                            .show();
                }
            }

            private void createQuestion(String question, String option1, String option2, String option3, String option4, String level, String answer) {
                Boolean response = db.addQuestion(question, option1, option2, option3, option4, level, answer);
                if (response == true) {
                    Toast.makeText
                            (getApplicationContext(), "Soru kaydedildi.", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Toast.makeText
                            (getApplicationContext(), "Beklenmeyen bir hata oluştu.", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });


        // Get reference of widgets from XML layout
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Initializing a String Array
        String[] LEVELS = new String[]{
                "1",
                "2",
                "3",
                "4",
                "5"
        };

        final List<String> LEVEL_LIST = new ArrayList<>(Arrays.asList(LEVELS));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, LEVEL_LIST) {

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTextColor(Color.BLACK);
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLevel = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                // Notify the selected item text
                Toast.makeText
                        (getApplicationContext(), "Zorluk seviyesi : " + selectedLevel, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
