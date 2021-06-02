package freelancer;

import java.util.List;

public interface NpcBrain<ScriptReg, NpcReg> {
    String getEmotionDescription(Emotion e);
    Script<NpcReg> getEvidenceResponse(ScriptReg scripts, Object id);
    List<Prompt<Script<NpcReg>>> getDialogPrompts(ScriptReg scripts);
}
