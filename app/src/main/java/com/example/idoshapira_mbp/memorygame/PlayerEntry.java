package com.example.idoshapira_mbp.memorygame;


/**
 * Created by idoshapira-mbp on 25/05/2018.
 */

public class PlayerEntry {

    private String playerName;
    private Double score;
    private String diff;

    public PlayerEntry(String name,Double score,String diff){
        this.playerName = name;
        this.score = score;
        this.diff = diff;
    }

    public String getplayerName() {
        return playerName;
    }
    public void setplayerName(String name) {
        this.playerName = name;
    }

    public Double getScore() {
        return score;
    }
    public void setScore(Double score) {
        this.score = score;
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }




}
