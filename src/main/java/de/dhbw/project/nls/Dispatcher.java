package de.dhbw.project.nls;

public class Dispatcher implements IConsumeIndex {

    private IConsumeIndex indexConsumer;

    public void setIndexConsumer(IConsumeIndex indexConsumer) {
        this.indexConsumer = indexConsumer;
    }

    @Override
    public void setMatchEndIndex(int index) {
        indexConsumer.setMatchEndIndex(index);
    }

}
