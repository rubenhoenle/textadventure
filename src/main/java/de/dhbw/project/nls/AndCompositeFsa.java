package de.dhbw.project.nls;

import java.util.ArrayList;
import java.util.List;

public class AndCompositeFsa extends CompositeFsa {
    private List<FSA> fsas;
    private int currentFsaIndex = 0;

    public AndCompositeFsa(IConsumeIndex indexConsumer) {
        super(indexConsumer);
        fsas = new ArrayList<FSA>();
    }

    public AndCompositeFsa(CompositeFsa indexConsumer) {
        super(indexConsumer);
        fsas = new ArrayList<FSA>();
    }

    public CompositeFsa add(FSA fsa) {
        fsas.add(fsa);
        return this;
    }

    @Override
    public void reset() {
        super.reset();
        currentFsaIndex = 0;
        for (FSA fsa : fsas) {
            fsa.reset();
        }
    }

    @Override
    public void processChar(char c, int index) {
        FSA fsa = fsas.get(currentFsaIndex);
        fsa.processChar(c, index);
        FSA.State fsaState = fsa.getState();

        if (fsaState == FSA.State.NO_MATCH) {
            super.setState(FSA.State.NO_MATCH);
        } else if (fsaState == FSA.State.MATCH) {
            currentFsaIndex++;

            if (currentFsaIndex == fsas.size()) {
                super.setState(FSA.State.MATCH);
                // super.setMatchIndex(fsa.getMatchIndex());
            }
        }
    }

    @Override
    protected void SetDataOnChildFsas(DataStorage dataStorage) {
        for (FSA fsa : fsas) {
            fsa.setDataOn(dataStorage);
        }
    }
}
