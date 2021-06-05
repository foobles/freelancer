package freelancer;

import java.util.List;
import java.util.Optional;

public final class Location<ScriptReg, Env> {
    private final LocationState<ScriptReg, Env> state;
    private final String name;
    private final String description;
    private boolean familiar;

    public Location(
            LocationState<ScriptReg, Env> state,
            String name,
            String description
    ) {
        this.state = state;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }

    public Optional<Npc<ScriptReg, Env>> getNpc(Env env) {
        return state.getNpc(env);
    }

    public boolean familiarize() {
        boolean ret = !familiar;
        familiar = true;
        return ret;
    }

    public Optional<Script<Env>> getIntroScript(ScriptReg scripts) {
        return state.getIntroScript(scripts);
    }

    public PromptTree<Env> getExaminationTree(ScriptReg scripts) {
        return state.getExaminationTree(scripts);
    }

    public List<Location<ScriptReg, Env>> getConnectingLocations(Env env) {
        return state.getConnectingLocations(env);
    }
}
