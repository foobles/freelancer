package freelancer;

@FunctionalInterface
public interface ScriptEvent<NpcReg> {
    void run(Player p, NpcReg npcs);
}
