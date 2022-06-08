import java.util.ArrayList;
import java.util.Random;

public class Deck {

    public ArrayList<Card> deck;

    public ArrayList<Card> cardsOffPlay;

    public Deck() {

        deck = new ArrayList<>();

        for (SUITES suite: SUITES.class.getEnumConstants()) {
            for (RANKS rank: RANKS.class.getEnumConstants()) {
                deck.add(new Card(suite, rank));
            }
        }

        cardsOffPlay = new ArrayList<>(deck);
    }

    public Card pick() {

        int random = new Random().nextInt(cardsOffPlay.size());

        Card card = cardsOffPlay.get(random);

        cardsOffPlay.remove(card);

        return card;
    }

}
