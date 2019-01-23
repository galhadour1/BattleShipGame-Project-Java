package BattleShipLogic;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class GameTime {
    private byte m_SecondsOfPlaying;
    private int m_MinutesOfPlaying;

    public GameTime(){
        m_MinutesOfPlaying = 0;
        m_SecondsOfPlaying = 0;
    }

    public GameTime(Date beginTime, Date endTime){
        CalculateTime(beginTime, endTime);
    }

    public void SetGameTime(int minutesToSet, byte secondsToSet){
        this.m_MinutesOfPlaying = minutesToSet;
        this.m_SecondsOfPlaying = secondsToSet;
    }

    @Override
    public String toString() {
        return String.format("%dM:%dS", m_MinutesOfPlaying, m_SecondsOfPlaying);
    }


    public void CalcAverageTime(GameTime gameTime, int attacksCount){
        long playingSeconds = ConvertTimeToSeconds(gameTime);

        if (attacksCount!=0)
            playingSeconds /= attacksCount;
        else
            playingSeconds=0;
        AddTime(playingSeconds);
    }

    public void CalculateTime(Date beginTime, Date endTime){
        long seconds = CalcAttackTimeInSeconds(beginTime.getTime(), endTime.getTime());
        SetGameTime((int)(seconds/60), (byte) (seconds%60));
    }

    // This function calculates the move time by getting the starting time and ending time
    // in milliseconds.
    // It returns the time in m_SecondsOfPlaying.
    public long CalcAttackTimeInSeconds(long beginTime, long endTime){
        beginTime = TimeUnit.MILLISECONDS.toSeconds(beginTime);
        endTime = TimeUnit.MILLISECONDS.toSeconds(endTime);

        return (endTime - beginTime);
    }

    public static long ConvertTimeToSeconds(GameTime gameTime){

        long newSeconds;

        newSeconds = gameTime.m_SecondsOfPlaying + (gameTime.m_MinutesOfPlaying * 60);

        return newSeconds;
    }

    public static GameTime ConvertSecondToTime(long secondsToConvert){
        GameTime newTime = new GameTime();

        if(secondsToConvert > 0) {
            newTime.m_SecondsOfPlaying = (byte)(secondsToConvert % 60);
            newTime.m_MinutesOfPlaying = (int)(secondsToConvert / 60);
        }

        return newTime;
    }

    public void AddTime(GameTime timeToAdd){
        this.m_MinutesOfPlaying += timeToAdd.m_MinutesOfPlaying;
        this.m_SecondsOfPlaying += timeToAdd.m_SecondsOfPlaying;
        if(m_SecondsOfPlaying >= 60){
            this.m_SecondsOfPlaying %= 60;
            this.m_MinutesOfPlaying++;
        }
    }

    public void AddTime(long secondsToAdd){
        this.m_MinutesOfPlaying += secondsToAdd / 60;
        this.m_SecondsOfPlaying += secondsToAdd % 60;
        if(secondsToAdd >= 60){
            this.m_SecondsOfPlaying %= 60;
            this.m_MinutesOfPlaying++;
        }
    }
}