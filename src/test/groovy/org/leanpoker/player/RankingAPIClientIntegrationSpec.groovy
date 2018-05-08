package org.leanpoker.player

import spock.lang.Specification

class RankingAPIClientIntegrationSpec extends Specification {

    def "Return correct response, given a payload of 5 cards"() {
        given: "A payload"
        def payload = [createCard("Q", "Diamonds"),
                       createCard("Q", "Spades"),
                       createCard("A", "Hearts"),
                       createCard("A", "Diamonds"),
                       createCard("K", "Spades")]


        when:
        def response = new RankingAPIClient().executeRequest(payload)

        then:
        response != null
    }


    def createCard(String rank, String suit) {
        new Card(rank, suit)
    }
}
