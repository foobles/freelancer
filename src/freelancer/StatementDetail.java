package freelancer;

import java.util.Arrays;
import java.util.Optional;

public final class StatementDetail<Env> {
    private final Script<Env> explanation;
    private final Script<Env> contradictionResponse;
    private final Object[] contradictoryEvidenceIDs;

    public StatementDetail(Script<Env> explanation, Script<Env> contradictionResponse, Object[] contradictoryEvidenceIDs) {
        this.explanation = explanation;
        this.contradictionResponse = contradictionResponse;
        this.contradictoryEvidenceIDs = contradictoryEvidenceIDs;

        assert contradictionResponse != null || contradictoryEvidenceIDs.length == 0;
    }

    public Script<Env> getExplanation() {
        return explanation;
    }

    public Optional<Script<Env>> getContradictionResponse(Object evidenceID) {
        return Optional
                .ofNullable(contradictionResponse)
                .filter(scr ->
                        Arrays.stream(contradictoryEvidenceIDs).anyMatch(e -> e == evidenceID)
                );
    }
}