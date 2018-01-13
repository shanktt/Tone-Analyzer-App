package com.example.ludwigvonpoopen.toneanalyzer;

import android.util.Log;

import java.util.ArrayList;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneOptions;
/**
 * Created by LudwigVonPoopen on 1/6/2018.
 */

public class ToneAnalyzerExample {
    public static void main(String[] args) {
        final String VERSION_DATE = "2016-05-19";
        ToneAnalyzer service = new ToneAnalyzer(VERSION_DATE);
        service.setUsernameAndPassword("e76bc0e2-29bd-4ee1-a5c8-6d11b1f55d2d", "cL6qPy040wj3");

        String text = "I know the times are difficult! Our sales have been "
                + "disappointing for the past three quarters for our data analytics "
                + "product suite. We have a competitive data analytics product "
                + "suite in the industry. But we need to do our job selling it! "
                + "We need to acknowledge and fix our sales challenges. "
                + "We can’t blame the economy for our lack of execution! " + "We are missing critical sales opportunities. "
                + "Our product is in no way inferior to the competitor products. "
                + "Our clients are hungry for analytical tools to improve their "
                + "business outcomes. Economy has nothing to do with it.";

        String text2 = "I love you";

        // Call the service and get the tone
        ToneOptions tonOptions = new ToneOptions.Builder().text(text2).build();
        ToneAnalysis tone = service.tone(tonOptions).execute();
        //System.out.println(tone);
        ArrayList<Tone> tones = getTones(tone.toString());

        //System.out.println(getHighestTone(tones).getTone());
        Log.d("myTag", getHighestTone(tones).getTone());
    }

    public static ArrayList<Tone> getTones(String tone)
    {
        ArrayList<Tone> tones = new ArrayList<Tone>();

        ArrayList<Integer> index = new ArrayList<Integer>();
        ArrayList<Integer> index2 = new ArrayList<Integer>();
        ArrayList<Integer> index3 = new ArrayList<Integer>();
        ArrayList<Integer> index4 = new ArrayList<Integer>();

        int lookFor = tone.indexOf("score");
        while(lookFor>= 0)
        {
            index.add(lookFor);
            lookFor = tone.indexOf("score", lookFor+1);
        }

        lookFor = tone.indexOf(",", index.get(0));
        for(int i = 1; i < index.size(); i++)
        {
            index2.add(lookFor);
            lookFor = tone.indexOf(",", index.get(i)+1);
        }

        lookFor = tone.indexOf(",", index.get(index.size()-1)+1);
        index2.add(lookFor);

//-----------------------------------------------------------------------------------------------------------

        lookFor = tone.indexOf("tone_id");
        while(lookFor >= 0)
        {
            index3.add(lookFor);
            lookFor = tone.indexOf("tone_id", lookFor+1);
        }

        lookFor = tone.indexOf(",", index3.get(0));
        for(int i = 1; i < index3.size(); i++)
        {
            index4.add(lookFor);
            lookFor = tone.indexOf(",", index3.get(i)+1);
        }

        lookFor = tone.indexOf(",", index3.get(index3.size()-1)+1);
        index4.add(lookFor);



        for(int i = 0; i < index.size(); i++)
        {
            String score = tone.substring(index.get(i), index2.get(i));
            score = score.substring(score.indexOf(":")+1);
            double scoreNum = Double.parseDouble(score);

            String toneStr = tone.substring(index3.get(i), index4.get(i));
            toneStr = toneStr.replace("\"", "");
            toneStr = toneStr.substring(toneStr.indexOf(":")+1);
            toneStr = toneStr.trim();

            tones.add(new Tone(scoreNum, toneStr));
//		  System.out.println(tone.substring(index.get(i), index2.get(i)) + " " + tone.substring(index3.get(i), index4.get(i)));
        }

        return tones;
    }

    public static Tone getHighestTone(ArrayList<Tone> tones)
    {
        double maxScore = 0;
        int indexMax = 0;
        for(int i = 0; i < tones.size(); i++)
        {
            if(tones.get(i).getScore() > maxScore)
            {
                maxScore = tones.get(i).getScore();
                indexMax = i;
            }
        }
        return tones.get(indexMax);
    }
}
