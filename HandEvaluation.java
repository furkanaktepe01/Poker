import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class HandEvaluation {

    public static Hand bestHand(ArrayList<Card> cards) {

        cards = Card.sortCards(cards);

        return checkStraightFlush(cards).orElse(
                checkFourOfAKind(cards).orElse(
                        checkFullHouse(cards).orElse(
                                checkFlush(cards).orElse(
                                        checkStraight(cards).orElse(
                                                checkThreeOfAKind(cards).orElse(
                                                        checkTwoPairs(cards).orElse(
                                                                checkPair(cards).orElse(
                                                                        checkHighCard(cards).orElse(
                                                                                null
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    public static Optional<Hand> checkStraightFlush(ArrayList<Card> cards) {

        Optional<Hand> optionalHand = Optional.empty();

        Optional<Hand> flushHand = checkFlush(cards);

        if (flushHand.isPresent()) {

            ArrayList<Card> cardsWithSuiteOfFlush = cards.stream()
                    .filter(card -> card.suite.equals(flushHand.get().mains.get(0).suite)).collect(Collectors.toCollection(ArrayList::new));

            Card highCard = includesStraight(cardsWithSuiteOfFlush);

            if (highCard.value() != 1) {

                Hand straightFlushHand = new Hand(HANDS.STRAIGHT_FLUSH);
                straightFlushHand.mains.add(highCard);

                optionalHand = Optional.of(straightFlushHand);
            }
        }

        return optionalHand;
    }

    public static Optional<Hand> checkFourOfAKind(ArrayList<Card> cards) {

        return xOfAKind(cards, HANDS.FOUR_OF_A_KIND, 4);
    }

    public static Optional<Hand> checkFullHouse(ArrayList<Card> cards) {

        Optional<Hand> optionalHand = Optional.empty();

        Optional<Hand> threeOfAKindHand = checkThreeOfAKind(cards);

        if (threeOfAKindHand.isPresent()) {

            Card threeOfAKindMain = threeOfAKindHand.get().mains.get(0);

            Hand fullHouseHand = new Hand(HANDS.FULL_HOUSE);
            fullHouseHand.mains.add(threeOfAKindMain);

            ArrayList<Card> otherCards = cards.stream().filter(card -> card.rank != threeOfAKindMain.rank).collect(Collectors.toCollection(ArrayList::new));

            Optional<Hand> pairHand = checkPair(otherCards);

            if (pairHand.isPresent()) {

                fullHouseHand.mains.add(pairHand.get().mains.get(0));

                optionalHand = Optional.of(fullHouseHand);
            }
        }

        return optionalHand;
    }

    public static Optional<Hand> checkFlush(ArrayList<Card> cards) {

        Optional<Hand> optionalHand = Optional.empty();

        for (int i = 0; i < cards.size(); i++) {

            Card card = cards.get(i);

            if (cards.stream().filter(c -> c.suite.equals(card.suite)).count() >= 5) {

                Hand flushHand = new Hand(HANDS.FLUSH);
                flushHand.mains.add(card);
                cards.stream().filter(c -> !c.equals(card) && c.suite.equals(card.suite)).limit(4).forEach(c -> flushHand.kickers.add(c));

                optionalHand = Optional.of(flushHand);
            }

            if (optionalHand.isPresent()) break;
        }

        return optionalHand;
    }

    public static Optional<Hand> checkStraight(ArrayList<Card> cards) {

        Optional<Hand> optionalHand = Optional.empty();

        Card highCard = includesStraight(cards);

        if (highCard.value() != 1) {

            Hand straightHand = new Hand(HANDS.STRAIGHT);
            straightHand.mains.add(highCard);

            optionalHand = Optional.of(straightHand);
        }

        return optionalHand;
    }

    public static Optional<Hand> checkThreeOfAKind(ArrayList<Card> cards) {

        return xOfAKind(cards, HANDS.THREE_OF_A_KIND, 3);
    }

    public static Optional<Hand> checkTwoPairs(ArrayList<Card> cards) {

        Optional<Hand> optionalHand = Optional.empty();

        ArrayList<Card> firstPair = checkPair(cards).orElse(new Hand(HANDS.TWO_PAIRS)).mains;

        if (!firstPair.isEmpty()) {

            ArrayList<Card> otherCards = cards.stream().filter(card -> card.rank != firstPair.get(0).rank).collect(Collectors.toCollection(ArrayList::new));

            ArrayList<Card> secondPair = checkPair(otherCards).orElse(new Hand(HANDS.TWO_PAIRS)).mains;

            if (!secondPair.isEmpty()) {

                Hand twoPairsHand = new Hand(HANDS.TWO_PAIRS);
                twoPairsHand.mains.add(firstPair.get(0));
                twoPairsHand.mains.add(secondPair.get(0));
                otherCards.stream().filter(card -> !card.rank.equals(firstPair.get(0).rank) && !card.rank.equals(secondPair.get(0).rank))
                        .limit(1).forEach(kicker -> twoPairsHand.kickers.add(kicker));

                optionalHand = Optional.of(twoPairsHand);
            }
        }

        return optionalHand;
    }

    public static Optional<Hand> checkPair(ArrayList<Card> cards) {

        return xOfAKind(cards, HANDS.PAIR, 2);
    }

    public static Optional<Hand> checkHighCard(ArrayList<Card> cards) {

        return xOfAKind(cards, HANDS.HIGH_CARD, 1);
    }

    public static Optional<Hand> xOfAKind(ArrayList<Card> cards, HANDS handType, int x) {

        Optional<Hand> optionalHand = Optional.empty();

        for (int i = 0; i < cards.size(); i++) {

            Card card = cards.get(i);

            if (cards.stream().filter(c -> c.rank.equals(card.rank)).count() == x) {

                Hand xOfAKindHand = new Hand(handType);
                xOfAKindHand.mains.add(card);
                cards.stream().filter(c -> c.rank != card.rank).limit(5 - x).forEach(c -> xOfAKindHand.kickers.add(c));

                optionalHand = Optional.of(xOfAKindHand);
            }

            if (optionalHand.isPresent()) break;
        }

        return optionalHand;
    }

    public static  Card includesStraight(ArrayList<Card> cards) {

        Card highCard = _includesStraight(cards);

        if (highCard.value() == 1) {

            highCard = _includesTheWheel(cards);
        }

        return  highCard;
    }

    public static Card _includesStraight(ArrayList<Card> cards) {

        Card highCard = new Card(null, null);

        int countOfPossibleStraights = cards.size() - 4;

        for (int i = 0; i < countOfPossibleStraights; i++) {

            Card headCard = cards.get(i);

            int counter = 0;

            for (int j = 0; j < 4; j++) {

                Card card = cards.get(i + j);
                Card nextCard = cards.get(i + j + 1);

                if (card.value() - nextCard.value() == 1) counter++;
            }

            if (counter == 4) {

                highCard = headCard;

                break;
            }
        }

        return highCard;
    }

    public static Card _includesTheWheel(ArrayList<Card> cards) {

        Card lowAce = new Card(null, null);

        ArrayList<Card> acesConvertedToOne = cards.stream().map(card -> card.rank.equals(RANKS.ACE) ? lowAce : card)
                .collect(Collectors.toCollection(ArrayList::new));

        return _includesStraight(Card.sortCards(acesConvertedToOne));
    }

}
