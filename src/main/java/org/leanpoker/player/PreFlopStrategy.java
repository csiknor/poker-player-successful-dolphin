package org.leanpoker.player;

import java.io.IOException;
import java.util.List;

public class PreFlopStrategy {

    private int current_buy_in;
    private int minimum_raise;
    private int playerStack;
    private int playerBet;

    private Card[] playerCards;
    private List<Card> cardsInPlay;
    private float player_aggression_level = 0.4f;
    private int small_blind;

    public PreFlopStrategy(int current_buy_in, int minimum_raise, int playerStack, int playerBet, Card[] playerCards, Card[] communityCards, List<Card> cardsInPlay, int small_blind) {
        this.current_buy_in = current_buy_in;
        this.minimum_raise = minimum_raise;
        this.playerStack = playerStack;
        this.playerBet = playerBet;
        this.playerCards = playerCards;
        this.cardsInPlay = cardsInPlay;
        this.small_blind = small_blind;
        this.player_aggression_level = 0.4f;
    }

    public int executePlay() throws IOException {

        if (hasHighPair("A") || hasHighPair("K") || hasKingOrAce()) {
            return playerStack;
        } else if (havePair() || hasHighCard(playerCards[0]) || hasHighCard(playerCards[1])) {
            return minimum_raise;
        }

        return 0;
    }

    private boolean hasKingOrAce() {

        return (    (playerCards[0].getRank() == "K" && playerCards[0].getRank() == "A")
                ||  (playerCards[0].getRank() == "K" && playerCards[0].getRank() == "A"));
    }

    private boolean hasHighPair(String rank) {
        return playerCards[0].getRank().equals(rank) && playerCards[1].getRank().equals(rank);
    }

    private boolean hasHighCardsAndSuite() {
        return (    (playerCards[0].getRank() == "K" && playerCards[0].getRank() == "A" && playerCards[0].getSuit() == playerCards[1].getSuit())
                ||  (playerCards[0].getRank() == "K" && playerCards[0].getRank() == "A") && playerCards[0].getSuit() == playerCards[1].getSuit());
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
