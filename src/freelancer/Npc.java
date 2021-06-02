package freelancer;

import java.util.List;

public final class Npc<ScriptReg, NpcReg> {
    private String name;
    private final String description;
    private final NpcBrain<ScriptReg, NpcReg> brain;
    private Emotion emotion = Emotion.NEUTRAL;
    private boolean knowsPlayer = false;

    public Npc(String name, String description, NpcBrain<ScriptReg, NpcReg> brain) {
        this.name = name;
        this.description = description;
        this.brain = brain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharacterDescription() {
        return description;
    }

    public String getEmotionDescription() {
        return brain.getEmotionDescription(emotion);
    }

    public Emotion getEmotion() {
        return emotion;
    }

    public void setEmotion(Emotion emotion) {
        this.emotion = emotion;
    }

    public boolean getKnowsPlayer() {
        return knowsPlayer;
    }

    public void setKnowsPlayer(boolean knowsPlayer) {
        this.knowsPlayer = knowsPlayer;
    }

    public List<Prompt<Script<NpcReg>>> getDialogPrompts(ScriptReg scripts) {
        return brain.getDialogPrompts(scripts);
    }

    public Script<NpcReg> getEvidenceResponse(ScriptReg scripts, Object id) {
        return brain.getEvidenceResponse(scripts, id);
    }
}