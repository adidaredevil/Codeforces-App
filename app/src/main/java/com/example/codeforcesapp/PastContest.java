package com.example.codeforcesapp;

public class PastContest {
    String name;
    String rank;
    String solved;
    String ratingchange;
    String newrating;

    public PastContest(String name, String rank, String solved, String ratingchange, String newrating) {
        this.name = name;
        this.rank = rank;
        this.solved = solved;
        this.ratingchange = ratingchange;
        this.newrating = newrating;
    }

    public String getName() {
        return name;
    }


    public String getRank() {
        return rank;
    }


    public String getSolved() {
        return solved;
    }


    public String getRatingchange() {
        return ratingchange;
    }


    public String getNewrating() {
        return newrating;
    }


    @Override
    public String toString() {
        return "PastContest{" +
                "name='" + name + '\'' +
                ", rank='" + rank + '\'' +
                ", solved='" + solved + '\'' +
                ", ratingchange='" + ratingchange + '\'' +
                ", newrating='" + newrating + '\'' +
                '}';
    }
}
