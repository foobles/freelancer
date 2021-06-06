package freelancer;

import java.util.Scanner;

@FunctionalInterface
public interface ScriptEvent<Env> {
    void run(Scanner input, Player p, Env env);
}
