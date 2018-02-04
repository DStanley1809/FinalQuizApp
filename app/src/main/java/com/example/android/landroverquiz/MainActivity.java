package com.example.android.landroverquiz;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    // Variable used to track under score, initialised to 0 and incremented by 1 for each correct answer
    int userScore = 0;

    // Specify maximum available score. Maximum available score should be the same as the number of questions
    int maxScore = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set background colour of buttons by calling the setButtonColor method
        setButtonColor("#05703b", R.id.submitButton);
        setButtonColor("#05703b", R.id.resetButton);
        setButtonColor("#05703b", R.id.shareButton);

        // Populate question 3 drop down box/spinner with the options defined in Strings
        Spinner q3Spinner = (Spinner) findViewById(R.id.q3Spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.wheelbase_lengths, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        q3Spinner.setAdapter(adapter);
    }

    /**
     * This method is called when the submit button is clicked.
     */
    public void submitAnswers(View view) {

        userScore = 0;

        // Evaluate answer to question 1
        String q1Answer = getQ1Answer();
        if (q1Answer.equals("Rover Company")) {
            userScore = userScore + 1;
        } else if (q1Answer.equals("Rover")) {
            userScore = userScore + 1;
        }

        // Evaluate answer to question 2
        if (getQ2Answer()) {
            userScore += 1;
        }

        // Evaluate answer to question 3
        String q3Answer = getQ3Answer();
        if (q3Answer.equals("80 inches")) {
            userScore += 1;
        }

        // Evaluate answer to question 4
        if (getQ4Answer()) {
            userScore += 1;
        }

        //Evaluate answer to question 5
        Boolean c5_cb1State = getCheckBoxState(R.id.q5_cb1);
        Boolean c5_cb2State = getCheckBoxState(R.id.q5_cb2);
        Boolean c5_cb3State = getCheckBoxState(R.id.q5_cb3);
        Boolean c5_cb4State = getCheckBoxState(R.id.q5_cb4);
        Boolean c5_cb5State = getCheckBoxState(R.id.q5_cb5);
        Boolean c5_cb6State = getCheckBoxState(R.id.q5_cb6);
        Boolean c5_cb7State = getCheckBoxState(R.id.q5_cb7);
        Boolean c5_cb8State = getCheckBoxState(R.id.q5_cb8);

        if (c5_cb1State && c5_cb2State && c5_cb4State && c5_cb6State && !c5_cb3State && !c5_cb5State && !c5_cb7State && !c5_cb8State) {
            userScore += 1;
        }

        // Makes hidden view containing reset button visible
        Button resetButtonVisibility = (Button) findViewById(R.id.resetButton);
        resetButtonVisibility.setVisibility(view.VISIBLE);

        // Toast message displaying user score. If score is maximum, user gets congratulations message. If score is not maximum user is asked to try again.
        if (userScore == maxScore) {
            Toast userScoreMessage = Toast.makeText(this, "Congratulations! Your score is " + userScore + "/" + maxScore, Toast.LENGTH_LONG);
            userScoreMessage.show();
        } else if (userScore < maxScore) {
            Toast userScoreMessage2 = Toast.makeText(this, "Your score is " + userScore + "/" + maxScore + "\nPlease try again", Toast.LENGTH_LONG);
            userScoreMessage2.show();
        }

        // Hide quiz and show score, reset button and share button
        LinearLayout quizViewVisibility = (LinearLayout) findViewById(R.id.quizLayout);
        quizViewVisibility.setVisibility(view.GONE);

        LinearLayout resultShareScreenVisibility = (LinearLayout) findViewById(R.id.resultShareScreen);
        resultShareScreenVisibility.setVisibility(view.VISIBLE);
    }

    /**
     * Get text from user's answer to Question 1
     */
    private String getQ1Answer() {
        EditText q1Answer = (EditText) findViewById(R.id.textEntryQ1);
        String answer1 = q1Answer.getText().toString().trim();
        return answer1;
    }

    /**
     * Get user's answer to question 2
     */
    private boolean getQ2Answer() {
        RadioButton mauriceWilks = (RadioButton) findViewById(R.id.option2);
        Boolean mauriceWilksState = mauriceWilks.isChecked();
        return mauriceWilksState;
    }

    /**
     * Get user's answer to question 3
     */
    private String getQ3Answer() {
        Spinner q3Answer = (Spinner) findViewById(R.id.q3Spinner);
        String answer3 = q3Answer.getSelectedItem().toString();
        return answer3;
    }

    /**
     * Get user's answer to question 4
     */
    private boolean getQ4Answer() {
        ToggleButton landRoverJeep = (ToggleButton) findViewById(R.id.q4ToggleButton);
        Boolean landRoverJeepState = landRoverJeep.isChecked();
        return landRoverJeepState;
    }

    /**
     * Check state of CheckBoxes
     */
    private boolean getCheckBoxState(int cb_ID) {
        CheckBox checkCB = (CheckBox) findViewById(cb_ID);
        Boolean checkCBState = checkCB.isChecked();
        return checkCBState;
    }

    /**
     * Clear CheckBoxes
     */
    private void clearCheckBoxes(int cb_id) {
        CheckBox clearCB = (CheckBox) findViewById(cb_id);
        clearCB.setChecked(false);
    }

    /**
     * Change color of button
     */
    public void setButtonColor(String color, int buttonID) {
        Resources res = getResources();
        GradientDrawable shape = (GradientDrawable) res.getDrawable(R.drawable.oval_button);
        shape.setColor(Color.parseColor(color));
        Button button = (Button) findViewById(buttonID);
        button.setBackground(shape);
    }

    /**
     * Reset state of all answers, hide Reset Button, show Submit Button
     */
    public void resetScores(View view) {

        // Reset Q1
        EditText clearText = (EditText) findViewById(R.id.textEntryQ1);
        clearText.setText("");

        // Reset Q2
        RadioButton spencerWilks = (RadioButton) findViewById(R.id.option1);
        spencerWilks.setChecked(false);

        RadioButton mauriceWilks = (RadioButton) findViewById(R.id.option2);
        mauriceWilks.setChecked(false);

        RadioButton charlesSpencerKing = (RadioButton) findViewById(R.id.option3);
        charlesSpencerKing.setChecked(false);

        RadioButton gerryMcGovern = (RadioButton) findViewById(R.id.option4);
        gerryMcGovern.setChecked(false);

        // Reset Q3
        Spinner clearSpinner = (Spinner) findViewById(R.id.q3Spinner);
        clearSpinner.setSelection(0);

        // Reset Q4
        ToggleButton clearButton = (ToggleButton) findViewById(R.id.q4ToggleButton);
        clearButton.setChecked(false);

        // Reset Q5
        clearCheckBoxes(R.id.q5_cb1);
        clearCheckBoxes(R.id.q5_cb2);
        clearCheckBoxes(R.id.q5_cb3);
        clearCheckBoxes(R.id.q5_cb4);
        clearCheckBoxes(R.id.q5_cb5);
        clearCheckBoxes(R.id.q5_cb6);
        clearCheckBoxes(R.id.q5_cb7);
        clearCheckBoxes(R.id.q5_cb8);

        // Hide Reset Button
        /*Button resetButtonVisibility = (Button) findViewById(R.id.resetButton);
        resetButtonVisibility.setVisibility(view.INVISIBLE);*/

        // Show quiz and hide score, reset button and share button
        LinearLayout quizViewVisibility = (LinearLayout) findViewById(R.id.quizLayout);
        quizViewVisibility.setVisibility(view.VISIBLE);

        LinearLayout resultShareScreenVisibility = (LinearLayout) findViewById(R.id.resultShareScreen);
        resultShareScreenVisibility.setVisibility(view.GONE);
    }

    /**
     * Share score
     */
    public void shareScore (View view) {
        String share_text = getResources().getString(R.string.share_text);
        Intent intent = new Intent (Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, share_text + (userScore + "/" + maxScore).toString());
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, getResources().getText(R.string.send_to)));

    }


}
