package freelancer;

import java.util.Scanner;

public interface PromptTree<Env> {
    Script<Env> runPrompt(Scanner in);
}
