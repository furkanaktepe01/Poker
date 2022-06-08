import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Card {

    public SUITES suite;
    public RANKS rank;

    public Card(SUITES suite, RANKS rank) {
        this.suite = suite;
        this.rank = rank;
    }

    public int value() {

        if (rank != null) {
            return RANKS.value(rank);
        } else {
            return 1;
        }
    }

    public String toString() {
        return rank.toString() + " of " + suite.toString();
    }

    public static ArrayList<Card> sortCards(ArrayList<Card> cards) {
        return cards.stream().sorted(Comparator.comparing(Card::value).reversed()).collect(Collectors.toCollection(ArrayList::new));
    }

}
