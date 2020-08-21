package de.dhbw.project.nls;

public class TerminatingCharFsa extends SingleCharacterFsa {
    public TerminatingCharFsa(IConsumeIndex indexConsumer) {
        super('\0', indexConsumer);
    }

    public TerminatingCharFsa(CompositeFsa fsa) {
        super('\0', fsa);
    }
}
