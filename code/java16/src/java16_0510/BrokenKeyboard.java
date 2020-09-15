package java16_0510;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class BrokenKeyboard {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            // 1. 处理输入, 读取输入字符串
            String expected = scanner.next();
            String actual = scanner.next();
            // 2. 把两个字符串都转成 大写
            expected = expected.toUpperCase();
            actual = actual.toUpperCase();
            // 3. 把 actual 中的字符放到一个 set 中
            Set<Character> setActual = new HashSet<>();
            for (int i = 0; i < actual.length(); i++) {
                setActual.add(actual.charAt(i));
            }
            // 4. 遍历预期输出的字符串, 看这里的字符哪些没有在 setActual 中出现. 没出现的字符就是坏键
            Set<Character> brokenKey = new HashSet<>();
            for (int i = 0; i < expected.length(); i++) {
                char c = expected.charAt(i);
                if (setActual.contains(c)) {
                    // 存在就不是坏键
                    continue;
                }
                // 这是一个坏键, 输出之. 但是别忘了, 坏键不需要重复输出
                if (brokenKey.contains(c)) {
                    continue;
                }
                System.out.print(c);
                brokenKey.add(c);
            }
            System.out.println();
        }
    }
}
