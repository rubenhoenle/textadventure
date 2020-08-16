package de.dhbw.project.nls;

import java.util.ArrayList;
import java.util.List;

public class LazyCompositeFsa extends CompositeFsa {
    IBuildCompositeFsa fsaBuilder;
    CompositeFsa fsa = null;

    List<FSA> fsas = new ArrayList<FSA>();

    public LazyCompositeFsa(IBuildCompositeFsa fsaBuilder, IConsumeIndex indexConsumer) {
        super(indexConsumer);
        this.fsaBuilder = fsaBuilder;
    }

    public LazyCompositeFsa(IBuildCompositeFsa fsaBuilder, CompositeFsa fsa) {
        super(fsa);
        this.fsaBuilder = fsaBuilder;
    }

    @Override
    public void processChar(char c, int index) {
        if (fsa == null)
            build();

        fsa.processChar(c, index);
        super.setState(fsa.getState());
    }

    private void build() {
        fsa = fsaBuilder.build(this);
        for (FSA fsa : fsas) {
            this.fsa.add(fsa);
        }
        this.fsas = null;
    }

    @Override
    public CompositeFsa add(FSA fsa) {
        fsas.add(fsa);
        return this;
    }

    @Override
    protected void SetDataOnChildFsas(DataStorage dataStorage) {
        fsa.setDataOn(dataStorage);
    }

    @Override
    public void reset() {
        super.reset();
        if (fsa != null)
            fsa.reset();
    }

}
