package de.dhbw.project.nls;

public class SingleCharacterFsa extends FSA {
    private char character;

    public SingleCharacterFsa(char character, IConsumeIndex indexConsumer) {
        super(indexConsumer);
        this.character = character;
    }

    public SingleCharacterFsa(char character, CompositeFsa fsa) {
        super(fsa);
        this.character = character;
    }

    @Override
    public void processChar(char c, int index) {
        if (c == this.character) {
            super.setMatchEndIndex(index);
            super.setState(FSA.State.MATCH);
        } else {
            super.setState(FSA.State.NO_MATCH);
        }
    }
}
