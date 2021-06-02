package freelancer;

public final class TextMessage<NpcReg> implements ScriptEvent<NpcReg> {
    private final String speakerName;
    private final String[] lines;

    public TextMessage(String speakerName, String[] lines) {
        this.speakerName = speakerName;
        this.lines = lines;
    }

    public TextMessage(Npc<?, ?> npc, String[] lines) {
        this(npc.getName(), lines);
    }

    @Override
    public void run(Player p, Object npcs) {
        System.out.println(speakerName + ":");
        for (String line : lines) {
            System.out.print("    ");
            speakLine(line);
        }
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
