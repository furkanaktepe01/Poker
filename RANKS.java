public enum RANKS {
    
    TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE;

    public static int value(RANKS rank) {

        int value = 0;

        switch (rank.toString()) {

            case "TWO":
                value = 2;
                break;
            case "THREE":
                value = 3;
                break;
            case "FOUR":
                value = 4;
                break;
            case "FIVE":
                value = 5;
                break;
            case "SIX":
                value = 6;
                break;
            case "SEVEN":
                value = 7;
                break;
            case "EIGHT":
                value = 8;
                break;
            case "NINE":
                value = 9;
                break;
            case "TEN":
                value = 10;
                break;
            case "JACK":
                value = 11;
                break;
            case "QUEEN":
                value = 12;
                break;
            case "KING":
                value = 13;
                break;
            case "ACE":
                value = 14;
                break;
        }

        return value;
    }

}
