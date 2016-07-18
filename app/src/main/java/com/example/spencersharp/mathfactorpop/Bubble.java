package com.example.spencersharp.mathfactorpop;

/**
 * Created by spencersharp on 6/11/16.
 */
public class Bubble
{
    long id;
    long xPosition;
    long factor;
    long initOffset;
    boolean isValidClick;
    boolean hasBeenClicked;

    public Bubble()
    {

    }

    public Bubble(long id, long xPosition, long initOffset, long factor, boolean isValidClick)
    {
        this.id = id;
        this.xPosition = xPosition;
        this.initOffset = initOffset;
        this.factor = factor;
        this.isValidClick = isValidClick;
        hasBeenClicked = false;
    }

    public void clicked()
    {
        hasBeenClicked = true;
    }

    public boolean hasBeenClicked()
    {
        return hasBeenClicked;
    }
    public long getID()
    {
        return id;
    }

    public long getXPosition()
    {
        return xPosition;
    }

    public long getYAtMilli(long milli)
    {
        return (long)(initOffset + (milli* GameActivityTime.incrementDistance));
    }

    public long getNumber()
    {
        return factor;
    }

    public boolean isValidClick()
    {
        return isValidClick;
    }

    public String toString()
    {
        return ""+factor;
    }
}