package freelancer;

public record DialogPrompt<NpcReg>(String topic, Script<NpcReg> script) {}