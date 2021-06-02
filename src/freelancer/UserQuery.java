package freelancer;

import java.util.Scanner;

public final class UserQuery {
    private final String query;
    private final QueryOption[] options;

    public UserQuery(String query, QueryOption... options) {
        this.query = query;
        this.options = options;
    }

    public void run(Scanner in) {
        while (true) {
            System.out.println(query);
            for (int i = 0; i < options.length; ++i) {
                System.out.println("  " + (i+1) + ". " + options[i].desc());
            }
            System.out.print("> ");
            String userLine = in.nextLine();
            int userNum;

            try {
                userNum = Integer.parseInt(userLine.trim());
            } catch (NumberFormatException e) {
                userQueryError(in);
                continue;
            }

            if (userNum < 1 || userNum > options.length) {
                userQueryError(in);
                continue;
            }

            options[userNum-1].action().run();
            break;
        }
    }

    private void userQueryError(Scanner in) {
        System.out.print("Enter a number from 1-" + options.length + ". ");
        in.nextLine();
    }
}
