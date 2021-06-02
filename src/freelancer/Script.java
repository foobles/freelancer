package freelancer;

import java.util.List;

public final class Script<Env> {
    private final List<ScriptEvent<Env>> events;

    public Script(List<ScriptEvent<Env>> events) {
        this.events = events;
    }

    public void play(Player player, Env env) {
        for (var event : events) {
            event.run(player, env);
        }
    }
}
