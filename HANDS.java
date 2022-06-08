public enum HANDS {

    HIGH_CARD, PAIR, TWO_PAIRS, THREE_OF_A_KIND, STRAIGHT, FLUSH, FULL_HOUSE, FOUR_OF_A_KIND, STRAIGHT_FLUSH;

    public static int value(HANDS handType) {

        int value = 0;

        switch (handType.toString()) {

            case "HIGH_CARD":
                value = 1;
                break;
            case "PAIR":
                value = 2;
                break;
            case "TWO_PAIRS":
                value = 3;
                break;
            case "THREE_OF_A_KIND":
                value = 4;
                break;
            case "STRAIGHT":
                value = 5;
                break;
            case "FLUSH":
                value = 6;
                break;
            case "FULL_HOUSE":
                value = 7;
                break;
            case "FOUR_OF_A_KIND":
                value = 8;
                break;
            case "STRAIGHT_FLUSH":
                value = 9;
                break;
        }

        return value;
    }

}
