import java.util.ArrayList;

public class Hand {

    public HANDS type;
    public ArrayList<Card> mains;
    public ArrayList<Card> kickers;

    public Hand(HANDS type) {
        this.type = type;
        this.mains = new ArrayList<>();
        this.kickers = new ArrayList<>();
    }

    public int typeValue() {

        return HANDS.value(type);
    }

}
