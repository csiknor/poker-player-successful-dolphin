package org.leanpoker.player;

import java.io.IOException;
import java.util.List;

public class PostFlopStrategy {
    private int current_buy_in;
    private int minimum_raise;
    private int playerStack;
    private int playerBet;

    private Card[] playerCards;
    private List<Card> cardsInPlay;
    private float player_aggression_level = 0.4f;
    private int small_blind;

    public PostFlopStrategy(int current_buy_in, int minimum_raise, int playerStack, int playerBet, Card[] playerCards, Card[] communityCards, List<Card> cardsInPlay, int small_blind) {
        this.current_buy_in = current_buy_in;
        this.minimum_raise = minimum_raise;
        this.playerStack = playerStack;
        this.playerBet = playerBet;
        this.playerCards = playerCards;
        this.cardsInPlay = cardsInPlay;
        this.small_blind = small_blind;
    }

    public int executePlay() throws IOException {

        if (havePair() || hasHighCard(playerCards[0]) || hasHighCard(playerCards[1])) {
            return minimum_raise + raiseAmount();
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

    private int raiseAmount() throws IOException {
        RankingResponse rankingResponse = new RankingAPIClient().executeRequest(cardsInPlay);

        int hand_rank = rankingResponse.getRank();
        int hand_value = rankingResponse.getValue();
        int hand_second_value = rankingResponse.getSecond_value();
        return Math.round(small_blind * hand_rank * hand_rank * (1 + hand_value/14) * (1 + hand_second_value/28) * player_aggression_level);
    }
}
