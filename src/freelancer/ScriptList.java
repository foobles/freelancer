package freelancer;

import java.util.List;
import java.util.Scanner;

public final class ScriptList<Env> implements Script<Env> {
    private final List<Script<Env>> events;

    public ScriptList(List<Script<Env>> events) {
        this.events = events;
    }

    public void play(Scanner input, Player player, Env env) {
        for (var event : events) {
            event.play(input, player, env);
        }
    }
}
