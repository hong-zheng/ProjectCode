package java16_0510;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Card {
    public String rank;
    public String suit;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public int convertRank() {
        // 把 String 类型的 rank 转成 int 值.
        // 2 - 10 => 2 - 10
        // J => 11
        // Q => 12
        // K => 13
        // A => 14
        if ("A".equals(rank)) {
            return 14;
        }
        if ("K".equals(rank)) {
            return 13;
        }
        if ("Q".equals(rank)) {
            return 12;
        }
        if ("J".equals(rank)) {
            return 11;
        }
        return Integer.parseInt(rank);
    }

    @Override
    public String toString() {
        return "Card{" +
                "rank='" + rank + '\'' +
                ", suit='" + suit + '\'' +
                '}';
    }
}

class CardComparator implements Comparator<Card> {
    @Override
    public int compare(Card o1, Card o2) {
        int rank1 = o1.convertRank();
        int rank2 = o2.convertRank();
        return rank1 - rank2;
    }
}

public class TestCompare {
    public static void main(String[] args) {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card("A", "红桃"));
        cards.add(new Card("J", "红桃"));
        cards.add(new Card("K", "红桃"));
        cards.add(new Card("10", "红桃"));
        Collections.sort(cards, new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                return 0;
            }
        });
        System.out.println(cards);
    }
}
