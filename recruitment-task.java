import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        int[] numbers = {10, -5, 0, 20, -10, 0, 15};
        LinkedList<Integer> result = new LinkedList<>();

        for (int num : numbers) {
            if (num < 0) {
                // If the number is below zero, add it to the result linked list
                result.add(num);
            } else if (num > 0 && num < result.size()) {
                // If the number is above zero, remove the element at the position specified by num
                result.remove(num);
            }
        }

        System.out.println("Result Linked List: " + result);
    }
}
