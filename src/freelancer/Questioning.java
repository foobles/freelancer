package freelancer;

import java.util.List;
import java.util.Scanner;

public final class Questioning<Env> implements ScriptEvent<Env> {
    private final List<Prompt<StatementDetail<Env>>> details;
    private final Script<Env> retortNonContradictory;

    public Questioning(List<Prompt<StatementDetail<Env>>> details, Script<Env> retortNonContradictory) {
        this.details = details;
        this.retortNonContradictory = retortNonContradictory;
    }

    @Override
    public void run(Scanner input, Player p, Env env) {
        String pressQuestion = "Press further?";
        while (Prompt.ask(input, pressQuestion, Prompt.YES_NO)) {
            pressQuestion = "Keep pressing?";
            Prompt.askOptional(input, "About what?", details).ifPresent(stmt ->
                pressStatement(input, stmt, p, env)
            );
        }
    }

    private void pressStatement(Scanner input, StatementDetail<Env> stmt, Player p, Env env) {
        stmt.getExplanation().play(input, p, env);
        if (Prompt.ask(input, "Was that contradictory?", Prompt.YES_NO)) {
            p.promptEvidenceOptional(input, "Contradicts what?").ifPresent(evidence ->
                stmt.getContradictionResponse(evidence.getID()).orElse(retortNonContradictory).play(input, p, env)
            );
        }
    }
}
