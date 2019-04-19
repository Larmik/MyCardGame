package com.mygdx.game;

public class CardSelectionUtil {

    public static CardPriority getPriority(Card card, int score, boolean isPlusDix, boolean isAs1) {
        switch (Card.getCardValue(card.getAsset())) {
            case SEPT:
                if (score < 48)
                    return CardPriority.PRIORITY_9;
                else
                    return CardPriority.PRIORITY_8;
            case HUIT:
                if (score < 42)
                    return CardPriority.PRIORITY_1;
                break;
            case NEUF:
                if (score < 42)
                    return CardPriority.PRIORITY_8;
                else if (score <= 46)
                    return CardPriority.PRIORITY_5;
                else {
                    switch (score) {
                        case 47:
                            return CardPriority.PRIORITY_4;
                        case 48:
                            return CardPriority.PRIORITY_3;
                        case 49:
                            return CardPriority.PRIORITY_2;
                        case 50:
                            return CardPriority.PRIORITY_1;
                    }
                }
                break;
            case DIX:
                if (isPlusDix) {
                    if (score < 42)
                        return CardPriority.PRIORITY_5;
                } else {
                    if (score < 42)
                        return CardPriority.PRIORITY_10;
                    else if (score <= 46)
                        return CardPriority.PRIORITY_7;
                    else {
                        switch (score) {
                            case 47:
                                return CardPriority.PRIORITY_6;
                            case 48:
                                return CardPriority.PRIORITY_5;
                            case 49:
                                return CardPriority.PRIORITY_4;
                            case 50:
                                return CardPriority.PRIORITY_3;
                        }
                    }
                }
                break;

            case VALET:
                if (score < 42)
                    return CardPriority.PRIORITY_4;
                else if (score <= 46)
                    return CardPriority.PRIORITY_3;
                else {
                    switch (score) {
                        case 47:
                            return CardPriority.PRIORITY_2;
                        case 48:
                            return CardPriority.PRIORITY_1;
                    }
                    break;
                }
            case DAME:
                if (score < 42)
                    return CardPriority.PRIORITY_3;
                else if (score <= 46)
                    return CardPriority.PRIORITY_2;
                else {
                    switch (score) {
                        case 47:
                            return CardPriority.PRIORITY_1;
                    }
                    break;
                }
            case ROI:
                if (score < 42)
                    return CardPriority.PRIORITY_2;
                else if (score <= 46)
                    return CardPriority.PRIORITY_1;
                break;
            case AS:
                if (isAs1) {
                    if (score < 42)
                        return CardPriority.PRIORITY_7;
                    else if (score <= 46)
                        return CardPriority.PRIORITY_6;
                    else {
                        switch (score) {
                            case 47:
                                return CardPriority.PRIORITY_3;
                            case 48:
                                return CardPriority.PRIORITY_2;
                            case 49:
                                return CardPriority.PRIORITY_1;
                        }
                    }
                } else {
                    if (score < 40)
                        return CardPriority.PRIORITY_2;
                }

                break;
        }


        return CardPriority.LOOSED;
    }

    public enum CardPriority {

        PRIORITY_1(1), PRIORITY_2(2), PRIORITY_3(3), PRIORITY_4(4), PRIORITY_5(5), PRIORITY_6(6), PRIORITY_7(7), PRIORITY_8(8), PRIORITY_9(9), PRIORITY_10(10), LOOSED(99);

        int value;

        CardPriority(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }


}
