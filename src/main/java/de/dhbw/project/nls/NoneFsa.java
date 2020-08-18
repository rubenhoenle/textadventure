package de.dhbw.project.nls;

public class NoneFsa extends FSA {

    public NoneFsa(CompositeFsa fsa) {
        super(fsa);
    }

    @Override
    public void processChar(char c, int index) {
        super.setState(State.MATCH);
        super.setMatchEndIndex(index - 1);
    }

}
