package freelancer;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public record PromptTreeBranch<Env>(
        String desc,
        List<Prompt<PromptTree<Env>>> children
) implements PromptTree<Env> {
    @Override
    public Script<Env> runPrompt(Scanner in) {
        return Prompt.ask(in, desc, children).runPrompt(in);
    }

    @Override
    public Optional<Script<Env>> runPromptOptional(Scanner in) {
        return Prompt.askOptional(in, desc, children).flatMap(subtree -> subtree.runPromptOptional(in));
    }
}
