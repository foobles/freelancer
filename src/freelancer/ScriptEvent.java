package freelancer;

@FunctionalInterface
public interface ScriptEvent<Env> {
    void run(Player p, Env env);
}
