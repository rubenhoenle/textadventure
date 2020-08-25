package de.dhbw.project.nls;

public class IdentifierFsa extends FSA {
    private StringBuilder identifierName = new StringBuilder();
    private boolean isIdentifier = false;
    private String key = "test";

    public IdentifierFsa(String key, IConsumeIndex indexConsumer) {
        super(indexConsumer);
        this.key = key;
    }

    public IdentifierFsa(String key, CompositeFsa indexConsumer) {
        super(indexConsumer);
        this.key = key;
    }

    @Override
    public void processChar(char c, int index) {
        if (Character.isLetter(c) || ((Character) c).equals(',')) {
            identifierName.append(c);
            isIdentifier = true;
        } else {
            if (isIdentifier) {
                super.setState(State.MATCH);
                super.setMatchEndIndex(index - 1);
            } else
                super.setState(State.NO_MATCH);
        }
    }

    @Override
    public void setDataOn(DataStorage dataStorage) {
        dataStorage.add(key, identifierName.toString());
    }

    @Override
    public void reset() {
        super.reset();
        identifierName = new StringBuilder();
        isIdentifier = false;
    }
}
