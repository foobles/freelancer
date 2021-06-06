package freelancer;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public record Prompt<T>(String desc, T value) {

    public static <T> T ask(Scanner in, String question, List<Prompt<T>> qs) {
        while (true) {
            System.out.println(question);
            printPrompts(qs);
            System.out.print("> ");
            int userIdx = getUserIdx(qs.size(), in.nextLine().trim());

            if (userIdx < 0) {
                askErr(in, qs.size());
                continue;
            }

            return qs.get(userIdx).value;
        }
    }


    public static <T> Optional<T> askOptional(Scanner in, String question, List<Prompt<T>> qs) {
        while (true) {
            System.out.println(question);
            printPrompts(qs);
            System.out.println("  q. quit");
            System.out.print("> ");
            String userLine = in.nextLine().trim();

            if (userLine.equals("q") || userLine.equals("quit")) {
                return Optional.empty();
            }

            int userIdx = getUserIdx(qs.size(), userLine);

            if (userIdx < 0) {
                askErr(in, qs.size());
                continue;
            }

            return Optional.of(qs.get(userIdx).value);
        }
    }

    private static int getUserIdx(int size, String userLine) {
        int userNum;
        try {
            userNum = Integer.parseInt(userLine);
        } catch (NumberFormatException e) {
            return -1;
        }
        if (userNum < 1 || userNum > size) {
            return -1;
        }
        return userNum - 1;
    }

    private static <T> void printPrompts(List<Prompt<T>> qs) {
        for (int i = 0; i < qs.size(); ++i) {
            System.out.println("  " + (i+1) + ". " + qs.get(i).desc);
        }
    }

    private static void askErr(Scanner in, int n) {
        System.out.print("You must enter a number from 1-" + n + ".");
        in.nextLine();
    }

    public static final List<Prompt<Boolean>> YES_NO = List.of(
            new Prompt<>("Yes", true),
            new Prompt<>("No", false)
    );
}
