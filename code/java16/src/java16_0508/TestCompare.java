package java16_0508;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Card implements Comparable<Card> {
    public String rank;
    public String suit;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    @Override
    public boolean equals(Object obj) {
        // 1. 看看 this 和 obj 身份是否相同.
        if (this == obj) {
            return true;
        }
        // 2. 如果 obj 为 null, 一定不相等
        if (obj == null) {
            return false;
        }
        // 3. obj 类型能否强转成 Card
        if (!(obj instanceof Card)) {
            return false;
        }
        // 4. 类型转换, 把参数转成 Card 类型
        Card other = (Card) obj;
        // 5. 真正进行按照指定规则比较.
        return this.rank.equals(other.rank);
    }

    @Override
    public int compareTo(Card o) {
        // this, o
        // this < o, 返回 < 0
        // this > o, 返回 > 0
        // this 和 o 相等, 返回 0
        // 定义比较规则: 扑克牌的点数来进行比较
        int rank1 = this.convertRank();
        int rank2 = o.convertRank();
        // 升序排序
        // return rank1 - rank2;
        // 降序排序
        return rank2 - rank1;
    }

    private int convertRank() {
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

public class TestCompare {
    public static void main(String[] args) {
        Card card1 = new Card("A", "♥");
        Card card2 = new Card("10", "♠");
        Card card3 = card1;

        // == 始终比较的是两个对象的身份. 比较的是两个引用中保存的地址的值.
//        System.out.println(card1 == card2);
//        System.out.println(card1 == card3);
        System.out.println(card1.compareTo(card2));

        List<Card> cards = new ArrayList<>();
        cards.add(new Card("A", "红桃"));
        cards.add(new Card("J", "红桃"));
        cards.add(new Card("K", "红桃"));
        cards.add(new Card("10", "红桃"));
        Collections.sort(cards);
        System.out.println(cards);
    }
}
