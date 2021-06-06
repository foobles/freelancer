package freelancer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public final class Player {
    private final ArrayList<Evidence> evidence;

    public Player() {
        evidence = new ArrayList<>();
    }

    public Player(ArrayList<Evidence> evidence) {
        this.evidence = evidence;
    }

    public List<Prompt<Evidence>> getEvidencePrompts(Evidence.Sort sort) {
        return evidence.stream()
                .filter(e -> e.getSort() == sort)
                .map(e -> new Prompt<>(e.getName() + " - " + e.getDescription(), e))
                .collect(Collectors.toList());
    }

    public Optional<Evidence> getEvidence(Object id) {
        return evidence.stream().filter(e -> e.getID() == id).findAny();
    }

    public void addEvidence(Evidence toAdd) {
        if (evidence.stream().anyMatch(e -> e.getID() == toAdd.getID())) {
            throw new RuntimeException("Cannot add evidence with duplicate ids (" + toAdd.getID() + ")");
        }
        evidence.add(toAdd);
    }

    public Optional<Evidence> promptEvidenceOptional(Scanner input, String p) {
        return Prompt.askOptional(input, p, EVID_SORT_PROMPTS).flatMap(sort ->
                Prompt.askOptional(input, "", getEvidencePrompts(sort))
        );
    }

    private static final List<Prompt<Evidence.Sort>> EVID_SORT_PROMPTS = List.of(
            new Prompt<>("Item", Evidence.Sort.Item),
            new Prompt<>("Hearsay", Evidence.Sort.Hearsay),
            new Prompt<>("Profile", Evidence.Sort.Profile)
    );
}
