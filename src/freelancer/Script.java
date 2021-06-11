package freelancer;

import java.util.Scanner;

@FunctionalInterface
public interface Script<Env> {
    void play(Scanner input, Player p, Env env);
}
