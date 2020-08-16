package de.dhbw.project.nls;

import java.util.ArrayList;
import java.util.List;

public class WhitespaceFsa extends FSA {
    private List<Character> whitespaceChars = new ArrayList<Character>() {
        private static final long serialVersionUID = 1L;
        {
            add(' ');
            add('\n');
            add('\r');
            add('\t');
            // add('\0');
        }
    };
    private boolean foundOne = false;

    public WhitespaceFsa(IConsumeIndex indexConsumer) {
        super(indexConsumer);
    }

    public WhitespaceFsa(CompositeFsa fsa) {
        super(fsa);
    }

    @Override
    public void processChar(char c, int index) {
        if (whitespaceChars.contains(c)) {
            foundOne = true;
        } else {
            if (foundOne) {
                super.setMatchEndIndex(index - 1);
                super.setState(FSA.State.MATCH);
            } else
                super.setState(FSA.State.NO_MATCH);
        }
    }

    @Override
    public void reset() {
        super.reset();
        foundOne = false;
    }
}
