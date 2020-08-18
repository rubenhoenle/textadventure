package de.dhbw.project.nls;

import java.util.Optional;

import de.dhbw.project.nls.FSA.State;

public class LanguageProcessor implements IConsumeIndex {
    private int currentIndex = 0;
    private FSA fsa;

    public void setFsa(FSA fsa) {
        this.fsa = fsa;
    }

    public LanguageProcessor() {
    }

    public Optional<DataStorage> getCommandFrom(String input) {
        currentIndex = 0;

        while (currentIndex < input.length()) {
            FSA.State fsaState = fsa.getState();
            if (fsaState != State.PENDING)
                break;

            fsa.processChar(input.charAt(currentIndex), currentIndex);

            currentIndex++;
        }

        FSA.State fsaState = fsa.getState();
        if (fsaState == FSA.State.MATCH) {
            DataStorage storage = new DataStorage();
            fsa.setDataOn(storage);

            return Optional.of(storage);
        } else
            return Optional.empty();
    }

    @Override
    public void setMatchEndIndex(int index) {
        currentIndex = index;
    }
}
