package freelancer;

import java.util.List;
import java.util.Scanner;

public class GameState<ScriptReg, Env> {
    private final Scanner input;
    private final ScriptReg scripts;
    private final Env env;
    private final Player player = new Player();
    private Location<ScriptReg, Env> location;

    private enum Action {
        TALK, PRESENT, EXAMINE, MOVE;

        public static final List<Prompt<Action>> NPC_PROMPTS = List.of(
                new Prompt<>("Talk", TALK),
                new Prompt<>("Present", PRESENT),
                new Prompt<>("Examine", EXAMINE),
                new Prompt<>("Move", MOVE)
        );

        public static final List<Prompt<Action>> NO_NPC_PROMPTS = List.of(
                new Prompt<>("Examine", EXAMINE),
                new Prompt<>("Move", MOVE)
        );
    }

    private static final List<Prompt<Evidence.Sort>> EVID_SORT_PROMPTS = List.of(
            new Prompt<>("Item", Evidence.Sort.Item),
            new Prompt<>("Hearsay", Evidence.Sort.Hearsay),
            new Prompt<>("Profile", Evidence.Sort.Profile)
    );

    public GameState(
            Scanner input,
            ScriptReg scripts,
            Env env,
            Location<ScriptReg, Env> startLoc
    ) {
        this.input = input;
        this.scripts = scripts;
        this.env = env;
        this.location = startLoc;
    }

    public void play(Script<Env> mediaRes) {
        if (mediaRes != null) {
            mediaRes.play(player, env);
        }

        //noinspection InfiniteLoopStatement
        while (true) {
            familiarizeLocation();
            var npc = location.getNpc();
            var actionPrompts = (npc != null)
                    ? Action.NPC_PROMPTS
                    : Action.NO_NPC_PROMPTS;

            switch (Prompt.ask(input, "What now?", actionPrompts)) {
                case TALK -> {
                    assert npc != null;
                    Prompt.ask(input, "About what?", npc.getDialogPrompts(scripts))
                            .play(player, env);
                }
                case PRESENT -> {
                    assert npc != null;
                    var evid_sort = Prompt.ask(input, "Present what?", EVID_SORT_PROMPTS);
                    var evidence = Prompt.ask(input, "", player.getEvidencePrompts(evid_sort));
                    npc.getEvidenceResponse(scripts, evidence).play(player, env);
                }
                case EXAMINE -> {
                    location.getExaminationTree().run(input).play(player, env);
                }
                case MOVE -> {
                    // TODO
                    throw new RuntimeException("todo");
                }
            }
        }
    }

    private void familiarizeLocation() {
        if (!location.isFamiliarSituation()) {
            System.out.println(location.getName());
            System.out.println("\t" + location.getDescription());
            location.setFamiliarSituation(true);
        }
    }
}
