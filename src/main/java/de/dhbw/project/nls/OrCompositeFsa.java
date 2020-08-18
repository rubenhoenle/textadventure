package de.dhbw.project.nls;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrCompositeFsa extends CompositeFsa {
    private List<FSA> fsas;
    // private List<Integer> fsaMatchEndIndicies;
    private int currentFsaIndex = 0;
    private IConsumeIndex indexConsumer;
    private Optional<Integer> firstIndex = Optional.empty();
    private FSA match = null;

    private OrCompositeFsa(Dispatcher dispatcher, IConsumeIndex indexConsumer) {
        super(dispatcher);
        init(dispatcher, indexConsumer);
    }

    private OrCompositeFsa(Dispatcher dispatcher, CompositeFsa fsa) {
        super(dispatcher);
        fsa.add(this);
        init(dispatcher, fsa);
    }

    private void init(Dispatcher dispatcher, IConsumeIndex indexConsumer) {
        dispatcher.setIndexConsumer(index -> handleIndex(index));
        this.indexConsumer = indexConsumer;
        fsas = new ArrayList<FSA>();
        // fsaMatchEndIndicies = new ArrayList<Integer>();
    }

    private void handleIndex(int index) {
        // fsaMatchEndIndicies.set(currentFsaIndex, index);
        indexConsumer.setMatchEndIndex(index);
    }

    public CompositeFsa add(FSA fsa) {
        fsas.add(fsa);
        // fsaMatchEndIndicies.add(-1);
        return this;
    }

    public static OrCompositeFsa create(IConsumeIndex indexConsumer) {
        Dispatcher dispatcher = new Dispatcher();
        return new OrCompositeFsa(dispatcher, indexConsumer);
    }

    public static OrCompositeFsa create(CompositeFsa fsa) {
        Dispatcher dispatcher = new Dispatcher();
        return new OrCompositeFsa(dispatcher, fsa);
    }

    @Override
    public void processChar(char c, int index) {
        if (!firstIndex.isPresent())
            firstIndex = Optional.of(index);

        FSA fsa = fsas.get(currentFsaIndex);
        fsa.processChar(c, index);
        State fsaState = fsa.getState();

        if (fsaState == State.MATCH) {
            super.setState(FSA.State.MATCH);
            match = fsa;
            // int matchEndIndex = fsaMatchEndIndicies.get(currentFsaIndex);
            // indexConsumer.setMatchEndIndex(matchEndIndex);
        } else if (fsaState == State.NO_MATCH) {
            currentFsaIndex++;
            if (currentFsaIndex == fsas.size()) {
                super.setState(State.NO_MATCH);
            } else
                indexConsumer.setMatchEndIndex(firstIndex.get() - 1);
        }
    }

    @Override
    protected void SetDataOnChildFsas(DataStorage dataStorage) {
        match.setDataOn(dataStorage);
    }

    @Override
    public void reset() {
        super.reset();
        currentFsaIndex = 0;
        firstIndex = Optional.empty();
        for (FSA fsa : fsas) {
            fsa.reset();
        }
    }
}