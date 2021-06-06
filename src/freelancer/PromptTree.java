package freelancer;

import java.util.Optional;
import java.util.Scanner;

public interface PromptTree<Env> {
    Script<Env> runPrompt(Scanner in);
    Optional<Script<Env>> runPromptOptional(Scanner in);
}
