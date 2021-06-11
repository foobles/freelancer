package freelancer;

import java.util.List;
import java.util.Scanner;

public final class ScriptCond<Env> implements Script<Env> {
    private final CondIndex<Env> cond;
    private final List<Script<Env>> scripts;

    @FunctionalInterface
    public interface CondIndex<Env> {
        int getScriptIdx(Player p, Env env);
    }

    public ScriptCond(CondIndex<Env> cond, List<Script<Env>> scripts) {
        this.cond = cond;
        this.scripts = scripts;
    }

    @Override
    public void play(Scanner input, Player p, Env env) {
        int idx = cond.getScriptIdx(p, env);
        if (idx >= 0) {
            scripts.get(idx).play(input, p, env);
        }
    }
}
