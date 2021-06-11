package freelancer;

import java.util.Scanner;

public final class ScriptLine<Env> implements Script<Env> {
    private final String speakerName;
    private final String[] lines;

    public ScriptLine(String speakerName, String... lines) {
        this.speakerName = speakerName;
        this.lines = lines;
    }

    public ScriptLine(Npc<?, ?> npc, String... lines) {
        this(npc.getName(), lines);
    }

    @Override
    public void play(Scanner input, Player p, Env env) {
        System.out.println(speakerName + ":");
        for (String line : lines) {
            System.out.print("    ");
            speakLine(line);
        }
        input.nextLine();
    }

    private void speakLine(String line) {
        try {
            for (int i = 0; i < line.length(); ++i) {
                char c = line.charAt(i);
                System.out.print(c);
                //noinspection BusyWait
                Thread.sleep(switch (c) {
                    case '.', '!', '?' -> 600;
                    case ',' -> 300;
                    default -> 30;
                });
            }
            System.out.println();
        } catch (InterruptedException ignored) { }
    }
}
