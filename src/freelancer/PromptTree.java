package freelancer;

import java.util.List;
import java.util.Scanner;

public record PromptTree<Env>(
        String desc,
        List<Prompt<PromptTreeNode<Env>>> children
) implements PromptTreeNode<Env> {
    public Script<Env> run(Scanner in) {
        var cur = Prompt.ask(in, desc, children);
        while (cur instanceof PromptTree<Env> curTree) {
            cur = Prompt.ask(in, curTree.desc, curTree.children);
        }
        return ((PromptTreeLeaf<Env>) cur).script();
    }
}
