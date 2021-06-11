package freelancer;

@FunctionalInterface
public interface GameCondition<Env> {
    boolean test(Player p, Env e);
}
