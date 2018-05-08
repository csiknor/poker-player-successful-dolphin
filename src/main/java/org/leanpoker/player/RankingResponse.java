package org.leanpoker.player;

public class RankingResponse {

    private int rank;
    private int value;
    private int second_value;

    public RankingResponse(int rank, int value, int second_value) {
        this.rank = rank;
        this.value = value;
        this.second_value = second_value;
    }

    public int getRank() {
        return rank;
    }

    public int getValue() {
        return value;
    }

    public int getSecond_value() {
        return second_value;
    }

}
