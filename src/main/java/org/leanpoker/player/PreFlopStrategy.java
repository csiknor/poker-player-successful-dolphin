package org.leanpoker.player;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class PreFlopStrategy {

    private static final Logger LOGGER = LogManager.getLogger(PreFlopStrategy.class);
    private int current_buy_in;
    private int minimum_raise;
    private int playerBet;
    private Card[] playerCards;
    private List<Card> cardsInPlay;

    public PreFlopStrategy(int current_buy_in, int minimum_raise, int playerBet, Card[] playerCards, List<Card> cardsInPlay) {
        this.current_buy_in = current_buy_in;
        this.minimum_raise = minimum_raise;
        this.playerBet = playerBet;
        this.playerCards = playerCards;
        this.cardsInPlay = cardsInPlay;
    }

    public int executePlay() throws IOException {

        LOGGER.info("-----------------------------------------------");
        LOGGER.info("-----------------------------------------------");
        LOGGER.info("-----------------------------------------------");
        LOGGER.info("-----------------------------------------------");
        LOGGER.info("Executing play");
        LOGGER.info("Player cards: {}, {}", playerCards[0], playerCards[1]);


        if (hasHighPair("A") || hasHighPair("K") || hasKingOrAce()) {
            LOGGER.info("Returning higher bet");
            return 20 * (current_buy_in - playerBet + minimum_raise);
        } else if (havePair() || hasHighCard(playerCards[0]) || hasHighCard(playerCards[1])) {
            LOGGER.info("Returning minimum raise");
            return 10 * (current_buy_in - playerBet + minimum_raise);
        }


        LOGGER.info("-----------------------------------------------");
        LOGGER.info("-----------------------------------------------");
        LOGGER.info("-----------------------------------------------");
        LOGGER.info("-----------------------------------------------");
        return 0;
    }

    private boolean hasKingOrAce() {

        return (    (playerCards[0].getRank() == "K" && playerCards[0].getRank() == "A")
                ||  (playerCards[0].getRank() == "K" && playerCards[0].getRank() == "A"));
    }

    private boolean hasHighPair(String rank) {
        return playerCards[0].getRank().equals(rank) && playerCards[1].getRank().equals(rank);
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
