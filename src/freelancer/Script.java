package freelancer;

import java.util.List;
import java.util.Scanner;

public final class Script<Env> {
    private final List<ScriptEvent<Env>> events;

    public Script(List<ScriptEvent<Env>> events) {
        this.events = events;
    }

    public void play(Scanner input, Player player, Env env) {
        for (var event : events) {
            event.run(input, player, env);
        }
    }
}
