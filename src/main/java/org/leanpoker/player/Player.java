package org.leanpoker.player;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.leanpoker.player.utils.CardCollectionBuilder;

import java.util.List;

public class Player {

    private static final Logger LOGGER = LogManager.getLogger(Player.class);

    static final String VERSION = "Stressed Java player";

    public static int betRequest(JsonElement request) {
        JsonObject gameState = request.getAsJsonObject();
        final Gson gson = new Gson();

        String round = gameState.get("round").getAsString();
        Card[] community_cards = gson.fromJson(gameState.get("community_cards"), Card[].class);
        try {

            int playerIndex = gameState.get("in_action").getAsInt();
            JsonArray players = gameState.get("players").getAsJsonArray();
            int smallBlind = gameState.get("small_blind").getAsInt();


            JsonObject ourPlayer = players.get(playerIndex).getAsJsonObject();

            int current_buy_in = gameState.get("current_buy_in").getAsInt();
            int minimum_raise = gameState.get("minimum_raise").getAsInt();
            int stack = ourPlayer.get("stack").getAsInt();
            int playerBet = ourPlayer.get("bet").getAsInt();

            Card[] hole_cards = gson.fromJson(ourPlayer.get("hole_cards"), Card[].class);

            List<Card> cardsInPlay = CardCollectionBuilder.buildCards(gameState);
            LOGGER.info("Round:{}",round);
            LOGGER.error("CardsInPlay {}", cardsInPlay.size());

            if (cardsInPlay.size() == 2) {
                return new PreFlopStrategy(current_buy_in, minimum_raise, playerBet, hole_cards, cardsInPlay).executePlay();
            } else {
                return new PostFlopStrategy(current_buy_in, minimum_raise, stack, playerBet, hole_cards, community_cards, cardsInPlay, smallBlind).executePlay();
            }

        } catch (Exception e) {
            LOGGER.error("Round:{}", round);
            LOGGER.error("community card lengths {}", community_cards.length);
            LOGGER.error("Exception occurred", e);
            return 0;
        }

    }

    public static void showdown(JsonElement game) {

    }
}
