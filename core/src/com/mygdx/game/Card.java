package com.mygdx.game;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum  Card {

    SEPT_TREFLE("assets/cards/7_of_clubs.png"),
    SEPT_PIQUE("assets/cards/7_of_spades.png"),
    SEPT_COEUR("assets/cards/7_of_hearts.png"),
    SEPT_CARREAU("assets/cards/7_of_diamonds.png"),

    HUIT_TREFLE("assets/cards/8_of_clubs.png"),
    HUIT_PIQUE("assets/cards/8_of_spades.png"),
    HUIT_COEUR("assets/cards/8_of_hearts.png"),
    HUIT_CARREAU("assets/cards/8_of_diamonds.png"),

    NEUF_TREFLE("assets/cards/9_of_clubs.png"),
    NEUF_PIQUE("assets/cards/9_of_spades.png"),
    NEUF_COEUR("assets/cards/9_of_hearts.png"),
    NEUF_CARREAU("assets/cards/9_of_diamonds.png"),

    DIX_TREFLE("assets/cards/10_of_clubs.png"),
    DIX_PIQUE("assets/cards/10_of_spades.png"),
    DIX_COEUR("assets/cards/10_of_hearts.png"),
    DIX_CARREAU("assets/cards/10_of_diamonds.png"),

    VALET_TREFLE("assets/cards/jack_of_clubs.png"),
    VALET_PIQUE("assets/cards/jack_of_spades.png"),
    VALET_COEUR("assets/cards/jack_of_hearts.png"),
    VALET_CARREAU("assets/cards/jack_of_diamonds.png"),

    DAME_TREFLE("assets/cards/queen_of_clubs.png"),
    DAME_PIQUE("assets/cards/queen_of_spades.png"),
    DAME_COEUR("assets/cards/queen_of_hearts.png"),
    DAME_CARREAU("assets/cards/queen_of_diamonds.png"),

    ROI_TREFLE("assets/cards/king_of_clubs.png"),
    ROI_PIQUE("assets/cards/king_of_spades.png"),
    ROI_COEUR("assets/cards/king_of_hearts.png"),
    ROI_CARREAU("assets/cards/king_of_diamonds.png"),

    AS_TREFLE("assets/cards/ace_of_clubs.png"),
    AS_PIQUE("assets/cards/ace_of_spades.png"),
    AS_COEUR("assets/cards/ace_of_hearts.png"),
    AS_CARREAU("assets/cards/ace_of_diamonds.png");

    String asset;
    static List<Card> deck = new ArrayList<Card>();


    Card(String asset) {
        this.asset = asset;
    }

    public String getAsset() {
        return asset;
    }

    public static List<Card> getDeck() {
        deck.clear();
        deck.addAll(Arrays.asList(Card.values()));
        Collections.shuffle(deck);
        return deck;
    }

    public static CardValue getCardValue(String asset) {
        if (asset.contains("7"))
            return CardValue.SEPT;
        if (asset.contains("8"))
            return CardValue.HUIT;
        if (asset.contains("9"))
            return CardValue.NEUF;
        if (asset.contains("10"))
            return CardValue.DIX;
        if (asset.contains("jack"))
            return CardValue.VALET;
        if (asset.contains("queen"))
            return CardValue.DAME;
        if (asset.contains("king"))
            return CardValue.ROI;
        if (asset.contains("ace"))
           return CardValue.AS;
        return CardValue.UNKNOW;
    }

    static boolean isTen(Card card) {
        return card.getAsset().contains("10");
    }

    static boolean isAce(Card card) {
        return card.getAsset().contains("ace");
    }





}
