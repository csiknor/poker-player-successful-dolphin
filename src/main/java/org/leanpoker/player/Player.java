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

    static final String VERSION = "Motivated Java player";

    public static int betRequest(JsonElement request) {

        try {
            final Gson gson = new Gson();

            JsonObject gameState = request.getAsJsonObject();
            int playerIndex = gameState.get("in_action").getAsInt();
            JsonArray players = gameState.get("players").getAsJsonArray();
            int smallBlind = gameState.get("small_blind").getAsInt();
            int round = gameState.get("round").getAsInt();

            JsonObject ourPlayer = players.get(playerIndex).getAsJsonObject();

            int current_buy_in = gameState.get("current_buy_in").getAsInt();
            int minimum_raise = gameState.get("minimum_raise").getAsInt();
            int stack = ourPlayer.get("stack").getAsInt();
            int playerBet = ourPlayer.get("bet").getAsInt();

            Card[] hole_cards = gson.fromJson(ourPlayer.get("hole_cards"), Card[].class);

            List<Card> cardsInPlay = CardCollectionBuilder.buildCards(gameState);
            LOGGER.info("Round:{}",round);

            if (round == 0) {
                return new PreFlopStrategy(current_buy_in, minimum_raise, stack, playerBet, hole_cards, new Card[2], cardsInPlay, smallBlind).executePlay();
            } else {
                return new PostFlopStrategy(current_buy_in, minimum_raise, stack, playerBet, hole_cards, new Card[2], cardsInPlay, smallBlind).executePlay();
            }

        } catch (Exception e) {
            LOGGER.error("Exception occurred", e);
            return 0;
        }

    }

    public static void showdown(JsonElement game) {

    }
}
