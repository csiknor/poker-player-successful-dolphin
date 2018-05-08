package org.leanpoker.player;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.leanpoker.player.utils.CardCollectionBuilder;

import java.util.List;

public class Player {

    static final String VERSION = "Dumb Java player";

    public static int betRequest(JsonElement request) {

        try {
            final Gson gson = new Gson();

            JsonObject gameState = request.getAsJsonObject();
            int playerIndex = gameState.get("in_action").getAsInt();
            JsonArray players = gameState.get("players").getAsJsonArray();
            int smallBlind = gameState.get("small_blind").getAsInt();
            JsonObject ourPlayer = players.get(playerIndex).getAsJsonObject();

            int current_buy_in = gameState.get("current_buy_in").getAsInt();
            int minimum_raise = gameState.get("minimum_raise").getAsInt();
            int stack = ourPlayer.get("stack").getAsInt();

            Card[] hole_cards = gson.fromJson(ourPlayer.get("hole_cards"), Card[].class);

            List<Card> cardsInPlay = CardCollectionBuilder.buildCards(gameState);

            return new PlayerStrategy(current_buy_in, minimum_raise, stack, 0, hole_cards, new Card[2], cardsInPlay, smallBlind).executePlay();
        } catch (Exception e) {
            return 0;
        }

    }

    public static void showdown(JsonElement game) {

    }
}
