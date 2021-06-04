package freelancer;

public final class Location<ScriptReg, Env> {
    private final PromptTree<Env> examinationTree;
    private final String name;
    private final String description;
    private Npc<ScriptReg, Env> npc;
    private boolean familiarSituation = false;

    public Location(
            Npc<ScriptReg, Env> npc,
            PromptTree<Env> examinationTree,
            String name,
            String description
    ) {
        this.npc = npc;
        this.examinationTree = examinationTree;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Npc<ScriptReg, Env> getNpc() {
        return npc;
    }

    public void setNpc(Npc<ScriptReg, Env> npc) {
        this.npc = npc;
    }

    public void removeNpc() {
        this.npc = null;
    }

    public boolean isFamiliarSituation() {
        return familiarSituation;
    }

    public void setFamiliarSituation(boolean familiarSituation) {
        this.familiarSituation = familiarSituation;
    }

    public PromptTree<Env> getExaminationTree() {
        return examinationTree;
    }
}
