package com.example.codeforcesapp;

public class Contest {
    String contestName;
    String contestStart;
    String contestLength;

    public Contest(String contestName, String contestStart, String contestLength) {
        this.contestName = contestName;
        this.contestStart = contestStart;
        this.contestLength = contestLength;
    }

    public String getContestName() {
        return contestName;
    }

    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    public String getContestStart() {
        return contestStart;
    }

    public void setContestStart(String contestStart) {
        this.contestStart = contestStart;
    }

    public String getContestLength() {
        return contestLength;
    }

    public void setContestLength(String contestLength) {
        this.contestLength = contestLength;
    }

    @Override
    public String toString() {
        return "Contest{" +
                "contestName='" + contestName + '\'' +
                ", contestStart=" + contestStart +
                ", contestLength=" + contestLength +
                '}';
    }
}
