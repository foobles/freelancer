package freelancer;

public record PromptTreeLeaf<Env>(
        Script<Env> script
) implements PromptTreeNode<Env> {}
