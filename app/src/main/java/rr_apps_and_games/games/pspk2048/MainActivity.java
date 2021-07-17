package rr_apps_and_games.games.pspk2048;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, GestureDetector.OnGestureListener {


    public static final int SWIPE_THRESHOLD = 100;
    public static final int SWIPE_VELOCITY_THRESHOLD = 100;
    Tile[][] buttonsGrid = new Tile[4][4];
    Tile[][] buttonsGridTemp = new Tile[4][4];
    public static int highScore=0;
    public static int highScoreTemp=0;
    public boolean firstMove=false;
    public int score=0;
    public int scoreTemp=0;
    public int isDone = 0;
    public GestureDetector mGestureDetector;
    private Random rand = new Random();
    private static final String PREFS_NAME = "MyPrefsFile";



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME,0);
        highScore = settings.getInt("highScore", highScore);
        displayHighScore(highScore,score);

        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                buttonsGrid[i][j] = new Tile(0,i,j,this);
            }
        }

        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                buttonsGridTemp[i][j] = new Tile(0,i,j,this);
            }
        }

        addRandomTile();
        addRandomTile();

        copyToTemp(buttonsGridTemp,buttonsGrid);

        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++) {
                buttonsGrid[i][j].mImageButton.setOnTouchListener(this);
            }
        }
        mGestureDetector = new GestureDetector(this,this);

        Button mnewGame = (Button)findViewById(R.id.newGame);
        final Button mUndo = (Button)findViewById(R.id.undo);
        mnewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<4;i++){
                    for(int j=0;j<4;j++){
                        buttonsGrid[i][j].modify(0);
                    }
                }

                for(int i=0;i<4;i++){
                    for(int j=0;j<4;j++){
                        buttonsGridTemp[i][j].modify(0);
                    }
                }
                score=0;
                scoreTemp=0;
                isDone=0;
                displayScore(score);
                //displayHighScore(highScore,score);
                addRandomTile();
                addRandomTile();
                copyToTemp(buttonsGridTemp,buttonsGrid);
            }
        });

        mUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(firstMove) {
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            buttonsGrid[i][j].modify(buttonsGridTemp[i][j].value);
                        }
                    }
                    score = scoreTemp;
                    highScore = highScoreTemp;
                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("highScore", highScore);
                    editor.commit();
                    displayHighScore(highScore, score);
                    displayScore(score);
                }
            }
        });

        if(endGame(buttonsGrid)){
            alertDialog();
        }


    }

    private void displayScore(int score) {
        TextView scoreView = (TextView) findViewById(R.id.score);
        scoreView.setText("Score: "+score);
    }
    private void displayHighScore(int highScore,int score) {
        TextView highScoreView = (TextView) findViewById(R.id.highScore);
        highScoreView.setText("Best: "+highScore);
    }

    private void alertDialogDone() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setCancelable(false);
        dialog.setMessage("YOU HAVE REACHED the 2048 tile! Would you like to continue? ");
        dialog.setTitle("CONGRATULATIONS!!");
        dialog.setPositiveButton("PLAY AGAIN",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        for(int i=0;i<4;i++){
                            for(int j=0;j<4;j++){
                                buttonsGrid[i][j].modify(0);
                            }
                        }
                        for(int i=0;i<4;i++){
                            for(int j=0;j<4;j++){
                                buttonsGridTemp[i][j].modify(0);
                            }
                        }
                        score=0;
                        scoreTemp=0;
                        displayScore(score);
                        isDone=0;
                        addRandomTile();
                        addRandomTile();
                        copyToTemp(buttonsGridTemp,buttonsGrid);
                    }
                });
        dialog.setNegativeButton("Continue",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                isDone++;
            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

    private void alertDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setCancelable(false);
        dialog.setMessage("THANK YOU FOR PLAYING :)");
        dialog.setTitle("GAME OVER!");
        dialog.setPositiveButton("PLAY AGAIN",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        for(int i=0;i<4;i++){
                            for(int j=0;j<4;j++){
                                buttonsGrid[i][j].modify(0);
                            }
                        }
                        for(int i=0;i<4;i++){
                            for(int j=0;j<4;j++){
                                buttonsGridTemp[i][j].modify(0);
                            }
                        }
                        score=0;
                        scoreTemp=0;
                        isDone=0;
                        displayScore(score);
                        addRandomTile();
                        addRandomTile();
                        copyToTemp(buttonsGridTemp,buttonsGrid);
                    }
                });
        dialog.setNegativeButton("EXIT",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences settings = getSharedPreferences(PREFS_NAME,0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("highScore",highScore);
                editor.commit();
                System.exit(0);

            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

    private boolean isGridFull(Tile[][] buttonsGrid) {
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<4;j++)
            {
                if(buttonsGrid[i][j].value==0)
                    return false;
            }
        }
        return true;
    }

    private boolean endGame(Tile[][] buttonsGrid) {

        if(isGridFull(buttonsGrid)) {

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (i - 1 >= 0 && buttonsGrid[i - 1][j].value != 0 && buttonsGrid[i - 1][j].value == buttonsGrid[i][j].value) {
                        return false;
                    }
                    else if (i+1 <= 3 && buttonsGrid[i+1][j].value != 0 && buttonsGrid[i+1][j].value == buttonsGrid[i][j].value) {
                        return false;
                    }
                    else if (j - 1 >= 0 && buttonsGrid[i][j-1].value != 0 && buttonsGrid[i][j-1].value == buttonsGrid[i][j].value) {
                        return false;
                    }
                    else if (j+1 <= 3 && buttonsGrid[i][j+1].value != 0 && buttonsGrid[i][j+1].value == buttonsGrid[i][j].value) {
                        return false;
                    }
                }
            }
        }
        else{
            return false;
        }

        return true;
    }

    private void addRandomTile() {
        int pos = rand.nextInt(4 * 4);
        int row, col;
        do {
            pos = (pos + 1) % (4 * 4);
            row = pos / 4;
            col = pos % 4;
        } while (buttonsGrid[row][col].value!=0);

        int val = rand.nextInt(10) == 0 ? 4 : 2;
        buttonsGrid[row][col] = new Tile(val,row,col,this);
        @SuppressLint("ResourceType") Animation animation1 =  AnimationUtils.loadAnimation(this, R.transition.fadein);
        buttonsGrid[row][col].mImageButton.startAnimation(animation1);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        mGestureDetector.onTouchEvent(motionEvent);

        return true;
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {

        firstMove=true;
        copyToTemp(buttonsGridTemp,buttonsGrid);
        scoreTemp=score;
        highScoreTemp=highScore;
        if(endGame(buttonsGrid)){
            alertDialog();
        }
        boolean flag = false;

        float diffX = motionEvent1.getX() - motionEvent.getX();
        float diffY = motionEvent1.getY() - motionEvent.getY();
        if (Math.abs(diffX) > Math.abs(diffY)) {//left or right swipe
            if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(v) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffX > 0) {
                    onSwipeRight();
                } else {
                    onSwipeLeft();
                }

            }
        } else {
            //up or down swipe
            if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(v1) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffY > 0) {
                    onSwipeDown();
                } else {
                    onSwipeUp();
                }

            }
        }

        displayScore(score);
        System.out.println("High SCOre is "+ highScore);
        displayHighScore(highScore,score);


        return true;
    }

    private void copyToTemp(Tile[][] temp, Tile[][] original) {
        for(int i=0;i<4;i++){
            for (int j=0;j<4;j++){
                temp[i][j].modify(original[i][j].value);
            }
        }
    }

    private void onSwipeDown(){
        boolean flag=false;
        //upToDown

        //move tiles
        for(int j=0;j<4;j++){
            for(int i=3;i>=0;i--){
                int k=i;
                while(k>0&&buttonsGrid[k][j].value==0){
                    int l=k;
                    while(l>0&&buttonsGrid[l-1][j].value==0){
                        l--;
                    }
                    System.out.println("l: "+l+" k: "+k+" i: "+i+" j: "+j);
                    if(l!=0) {
                        if(buttonsGrid[l-1][j].value!=0)
                            flag=true;
                        buttonsGrid[k][j].modify(buttonsGrid[l - 1][j].value);
                        buttonsGrid[l - 1][j].modify(0);
                    }
                    else if(buttonsGrid[l][j].value!=0){
                        if(buttonsGrid[l][j].value!=0)
                            flag=true;
                        buttonsGrid[k][j].modify(buttonsGrid[l][j].value);
                        buttonsGrid[l][j].modify(0);
                    }
                    k--;
                }
            }
        }

        //merge tiles
        for(int j=0;j<4;j++){
            for(int i=3;i>0;i--){
                if(buttonsGrid[i][j].value==buttonsGrid[i-1][j].value && buttonsGrid[i][j].value!=0){
                    if(buttonsGrid[i][j].value!=0)
                        flag=true;
                    buttonsGrid[i][j].modify(2*buttonsGrid[i-1][j].value);
                    buttonsGrid[i-1][j].modify(0);
                    Animation expandIn = AnimationUtils.loadAnimation(this, R.anim.expand_in);
                    buttonsGrid[i][j].mImageButton.startAnimation(expandIn);
                    if(buttonsGrid[i][j].value ==2048){
                        isDone++;
                    }
                    score+=buttonsGrid[i][j].value;
                    if(score>=highScore){
                        highScore=score;
                        SharedPreferences settings = getSharedPreferences(PREFS_NAME,0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putInt("highScore",highScore);
                        editor.commit();
                    }

                    if(i-1==2){
                        buttonsGrid[i-1][j].modify(buttonsGrid[i-2][j].value);
                        buttonsGrid[i-2][j].modify(buttonsGrid[i-3][j].value);
                        buttonsGrid[i-3][j].modify(0);
                    }
                    else if((i-1)==1){
                        buttonsGrid[i-1][j].modify(buttonsGrid[i-2][j].value);
                        buttonsGrid[i-2][j].modify(0);
                    }
                }
            }
        }

        if(!isGridFull(buttonsGrid) && flag) {
            addRandomTile();
        }
        if(endGame(buttonsGrid)){
            alertDialog();
        }
        if(isDone==1){
            alertDialogDone();
        }


    }

    private void onSwipeUp(){
        boolean flag=false;
        //downToUp

        //move tiles
        for(int j=0;j<4;j++){
            for(int i=1;i<4;i++){
                int k=i;
                while(k>0&&buttonsGrid[k-1][j].value==0){
                    if(buttonsGrid[k][j].value!=0){
                        flag=true;
                    }
                    buttonsGrid[k-1][j].modify(buttonsGrid[k][j].value);
                    buttonsGrid[k][j].modify(0);
                    k--;
                }
            }
        }

        //merge tiles
        for(int j=0;j<4;j++){
            for(int i=1;i<4;i++){
                if(buttonsGrid[i][j].value==buttonsGrid[i-1][j].value && buttonsGrid[i][j].value!=0){
                    if(buttonsGrid[i][j].value!=0){
                        flag=true;
                    }
                    buttonsGrid[i-1][j].modify(2*buttonsGrid[i][j].value);
                    buttonsGrid[i][j].modify(0);
                    Animation expandIn = AnimationUtils.loadAnimation(this, R.anim.expand_in);
                    buttonsGrid[i-1][j].mImageButton.startAnimation(expandIn);
                    if(buttonsGrid[i-1][j].value ==2048){
                        isDone++;
                    }
                    score+=buttonsGrid[i-1][j].value;
                    if(score>=highScore){
                        highScore=score;
                        SharedPreferences settings = getSharedPreferences(PREFS_NAME,0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putInt("highScore",highScore);
                        editor.commit();
                    }

                    if(i==1){
                        buttonsGrid[i][j].modify(buttonsGrid[i+1][j].value);
                        buttonsGrid[i+1][j].modify(buttonsGrid[i+2][j].value);
                        buttonsGrid[i+2][j].modify(0);
                    }
                    else if(i==2){
                        buttonsGrid[i][j].modify(buttonsGrid[i+1][j].value);
                        buttonsGrid[i+1][j].modify(0);
                    }
                }
            }
        }
        if(!isGridFull(buttonsGrid) && flag) {
            addRandomTile();
        }
        if(endGame(buttonsGrid)){
            alertDialog();
        }
        if(isDone==1){
            alertDialogDone();
        }
    }

    private void onSwipeLeft() {
        boolean flag=false;
        //rightToLeft

        //MoveTiles
        for(int i=0;i<4;i++){
            for(int j=1;j<4;j++){
                int k=j;
                while(k>0&&buttonsGrid[i][k-1].value==0){
                    if(buttonsGrid[i][k].value!=0){
                        flag=true;
                    }
                    buttonsGrid[i][k-1].modify(buttonsGrid[i][k].value);
                    buttonsGrid[i][k].modify(0);
                    k--;
                }
            }
        }

        //mergeTiles
        for(int i=0;i<4;i++){
            for(int j=1;j<4;j++){
                if(buttonsGrid[i][j].value==buttonsGrid[i][j-1].value && buttonsGrid[i][j].value!=0){
                    if(buttonsGrid[i][j].value!=0){
                        flag=true;
                    }
                    buttonsGrid[i][j-1].modify(2*buttonsGrid[i][j].value);
                    buttonsGrid[i][j].modify(0);
                    Animation expandIn = AnimationUtils.loadAnimation(this, R.anim.expand_in);
                    buttonsGrid[i][j-1].mImageButton.startAnimation(expandIn);
                    if(buttonsGrid[i][j-1].value ==2048){
                        isDone++;
                    }
                    score+=buttonsGrid[i][j-1].value;
                    if(score>=highScore){
                        highScore=score;
                        SharedPreferences settings = getSharedPreferences(PREFS_NAME,0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putInt("highScore",highScore);
                        editor.commit();
                    }
                    if(j==1){
                        buttonsGrid[i][j].modify(buttonsGrid[i][j+1].value);
                        buttonsGrid[i][j+1].modify(buttonsGrid[i][j+2].value);
                        buttonsGrid[i][j+2].modify(0);
                    }
                    else if(j==2){
                        buttonsGrid[i][j].modify(buttonsGrid[i][j+1].value);
                        buttonsGrid[i][j+1].modify(0);
                    }

                }
            }
        }

        if(!isGridFull(buttonsGrid) && flag) {
            addRandomTile();
        }
        if(endGame(buttonsGrid)){
            alertDialog();
        }
        if(isDone==1){
            alertDialogDone();
        }


    }

    private void onSwipeRight() {
        boolean flag=false;
        //leftToRight


        //MoveTiles
        for(int i=0;i<4;i++){
            for(int j=3;j>0;j--){
                int k=j;
                while(k>0&&buttonsGrid[i][k].value==0){
                    int l=k;
                    while(l>0&&buttonsGrid[i][l-1].value==0){
                        l--;
                    }
                    if(l!=0) {
                        if(buttonsGrid[i][l-1].value!=0)
                            flag=true;
                        buttonsGrid[i][k].modify(buttonsGrid[i][l - 1].value);
                        buttonsGrid[i][l - 1].modify(0);
                    }
                    else{
                        if(buttonsGrid[i][l].value!=0)
                            flag=true;
                        buttonsGrid[i][k].modify(buttonsGrid[i][l].value);
                        buttonsGrid[i][l].modify(0);
                    }
                    k--;
                }
            }
        }

        //mergeTiles
        for(int i=0;i<4;i++){
            for(int j=3;j>0;j--){
                if(buttonsGrid[i][j].value==buttonsGrid[i][j-1].value && buttonsGrid[i][j].value!=0){
                    if(buttonsGrid[i][j].value!=0){
                        flag=true;
                    }
                    buttonsGrid[i][j].modify(2*buttonsGrid[i][j-1].value);
                    buttonsGrid[i][j-1].modify(0);
                    Animation expandIn = AnimationUtils.loadAnimation(this, R.anim.expand_in);
                    buttonsGrid[i][j].mImageButton.startAnimation(expandIn);
                    if(buttonsGrid[i][j].value ==2048){
                        isDone++;
                    }
                    score+=buttonsGrid[i][j].value;
                    if(score>=highScore){
                        highScore=score;
                        SharedPreferences settings = getSharedPreferences(PREFS_NAME,0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putInt("highScore",highScore);
                        editor.commit();
                    }
                    if((j-1)==2){
                        buttonsGrid[i][j-1].modify(buttonsGrid[i][j-2].value);
                        buttonsGrid[i][j-2].modify(buttonsGrid[i][j-3].value);
                        buttonsGrid[i][j-3].modify(0);
                    }
                    else if((j-1)==1){
                        buttonsGrid[i][j-1].modify(buttonsGrid[i][j-2].value);
                        buttonsGrid[i][j-2].modify(0);
                    }
                }
            }
        }

        if(!isGridFull(buttonsGrid) && flag) {
            addRandomTile();
        }
        if(endGame(buttonsGrid)){
            alertDialog();
        }
        if(isDone==1){
            alertDialogDone();
        }


    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }


}