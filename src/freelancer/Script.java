package freelancer;

import java.util.List;

public final class Script<NpcReg> {
    private final List<ScriptEvent<NpcReg>> events;

    public Script(List<ScriptEvent<NpcReg>> events) {
        this.events = events;
    }

    public void play(Player player, NpcReg npcs) {
        for (var event : events) {
            event.run(player, npcs);
        }
    }
}
