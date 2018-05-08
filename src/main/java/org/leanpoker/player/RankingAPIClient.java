package org.leanpoker.player;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class RankingAPIClient {

    private static final String RANKING_API_URL = "http://rainman.leanpoker.org/rank?cards=";

    public RankingResponse executeRequest(List<Card> cards) throws IOException {
        Gson gson = new Gson();

        String cardsJson = gson.toJson(cards);
        String cardsPayload = URLEncoder.encode(cardsJson , "UTF-8");

        HttpURLConnection conn = (HttpURLConnection) new URL(RANKING_API_URL + cardsPayload).openConnection();
        conn.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));

        return gson.fromJson(in, RankingResponse.class);
        }


}
