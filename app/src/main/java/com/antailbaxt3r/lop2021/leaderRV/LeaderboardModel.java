package com.antailbaxt3r.lop2021.leaderRV;

public class LeaderboardModel {
    private String name, rank, points;

    public LeaderboardModel() {
    }

    public LeaderboardModel(String name, String rank, String points) {
        this.name = name;
        this.rank = rank;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}
