import java.util.ArrayList;

public class HandComparison {

    public static int compareHands(Hand hand0, Hand hand1) {

            int comparison = 0;

            if (hand0.typeValue() != hand1.typeValue()) {

                comparison = hand0.typeValue() > hand1.typeValue() ? 1 : -1;

            } else {

                ArrayList<Card> mains0 = hand0.mains;
                ArrayList<Card> mains1 = hand1.mains;

                for (int i = 0; i < mains0.size(); i++) {

                    if (mains0.get(i).value() == mains1.get(i).value()) {
                        continue;
                    }

                    comparison = mains0.get(i).value() > mains1.get(i).value() ? 1 : -1;
                }

                if (comparison == 0) {

                    ArrayList<Card> kicker0 = hand0.kickers;
                    ArrayList<Card> kicker1 = hand1.kickers;

                    for (int i = 0; i < kicker0.size(); i++) {

                        if (kicker0.get(i).value() == kicker1.get(i).value()) {
                            continue;
                        }

                        comparison = kicker0.get(i).value() > kicker1.get(i).value() ? 1 : -1;
                    }
                }
            }

            return comparison;
        }

}

