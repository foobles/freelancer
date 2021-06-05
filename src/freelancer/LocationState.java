package freelancer;

import java.util.List;
import java.util.Optional;

public interface LocationState<ScriptReg, Env> {
    Optional<Npc<ScriptReg, Env>> getNpc(Env env);
    PromptTree<Env> getExaminationTree(ScriptReg scripts);
    List<Location<ScriptReg, Env>> getConnectingLocations(Env env);

    default Optional<Script<Env>> getIntroScript(ScriptReg scripts) {
        return Optional.empty();
    }
}
