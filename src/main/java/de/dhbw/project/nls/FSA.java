package de.dhbw.project.nls;

public abstract class FSA implements IConsumeIndex {
    public enum State {
        PENDING, MATCH, NO_MATCH
    }

    private State state = State.PENDING;
    private IConsumeIndex indexConsumer;

    protected void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public FSA(IConsumeIndex indexConsumer) {
        this.indexConsumer = indexConsumer;
    }

    public FSA(CompositeFsa fsa) {
        this.indexConsumer = fsa;
        fsa.add(this);
    }

    public /* protected */ void setMatchEndIndex(int index) {
        indexConsumer.setMatchEndIndex(index);
    }

    public abstract void processChar(char c, int index);

    public void setDataOn(DataStorage dataStorage) {
    }

    public void reset() {
        this.state = State.PENDING;
    }
}
