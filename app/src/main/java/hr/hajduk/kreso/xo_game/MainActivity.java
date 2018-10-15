package hr.hajduk.kreso.xo_game;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];

    private boolean p1Turn = true;

    private int roundCount;

    private int p1Points;
    private int p2Points;

    private TextView tvP1;
    private TextView tvP2;
    private TextView tvResP1;
    private TextView tvResP2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        result();



        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_settings:
                Intent intent = new Intent(this, CustomPreferenceActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_reset:
                p1Points = 0;
                p2Points = 0;
                tvP1.setText("player1");
                tvP2.setText("player2");
                updatePointsText();
                resetBoard();
                return true;


            default:
                return super.onOptionsItemSelected(item);

        }

    }

    // KOD IGRE

    public void result(){
        tvResP1 = findViewById(R.id.tv_res_p1);
        tvResP2 = findViewById(R.id.tv_res_p2);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "btn_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

    }


    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (p1Turn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }
        roundCount++;

        if (checkForWin()) {
            if (p1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            p1Turn = !p1Turn;
        }

    }




    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    private void player1Wins() {

        p1Points++;
        Toast.makeText(this, "XXX!!!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void player2Wins() {
        p2Points++;
        Toast.makeText(this, "OOO!!!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }



    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void resetBoard() {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        p1Turn = true;
    }

    private void updatePointsText() {
        tvResP1.setText(""  + p1Points);
        tvResP2.setText("" + p2Points);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("p1Points", p1Points);
        outState.putInt("p2Points", p2Points);
        outState.putBoolean("p1Turn", p1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        p1Points = savedInstanceState.getInt("p1Points");
        p2Points = savedInstanceState.getInt("p2Points");
        p1Turn = savedInstanceState.getBoolean("p1Turn");

    }

    private void setPreferencePlayer1(){

        tvP1 = findViewById(R.id.tv_p1);
        String player1 = tvP1.getText().toString();
        if (player1 == player1){
            return;
        }

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("nickname_1",player1);
        editor.commit();
        Toast.makeText(this,R.string.name_set,Toast.LENGTH_SHORT).show();

    }

    private void setPreferencePlayer2(){

        tvP2 = findViewById(R.id.tv_p2);
        String player2 = tvP2.getText().toString();
        if (player2 == player2){

            return;
        }

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("nickname_2",player2);
        editor.commit();
        Toast.makeText(this,R.string.name_set,Toast.LENGTH_SHORT).show();

    }



    private void getPreferenceP1(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        if (sp.contains("nickname_1")){
            tvP1.setText(sp.getString("nickname_1", null));
        }
    }

    private void getPreferenceP2(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        if (sp.contains("nickname_2")){
            tvP2.setText(sp.getString("nickname_2", null));
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        setPreferencePlayer1();
        getPreferenceP1();
        setPreferencePlayer2();
        getPreferenceP2();


    }

}
