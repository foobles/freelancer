package freelancer;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

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

    public void play() {
        //noinspection InfiniteLoopStatement
        while (true) {
            if (location.familiarize()) {
                System.out.println(location.getName());
                System.out.println("  " + location.getDescription());
                location.getIntroScript(scripts).ifPresent(scr -> scr.play(player, env));
            }
            Optional<Npc<ScriptReg, Env>> npc = location.getNpc(env);
            var actionPrompts = npc.isPresent()
                    ? Action.NPC_PROMPTS
                    : Action.NO_NPC_PROMPTS;

            switch (Prompt.ask(input, "What now?", actionPrompts)) {
                case TALK -> {
                    assert npc.isPresent();
                    Prompt.askOptional(input, "About what?", npc.get().getDialogPrompts(scripts))
                            .ifPresent(scr -> scr.play(player, env));
                }

                case PRESENT -> {
                    assert npc.isPresent();
                    Prompt.askOptional(input, "Present what?", EVID_SORT_PROMPTS).flatMap(evidSort ->
                        Prompt.askOptional(input, "", player.getEvidencePrompts(evidSort))
                    ).ifPresent(e -> npc.get()
                            .getEvidenceResponse(scripts, e.getID())
                            .play(player, env)
                    );
                }

                case EXAMINE -> location.getExaminationTree(scripts)
                        .runPromptOptional(input)
                        .ifPresent(scr -> scr.play(player, env));

                case MOVE -> {
                    var connectionPrompts = location
                            .getConnectingLocations(env)
                            .stream()
                            .map(loc -> new Prompt<>(loc.getName(), loc))
                            .collect(Collectors.toList());

                    Prompt.askOptional(input, "Where to?", connectionPrompts)
                            .ifPresent(newLoc -> location = newLoc);
                }
            }
        }
    }

}
