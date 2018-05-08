package org.leanpoker.player.utils

import com.google.gson.Gson
import com.google.gson.JsonElement
import spock.lang.Specification

class CardCollectionBuilderUnitSpec extends Specification {

    def "Given a game state, return a collection of all cards in play"() {
        given:
        def gameState = """
        {
            "in_action": "1",
            "players": [
                {
                    "hole_cards": [
                        {"rank": "J", "suit": "Spades"},
                        {"rank": "K", "suit": "Hearts"}
                    ]
                },
                {
                    "hole_cards": [
                        {"rank": "A", "suit": "Spades"},
                        {"rank": "10", "suit": "Spades"}
                    ]
                }
            ],
            "community_cards": [{"rank": "Q", "suit": "Diamonds"},
                {"rank": "J", "suit": "Spades"},
                {"rank": "5", "suit": "Hearts"}
            ]
        }
        """
        def gameStateJson =  new Gson().fromJson(gameState, JsonElement.class)

        when:
        def result = CardCollectionBuilder.buildCards(gameStateJson)

        then:
        result.size() == 5
    }
}
