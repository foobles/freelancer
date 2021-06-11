package freelancer;

import java.util.List;
import java.util.Scanner;

public final class ScriptCond<Env> implements Script<Env> {
    private final Cond<Env> cond;
    private final List<Script<Env>> scripts;

    @FunctionalInterface
    public interface Cond<Env> {
        int getScriptIdx(Player p, Env env);
    }

    @FunctionalInterface
    public interface CondBoolean<Env> {
        boolean getScriptBoolean(Player p, Env env);
    }

    public ScriptCond(Cond<Env> cond, List<Script<Env>> scripts) {
        this.cond = cond;
        this.scripts = scripts;
    }

    public ScriptCond(CondBoolean<Env> cond, Script<Env> script1, Script<Env> script2) {
        this(
                (p, e) -> cond.getScriptBoolean(p, e) ? 0 : 1,
                List.of(script1, script2)
        );
    }

    public ScriptCond(CondBoolean<Env> cond, Script<Env> script) {
        this(
                (p, e) -> cond.getScriptBoolean(p, e) ? 0 : -1,
                List.of(script)
        );
    }

    @Override
    public void play(Scanner input, Player p, Env env) {
        int idx = cond.getScriptIdx(p, env);
        if (idx >= 0) {
            scripts.get(idx).play(input, p, env);
        }
    }
}
