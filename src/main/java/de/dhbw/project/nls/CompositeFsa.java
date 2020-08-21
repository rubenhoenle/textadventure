package de.dhbw.project.nls;

import java.util.ArrayList;
import java.util.List;

public abstract class CompositeFsa extends FSA {

    private static List<String> PRE = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;
        {
            add("i would like to");
            add("I'd like to");
            add("I want to");
            add("Please");
            add("Do");
        }
    };
    private static List<String> POST = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;
        {
            add(" please");
        }
    };

    public CompositeFsa(IConsumeIndex indexConsumer) {
        super(indexConsumer);
    }

    public CompositeFsa(CompositeFsa fsa) {
        super(fsa);
    }

    public abstract CompositeFsa add(FSA fsa);

    protected abstract void SetDataOnChildFsas(DataStorage dataStorage);

    public void setDataOn(DataStorage dataStorage) {
        SetDataOnChildFsas(dataStorage);
    }

    public static CompositeFsa buildCompositeFsaFrom(String pattern, CompositeFsa p) {

        CompositeFsa root = new AndCompositeFsa(p);
        List<CompositeFsa> currentFsas = new ArrayList<CompositeFsa>();
        currentFsas.add(root);

        addPreComposite(root);

        String word = "";

        boolean item = false;
        boolean optional = false;
        boolean or = false;
        boolean addedPost = false;

        for (int i = 0; i < pattern.length(); i++) {
            Character c = pattern.charAt(i);
            switch (c) {
            case '<':
                word = "";
                item = true;
                break;
            case '>':
                new IdentifierFsa(word, currentFsas.get(currentFsas.size() - 1));
                item = false;
                if (pattern.length() > i + 1 && ((Character) pattern.charAt(i + 1)).equals('+')) {
                    i += 2;
                    final String attribute = word;
                    String nextWord = null;
                    final boolean terminator, whitespace;
                    if (pattern.length() == i) {
                        terminator = true;
                        addedPost = true;
                        whitespace = false;
                    } else {
                        terminator = false;
                        whitespace = Character.isWhitespace(pattern.charAt(i));
                        if (whitespace)
                            i++;
                        String s = pattern.substring(i);
                        nextWord = s.split(" ")[0].trim();
                        i += (nextWord.length() - 1);
                    }
                    final String nW = nextWord;
                    new NoneOrMoreFsa(currentFsas.get(currentFsas.size() - 1), par -> {
                        AndCompositeFsa and = new AndCompositeFsa(par);
                        new WhitespaceFsa(and);
                        new IdentifierFsa(attribute, and);
                    }, par -> {
                        if (terminator) {
                            addPostComposite(par);
                        } else {
                            AndCompositeFsa and2 = new AndCompositeFsa(par);
                            new WhitespaceFsa(and2);
                            new WordFsa(nW, and2);
                        }
                        /*
                         * AndCompositeFsa and2 = new AndCompositeFsa(par); if(whitespace) new WhitespaceFsa(and2);
                         * if(terminator) new TerminatingCharFsa(and2); else new WordFsa(nW, and2);
                         */
                    });
                }
                word = "";
                break;
            case '(':
                or = true;
            case '[':
                optional = c.equals('[');
                word = "";
                OrCompositeFsa orComposite = OrCompositeFsa.create(currentFsas.get(currentFsas.size() - 1));
                currentFsas.add(orComposite);
                break;
            case ')':
                or = false;
                new WordFsa(word, currentFsas.remove(currentFsas.size() - 1));
                word = "";
                break;
            case '|':
                new WordFsa(word, currentFsas.get(currentFsas.size() - 1));
                word = "";
                break;
            case ']':
                optional = false;
                new WordFsa(word, currentFsas.get(currentFsas.size() - 1));
                new NoneFsa(currentFsas.remove(currentFsas.size() - 1));
                word = "";
                break;
            }

            if (Character.isLetter(c) || c.equals('?')) {
                word += c;
            } else if (Character.isWhitespace(c)) {
                if (optional || item || or) {
                    word += c;
                } else {
                    if (word.length() > 0) {
                        new WordFsa(word, currentFsas.get(currentFsas.size() - 1));
                        word = "";
                    }
                    new WhitespaceFsa(currentFsas.get(currentFsas.size() - 1));
                }
            }
        }

        if (word.length() > 0)
            new WordFsa(word, currentFsas.get(currentFsas.size() - 1));

        if (!addedPost) {
            addPostComposite(root);
        }

        return root;
    }

    private static void addPreComposite(CompositeFsa fsa) {
        OrCompositeFsa orComposite = OrCompositeFsa.create(fsa);
        for (String s : PRE) {
            AndCompositeFsa and = new AndCompositeFsa(orComposite);
            new WordFsa(s, and);
            new WhitespaceFsa(and);
        }
        new NoneFsa(orComposite);
    }

    private static void addPostComposite(CompositeFsa fsa) {
        OrCompositeFsa orComposite = OrCompositeFsa.create(fsa);
        for (String s : POST) {
            AndCompositeFsa and = new AndCompositeFsa(orComposite);
            new WordFsa(s, and);
            new TerminatingCharFsa(and);
        }
        new TerminatingCharFsa(orComposite);
    }
}
