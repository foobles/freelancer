package freelancer;

import java.util.*;

public class Main {
    private static record Scene1NpcReg(
            Npc<Scene1ScriptReg, Scene1NpcReg> testNpc1
           // Npc<Scene1ScriptReg, Scene1NpcReg> testNpc2
    ) {}

    private static record Scene1ScriptReg(
            Script<Scene1NpcReg> testDialog1,
            Script<Scene1NpcReg> testDialog2
    ) {}

    public static void main(String[] args) {
        var npcs = new Scene1NpcReg(
                new Npc<>(
                        "Test Person",
                        "A person so utterly bland that there is no point to describe them.",
                        new NpcBrain<>() {
                            @Override
                            public String getEmotionDescription(Emotion e) {
                                return "Expressionless void";
                            }

                            @Override
                            public Script<Scene1NpcReg> getEvidenceResponse(Scene1ScriptReg scripts, Object id) {
                                return null;
                            }

                            @Override
                            public List<DialogPrompt<Scene1NpcReg>> getDialogPrompts(Scene1ScriptReg scripts) {
                                return List.of(
                                        new DialogPrompt<>("Say Hi", scripts.testDialog1),
                                        new DialogPrompt<>("Something...?", scripts.testDialog2)
                                );
                            }
                        })
        );

        var scripts = new Scene1ScriptReg(
                new Script<>(Arrays.asList(
                        new TextMessage<>(npcs.testNpc1, new String[]{
                                "Hello! How do you do?",
                                "Fine weather today, isn't it?"
                        }),
                        new TextMessage<>("You", new String[]{
                                "K."
                        })
                )),
                new Script<>(Collections.singletonList(
                        new TextMessage<Scene1NpcReg>(npcs.testNpc1, new String[]{
                                "Excuse me? I haven't the slightest",
                                "what you are talking about, I sincerely",
                                "apologize my fellow."
                        })
                ))
        );
//
//        npcs.testNpc1.getDialogPrompts(scripts).get(1).script().play(new Player(), npcs);

        UserQuery q = new UserQuery(
                "What to do?",
                new QueryOption("Something", () -> {
                    System.out.println("yay");
                }),
                new QueryOption("Something *else*", () -> {
                    System.out.println("wowah");
                })
        );

        q.run(new Scanner(System.in));
    }
}
