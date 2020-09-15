package java16_0414;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class StackQueueInterview {
    // 判断字符串是否合法(括号是否匹配)
    public boolean isValid(String s) {
        if (s.isEmpty()) {
            return true;
        }

        Map<Character, Character> map = new HashMap<>();
        // 给定 key 能够找到 value
//        map.put(')', '(');
//        map.put(']', '[');
//        map.put('}', '{');
        map.put('(', ')');
        map.put('[', ']');
        map.put('{', '}');

        // 1. 创建一个栈
        Stack<Character> stack = new Stack<>();
        // 2. 循环遍历字符串, 取出字符看是左括号还是右括号
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // a) 如果是左括号, 直接入栈
            if (map.get(c) != null) {
                stack.push(c);
                continue;
            }
            // b) 如果是右括号, 取栈顶元素, 和当前括号看是否匹配
            if (stack.empty()) {
                return false;
            }
            Character top = stack.pop();
            // 判断括号是否匹配
//            if (map.get(c).equals(top)) {
//                continue;
//            }
            if (map.get(top).equals(c)) {
                continue;
            }
            // 前面三个合法情况都没有命中, 剩下的就只能是非法情况了
            return false;
        }
        // 3. 最后一步, 当字符串遍历结束后, 看 栈 是否为空
        if (stack.empty()) {
            return true;
        }
        return false;
    }
}
