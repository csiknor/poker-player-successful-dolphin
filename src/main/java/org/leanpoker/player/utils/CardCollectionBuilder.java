package org.leanpoker.player.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.leanpoker.player.Card;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CardCollectionBuilder {

    public static List<Card> buildCards(JsonElement gameState) {
        List<Card> cards = new LinkedList<>();
        Gson gson = new Gson();
        JsonObject gameStateJson = gameState.getAsJsonObject();
        int in_action = gameStateJson.get("in_action").getAsInt();
        JsonArray players = gameStateJson.get("players").getAsJsonArray();
        JsonArray community_cards = gameStateJson.get("community_cards").getAsJsonArray();
        community_cards.forEach(json -> {
            Card card = gson.fromJson(json, Card.class);
            cards.add(card);
        });

        JsonArray playerCards = players.get(in_action).getAsJsonObject().get("hole_cards").getAsJsonArray();
        playerCards.forEach(json -> {
            Card card = gson.fromJson(json, Card.class);
            cards.add(card);
        });


        return cards;
    }
}
