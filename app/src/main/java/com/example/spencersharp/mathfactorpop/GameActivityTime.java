package com.example.spencersharp.mathfactorpop;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class GameActivityTime extends AppCompatActivity
{
    public static final long bubbleRadius = 120;
    public static long bubbleMillisOnScreen = 5000;
    public static final long totalMillis = 30000;
    public static Thread th;
    public static long screenHeight = 1600;
    public static long screenWidth = 700;
    public static double incrementDistance = -1*((double)screenHeight/(double)bubbleMillisOnScreen);

    public static TextView showTick;
    public static TextView scoreBox;
    public static TextView numberBox;
    public static TextView clockField;

    public static RelativeLayout relativeLayout;
    public int score;
    static long tickRate;
    ArrayList<Bubble> bubbles;
    ArrayList<BubbleViewTime> bubbleViews;

    static Context context;

    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        relativeLayout = (RelativeLayout) findViewById(R.id.gameActivityRelativeLayout);

        context = this;

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.backgroundmusic);
        int difficulty = getIntent().getIntExtra(MainActivity.difficultyKey, 1);

        if(difficulty == 3)
            bubbleMillisOnScreen = 2500;
        else if(difficulty == 2)
            bubbleMillisOnScreen = 4000;
        else if(difficulty == 1)
            bubbleMillisOnScreen = 5000;
        else if(difficulty == 0)
            bubbleMillisOnScreen = 6500;
        mp.setLooping(true);
        mp.setVolume(0.5f, 0.5f);


        bubbleViews = new ArrayList<>();
        bubbles = new ArrayList<>();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay()
                .getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        incrementDistance = -1*((double)screenHeight/(double)bubbleMillisOnScreen);
        /*
        Bubble bubble = new Bubble(0,100,0,500,8,false);
        bubbles.add(bubble);
        BubbleView bV = new BubbleView(this,bubble);
        bV.setId(0);
//        bV.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
//        bV.setText(R.string.hello);
//        bV.setPadding(20,20,20,20);
        Log.d("buttonText", "" + bV.getText());

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.gameActivityRelativeLayout);
        //RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        relativeLayout.addView(bV);
        bubbleViews = new ArrayList<BubbleView>();
        bubbleViews.add(bV);*/

        showTick = new TextView(this);
        scoreBox = new TextView(this);
        numberBox = new TextView(this);
        clockField = new TextView(this);
        scoreBox.setText("0");
        scoreBox.setX(0);
        numberBox.setTextSize(30.0f);
        numberBox.setX(screenWidth/2);
        numberBox.setText("72");
        relativeLayout.addView(showTick);
        relativeLayout.addView(scoreBox);
        relativeLayout.addView(numberBox);
        clockField.setX(screenWidth/2);
        clockField.setY(screenHeight - 200);
        clockField.setTextSize(30.0f);
        relativeLayout.addView(clockField);

        //Bitmap bitmap = Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888);
        //Canvas c = new Canvas(bitmap);
        //Paint paint = new Paint();
        //paint.setColor(0x4785F4);
        //c.drawCircle(10.0f, 10.0f, 10.0f, paint);
        //Button b = new Button(this);

        //bV.setBackgroundResource();
        /**/
        bubbles = generateBubblesFromParams(0.0, 0);

        mp.start();
        //Count down 3...2...1...Go!
        timer = new CountDownTimer(totalMillis, 15) {

            public void onTick(long millisUntilFinished) {
                clockField.setText(""+millisUntilFinished / 1000);
                Log.d("onTickCalled", "" + (totalMillis - millisUntilFinished));
                updateLoop(totalMillis - millisUntilFinished);
            }

            public void onFinish() {
                clockField.setText("done!");
            }
        }.start();

        //startUpdateLoopThread();
    }

    /*
    private void startUpdateLoopThread()
    {
        th = new Thread(new Runnable() {
            @Override
            public void run() {
                while (curTick < totalTicks) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateLoop();
                            Log.d("tickNum", "" + curTick);
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                        Log.d("error", "" + e.getStackTrace());
                        break;
                    }
                }
            }
        });
        th.start();
    }
    */

    public long generateNumberBeingFactored(double difficulty)
    {
        /*
        TreeMap<Integer,Double> posNumbersWithDifficulties = new TreeMap<>();
        ArrayList<Integer> numbersInDifficultyRange = new ArrayList<Integer>();
        for(Map.Entry<Integer,Double> entry : posNumbersWithDifficulties.entrySet()) {
            Integer key = entry.getKey();
            Double value = entry.getValue();

            if(value >= difficulty - 0.05 && value <= difficulty + 0.05)
                numbersInDifficultyRange.add(key);
        }
        long numberBeingFactoredIndex = (long)(Math.random() * (numbersInDifficultyRange.size() + 1));
        long numberBeingFactored = numbersInDifficultyRange.get((int)numberBeingFactoredIndex);
        return numberBeingFactored;*/

        //Penalty if sone goes off the screen
        //Color on bubbles
        //Infinite mode, pop enough bubbles to change number at the top
        //Different bubble sizes and speeds
        boolean hasEnoughFactors = false;
        long ret = 0;
        while(!hasEnoughFactors)
        {
            long rand = (long)(Math.random() * ((100 - 50) + 1) + 50);
            int numFactors = 0;
            for(int i = 1; i <=rand; i++)
            {
                if(rand%i==1)
                    numFactors++;
            }
            if(numFactors>3)
            {
                hasEnoughFactors = true;
                ret = rand;
            }
        }
        return ret;
    }

    public long genFactorOf(long numberBeingFactored)
    {
        boolean isFactorOf = false;
        long ret = -1;
        while(!isFactorOf)
        {
            long num = (long)(Math.random() * ((numberBeingFactored - 1) + 1) + 1);
            if(numberBeingFactored%num==0 && num!=1 && num!=numberBeingFactored) {
                ret = num;
                isFactorOf = true;
            }
        }
        return ret;
    }

    public long genNonFactorOf(long numberBeingFactored)
    {
        boolean isFactorOf = true;
        long ret = -1;
        while(isFactorOf)
        {
            long num = (long)(Math.random() * ((numberBeingFactored - 1) + 1) + 1);
            if(numberBeingFactored%num!=0) {
                ret = num;
                isFactorOf = false;
            }
        }
        return ret;
    }

    public long generateNumberInBubble(long numberBeingFactored)
    {
        long randoFactor = (long)(Math.random() * ((73 - 1) + 1) + 1);
        double isFactorProportion = 0.6;
        double rand = Math.random();
        if(rand <= isFactorProportion)
        {
            randoFactor = genFactorOf(numberBeingFactored);
        }
        else
            randoFactor = genNonFactorOf(numberBeingFactored);
        return randoFactor;
    }

    public boolean isFactorOf(long factor, long numberBeingFactored)
    {
        return numberBeingFactored%factor==0;
    }

    public ArrayList<Bubble> generateBubblesFromParams(double difficulty, long runTimeInSeconds)
    {
        ArrayList<Bubble> retBubbles = new ArrayList<Bubble>();
        //Main variables
        //Tick rate
        //Size of numbers
        //Variables for tick rate
        long numBubbles = 49;
        //Code for main number at the top

        long numberBeingFactored = generateNumberBeingFactored(difficulty);
        numberBox.setText("" + numberBeingFactored);
        while(retBubbles.size()<numBubbles)
        {
            //Code to determine xPosition - TODO: Make sure it doesnt overlap with other bubbles
            long xPosition = (long)(Math.random() * (screenWidth-(bubbleRadius*2) + 1));
            //Code for determining initOffset
            long initTime = (long)(Math.random() * (((totalMillis-bubbleMillisOnScreen) - 10) + 1) + 10);
            long initOffset =(long) (initTime*incrementDistance)*(-1);
            Log.d("initOffset",""+initOffset);
            //Code for number in the bubble
            long factor = generateNumberInBubble(numberBeingFactored);
            //Code for if the bubble is a valid click
            boolean isFactor = isFactorOf(factor,numberBeingFactored);

            if(!isTooCloseToAnother(xPosition,initOffset,retBubbles)) {
                //Create the bubble and add it to the arraylist
                Bubble bubble = new Bubble(retBubbles.size(), xPosition, initOffset, factor, isFactor);
                retBubbles.add(bubble);
            }
        }
        return retBubbles;
    }

    public boolean isTooCloseToAnother(long xPosition, long initOffset, ArrayList<Bubble> retBubbles)
    {
        Log.d("retBubblesSize",""+retBubbles.size());
        for(Bubble bubble : retBubbles)
        {
            if(initOffset>=bubble.getYAtMilli(0)-(bubbleRadius*2) && initOffset <= bubble.getYAtMilli(0)+(bubbleRadius*2)) {
                if (xPosition >= bubble.getXPosition() - (bubbleRadius*2) && xPosition <= bubble.getXPosition() + (bubbleRadius*2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void updateLoop(long curMilli)
    {
        score = 0;
        //Move existing views (animate?)
        for(BubbleViewTime bV : bubbleViews)
        {
            bV.setY(bV.bubble.getYAtMilli(curMilli));
        }
        //Add new views
        for(Bubble bubble : bubbles)
        {
            long bubbleID = bubble.getID();
            long yOnMilli = bubble.getYAtMilli(curMilli);
            //Log.d("yOnMilli",""+yOnMilli);
            if(yOnMilli >= 0 && yOnMilli <= screenHeight)
            {
                boolean doesViewForBubbleAlreadyExist = false;
                for(BubbleViewTime bV : bubbleViews)
                {
                    if(bV.getID()==bubbleID)
                        doesViewForBubbleAlreadyExist = true;
                }
                if(!doesViewForBubbleAlreadyExist)
                {
                    //Add new bubbleView
                    Log.d("added bV",""+bubble);
                    bubbleViews.add(new BubbleViewTime(this,bubble));
                    relativeLayout.addView(bubbleViews.get(bubbleViews.size() - 1));
                }
            }
            if(bubble.hasBeenClicked())
            {
                if(bubble.isValidClick)
                    score++;
                else
                    score--;
            }
        }


        //Delete views off the screen
        int index = 0;
        while(index < bubbleViews.size())
        {
            long yOnMilli = bubbleViews.get(index).bubble.getYAtMilli(curMilli);
            if(yOnMilli > screenHeight)
            {
                BubbleViewTime remove = bubbleViews.remove(index);
                relativeLayout.removeView(remove);
            } else
                index++;
        }
        Log.d("bubbleViews", "" + bubbleViews);
        //Increment tick
        scoreBox.setText(""+score);
    }
}