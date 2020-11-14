package com.example.jokeapp;

public class Joke {
    private int ID;
    private String Type;
    private String Setup;
    private String Punchline;

    public Joke(int ID, String type, String setup, String punchline) {
        this.ID = ID;
        Type = type;
        Setup = setup;
        Punchline = punchline;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getSetup() {
        return Setup;
    }

    public void setSetup(String setup) {
        Setup = setup;
    }

    public String getPunchline() {
        return Punchline;
    }

    public void setPunchline(String punchline) {
        Punchline = punchline;
    }
}

