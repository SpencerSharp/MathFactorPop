package com.example.spencersharp.mathfactorpop;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class BubbleViewTime extends Button implements View.OnClickListener
{
    public static long bubbleRadius = GameActivityTime.bubbleRadius; //Radius of the bubble in dp
    public static double incrementDistance = GameActivityTime.incrementDistance;
    public static long incrementTime = GameActivityTime.tickRate-10;
    public static long startPoint = GameActivityTime.screenHeight;
    public static long radius = GameActivityTime.bubbleRadius;
    Context context = GameActivityTime.context;

    Bubble bubble;

    public BubbleViewTime(Context context) {
        super(context);
        init();
    }

    public BubbleViewTime(Context context, Bubble bubble)
    {
        super(context);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(0,0);
        lp.height = (int)radius*2;
        lp.width = (int)radius*2;
        setLayoutParams(lp);

        setPadding(0, 0, 0, 0);
        setBackgroundResource(R.drawable.circle);
        //setId(1);

        //setWidth(150);
        //setHeight(150);
        setTextSize(25.0f);
        /*setTextColor(0x000000);*/

        this.bubble = bubble;
        setX(bubble.getXPosition());
        setY((float)startPoint);

        setText(""+bubble.getNumber());
        init();

        setPadding(0,0,0,0);
        //setHeight(40);
        //setId((int)getID());
    }

    private void init(){
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.d("click", "clicked");
        bubble.clicked();
        final MediaPlayer mp = MediaPlayer.create(context, R.raw.sound);
        //mp.release();
        //mp.setVolume(0.9f, 0.9f);
        mp.start();

        this.setVisibility(View.GONE);
    }

    public long getID()
    {
        return bubble.getID();
    }

    public long getXPosition()
    {
        return bubble.getXPosition();
    }

    public void increment()
    {
        double curY = getY();
        //setY((float)(curY-incrementDistance));
        this.animate().x(getX()).y((float)(curY-incrementDistance)).setDuration(70).start();
    }

    public String toString()
    {
        return ""+bubble;
    }
}
