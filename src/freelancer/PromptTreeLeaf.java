package freelancer;

import java.util.Scanner;

public record PromptTreeLeaf<Env>(
        Script<Env> script
) implements PromptTree<Env> {
    @Override
    public Script<Env> runPrompt(Scanner in) {
        return script;
    }
}
