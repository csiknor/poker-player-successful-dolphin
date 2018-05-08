package org.leanpoker.player;

public class PlayerStrategy {

    private int current_buy_in;
    private int minimum_raise;
    private int playerStack;
    private int playerBet;
    private Card[] playerCards;

    public PlayerStrategy(int current_buy_in, int minimum_raise, int playerStack, int playerBet, Card[] playerCards) {
        this.current_buy_in = current_buy_in;
        this.minimum_raise = minimum_raise;
        this.playerStack = playerStack;
        this.playerBet = playerBet;
        this.playerCards = playerCards;
    }

    public int executePlay() {

        if (havePair() || hasHighCard(playerCards[0]) || hasHighCard(playerCards[1])) {
            return minimum_raise;
        }

        return 0;
    }

    private boolean havePair() {
        if (playerCards[0].getRank().equals(playerCards[1].getRank()))
            return true;
        else
          return false;

    }

    private boolean hasHighCard(Card card) {

        return card.getRank().matches("[A-Z]");
    }
}
