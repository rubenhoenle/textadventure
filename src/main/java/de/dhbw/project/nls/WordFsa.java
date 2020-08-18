package de.dhbw.project.nls;

public class WordFsa extends FSA {
    private String word;
    private int wordIndex;

    public WordFsa(String word, IConsumeIndex indexConsumer) {
        super(indexConsumer);
        this.word = word;
    }

    public WordFsa(String word, CompositeFsa fsa) {
        super(fsa);
        this.word = word;
    }

    @Override
    public void processChar(char c, int index) {
        if (wordIndex == word.length()) {
            if (Character.isLetter(c)) {
                super.setState(FSA.State.NO_MATCH);
                return;
            }

            super.setState(FSA.State.MATCH);
            super.setMatchEndIndex(index - 1);
            return;
        }

        if (Character.toLowerCase(word.charAt(wordIndex)) != Character.toLowerCase(c)) {
            super.setState(FSA.State.NO_MATCH);
            return;
        }

        wordIndex++;
    }

    @Override
    public void reset() {
        super.reset();
        wordIndex = 0;
    }

}
