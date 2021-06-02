package freelancer;

import java.util.List;
import java.util.Scanner;

public record Prompt<T>(String desc, T value) {

    public static <T> T ask(Scanner in, String question, List<Prompt<T>> qs) {
        while (true) {
            System.out.println(question);
            for (int i = 0; i < qs.size(); ++i) {
                System.out.println("  " + (i+1) + ". " + qs.get(i).desc);
            }

            System.out.print("> ");
            String userLine = in.nextLine();
            int userNum;
            try {
                userNum = Integer.parseInt(userLine);
            } catch (NumberFormatException e) {
                askErr(in, qs.size());
                continue;
            }

            if (userNum < 1 || userNum > qs.size()) {
                askErr(in, qs.size());
                continue;
            }

            return qs.get(userNum-1).value;
        }
    }

    private static void askErr(Scanner in, int n) {
        System.out.print("You must enter a number from 1-" + n + ".");
        in.nextLine();
    }
}
