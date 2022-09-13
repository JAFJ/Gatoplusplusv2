package com.example.jafj_gatoplusplus;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView pts_JugadorUno, pts_JugadorDos, status_juego;
    private Button[] buttons = new Button[36];
    private Button reset;

    private int contador_JugadorUno, contador_JugadorDos, contadormax;
    boolean jugadorActivo;

    //J1=>0
    //J2=>0
    //vacío =>2
    int[] gameState = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2};

    int[][]winningPositions = {
            {0,1,2,3},{6,7,8,9},{12,13,14,15},{18,19,20,21},{24,25,26,27},{30,31,32,33},{1,2,3,4},{7,8,9,10},{13,14,15,16},{19,20,21,22},{25,26,27,28},{31,32,33,34},{2,3,4,5},{8,9,10,11},{14,15,16,17},{20,21,22,23},{26,27,28,29},{32,33,34,35},{0,6,12,18},{1,7,13,19},{2,8,14,20},{3,9,15,21},{4,10,16,22},{5,11,17,23},{6,12,18,24},{7,13,19,25},{8,14,20,26},{9,15,21,27},{10,16,22,28},{11,17,23,29},{12,18,24,30},{13,19,25,31},{14,20,26,32},{15,21,27,33},{16,22,28,34},{17,23,29,35},{12,19,26,33},{6,13,20,27},{0,7,14,21},{1,8,15,22},{2,9,16,23},{8,15,22,29},{14,21,28,35},{13,20,27,34},{7,14,21,28},{18,13,8,3},{19,14,9,4},{20,15,10,5},{26,21,16,11},{32,27,22,17},{24,19,14,9},{30,25,20,15},{31,26,21,16},{25,20,15,10}
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pts_JugadorUno = (TextView) findViewById(R.id.pts_JugadorUno);
        pts_JugadorDos = (TextView) findViewById(R.id.pts_JugadorDos);
        status_juego = (TextView) findViewById(R.id.status_juego);

        reset = (Button) findViewById(R.id.reset);

        for(int i=0; i< buttons.length; i++){
            String buttonID = "btn_" + i;
            int resourceID = getResources().getIdentifier(buttonID,"id", getPackageName());
            buttons[i] = (Button) findViewById(resourceID);
            buttons[i].setOnClickListener(this);

        }

        contadormax =0;
        contador_JugadorUno =0;
        contador_JugadorDos =0;
        jugadorActivo = true;
    }

    @Override
    public void onClick(View v) {
        if(!((Button)v).getText().toString().equals("")){
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1, buttonID.length()));

        if(jugadorActivo){
            ((Button)v).setText("X");
            ((Button)v).setTextColor(Color.parseColor("#d200ff"));
            gameState[gameStatePointer] = 0;
        }else {
            ((Button)v).setText("O");
            ((Button)v).setTextColor(Color.parseColor("#00ffd2"));
            gameState[gameStatePointer] = 1;
        }
        contadormax++;

        if(checkWinner()){
            if(jugadorActivo){
                contador_JugadorUno++;
                updatePts_Jugador();
                Toast.makeText(this, "¡El jugador uno ha ganado!", Toast.LENGTH_SHORT).show();
                JugarOtraVez();
            }else{
                contador_JugadorDos++;
                updatePts_Jugador();
                Toast.makeText(this, "¡El jugador dos ha ganado!", Toast.LENGTH_SHORT).show();
                JugarOtraVez();
            }
        }else if (contadormax==36){
            Toast.makeText(this, "¡Es un empate!", Toast.LENGTH_SHORT).show();
        }else {
            jugadorActivo =! jugadorActivo;
        }
        if (contador_JugadorUno>contador_JugadorDos){
            status_juego.setText("El jugador uno toma la delantera!");
        }else if(contador_JugadorDos>contador_JugadorUno){
            status_juego.setText("El jugador dos está ganando!");
        }else{
            status_juego.setText("");
        }
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JugarOtraVez();
                contador_JugadorUno=0;
                contador_JugadorDos=0;
                status_juego.setText("");
                updatePts_Jugador();
            }
        });
    }

    public boolean checkWinner(){
        boolean winnerResult = false;

        for(int[] winningPosion:winningPositions) {
            if (gameState[winningPosion[0]] == gameState[winningPosion[1]] &&
                    gameState[winningPosion[1]] == gameState[winningPosion[2]] &&
                        gameState[winningPosion[2]] == gameState[winningPosion[3]] &&
                            gameState[winningPosion[0]] != 2) {
                winnerResult = true;
            }
        }
        return winnerResult;

        }
        public void updatePts_Jugador(){
            pts_JugadorUno.setText(Integer.toString(contador_JugadorUno));
            pts_JugadorDos.setText(Integer.toString(contador_JugadorDos));


        }
        public void JugarOtraVez(){
        contadormax=0;
        jugadorActivo=true;

        for(int i=0; i< buttons.length; i++){
            gameState[i]=2;
            buttons[i].setText("");
        }
        }
    }