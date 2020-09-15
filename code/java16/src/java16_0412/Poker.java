package java16_0412;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Poker {
    // 通过这个类来模拟创建一副牌, 洗牌, 发牌;
    // 不加 static 的内部类, 当我们创建 Card 对象的时候, 必须要依靠 Poker 的实例才能创建.
    // 加上 static 的内部类, 当我们创建 Card 对象的时候, 就不依赖 Poker 的实例. 就可以在 Poker 的静态方法中创建实例了
    static class Card {
        public String suit;
        public String rank;

        public Card(String suit, String rank) {
            this.suit = suit;
            this.rank = rank;
        }

        @Override
        public String toString() {
            // return "(" + suit + rank + ")";
            // String.format 静态方法, 可以像 C 语言的 printf 一样构造格式化字符串
            return String.format("(%s%s)", suit, rank);
        }
    }

    // 构建一副牌
    public static List<Card> buyPoker() {
        String[] suits = {"♥", "♠", "♣", "♦"};

        List<Card> poker = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 2; j <= 10; j++) {
                Card card = new Card(suits[i], String.valueOf(j));
                poker.add(card);
            }
            poker.add(new Card(suits[i], "J"));
            poker.add(new Card(suits[i], "Q"));
            poker.add(new Card(suits[i], "K"));
            poker.add(new Card(suits[i], "A"));
        }
        return poker;
    }

    public static void shuffle(List<Card> poker) {
        // 洗牌思路很简单. 也有很多不同的实现方式.
        // 从后往前遍历 List , 每次取到一个牌之后, 就和前面的一个随机位置的牌进行交换
        Random random = new Random();
        for (int i = poker.size() - 1; i > 0; i--) {
            // 产生一个 [0, i) 随机数
            int r = random.nextInt(i);
            swap(poker, i, r);
        }
    }

    private static void swap(List<Card> poker, int i, int j) {
        Card tmp = poker.get(i);
        poker.set(i, poker.get(j));
        poker.set(j, tmp);
    }

    public static void main(String[] args) {
        // 1. 先买一副牌
        List<Card> poker = buyPoker();
        // 2. 洗牌
        // shuffle(poker);
        Collections.shuffle(poker);
        System.out.println(poker);
        // 3. 发牌, 把牌发给 3 个玩家, 每人发五张(梭哈)
        //    通过嵌套 List 来表示.
        //    每个玩家的手牌是一个 List. 多个玩家的手牌再放到一个 List 中
        // players 中的元素个数就是玩家的总数. 有几个玩家就有几个元素. 每个玩家又是一个 List
        ArrayList<ArrayList<Card>> players = new ArrayList<ArrayList<Card>>();
        ArrayList<Card> player1 = new ArrayList<Card>();
        ArrayList<Card> player2 = new ArrayList<Card>();
        ArrayList<Card> player3 = new ArrayList<Card>();
        players.add(player1);
        players.add(player2);
        players.add(player3);

        // 此处咱们在模拟轮流发牌. 玩家 1 发一张, 玩家 2 发一张, 玩家 3 发一张....
        for (int cardIndex = 0; cardIndex < 5; cardIndex++) {
            for (int playerIndex = 0; playerIndex < 3; playerIndex++) {
                // 先获取到这个玩家
                List<Card> player = players.get(playerIndex);
                // 再把 poker 中的最前面的元素发给当前玩家
                Card topCard = poker.remove(0);
                player.add(topCard);
            }
        }

        // 4. 展示手牌, 比大小
        System.out.println("玩家1 的手牌: ");
        System.out.println(players.get(0));
        System.out.println("玩家2 的手牌: ");
        System.out.println(players.get(1));
        System.out.println("玩家3 的手牌: ");
        System.out.println(players.get(2));
        System.out.println("玩家发牌之后牌堆中的牌: ");
        System.out.println(poker);
    }
}
