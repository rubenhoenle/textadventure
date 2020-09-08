package de.dhbw.project.nls;

import java.util.ArrayList;
import java.util.List;

public abstract class CompositeFsa extends FSA {

    private static boolean addedPost = false;

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

        addPreComposite(root);
        buildCompositeFsa(pattern, root);
        if (!addedPost) {
            addPostComposite(root);
        }

        return root;
    }

    public static CompositeFsa buildCompositeFsa(String pattern, CompositeFsa root) {

        String word = "";

        for (int i = 0; i < pattern.length(); i++) {
            Character c = pattern.charAt(i);
            switch (c) {
            case '>':
                new IdentifierFsa(word, root);

                if (pattern.length() > i + 1 && ((Character) pattern.charAt(i + 1)).equals('+')) {
                    i += 2;
                    final String attribute = word;
                    final boolean terminator;
                    final String newPattern;

                    if (pattern.length() == i) {
                        newPattern = "";
                        terminator = true;
                        addedPost = true;
                    } else {
                        terminator = false;

                        String whitespeceString = "";
                        while (pattern.substring(i).charAt(0) == ' ') {
                            whitespeceString += " ";
                            i++;
                        }

                        String subPattern;
                        if (pattern.substring(i).charAt(0) == '(') {
                            subPattern = pattern.substring(i).split("\\)")[0] + ")";
                        } else {
                            subPattern = pattern.substring(i).split(" ")[0];
                        }
                        newPattern = whitespeceString + subPattern;
                        i += subPattern.length() - 1;
                    }

                    new NoneOrMoreFsa(root, par -> {
                        AndCompositeFsa and = new AndCompositeFsa(par);
                        new WhitespaceFsa(and);
                        new IdentifierFsa(attribute, and);
                    }, par -> {
                        if (terminator) {
                            addPostComposite(par);
                        } else {
                            AndCompositeFsa and2 = new AndCompositeFsa(par);
                            buildCompositeFsa(newPattern, and2);
                        }
                    });
                }
                word = "";
                break;

            case '(':
                OrCompositeFsa orComposite = OrCompositeFsa.create(root);
                String subPattern = pattern.substring(i + 1).split("\\)")[0];
                buildOrCompositeFsa(subPattern, orComposite);
                i += subPattern.length() + 1;
                break;

            case '[':
                OrCompositeFsa orNoneComposite = OrCompositeFsa.create(root);
                String subPattern2 = pattern.substring(i + 1).split("\\]")[0];
                buildOrCompositeFsa(subPattern2, orNoneComposite);
                new NoneFsa(orNoneComposite);
                i += subPattern2.length() + 1;
                break;
            }

            if (Character.isLetter(c) || c.equals('?')) {
                word += c;
            } else if (Character.isWhitespace(c)) {

                if (word.length() > 0) {
                    new WordFsa(word, root);
                    word = "";
                }
                new WhitespaceFsa(root);

            }
        }

        if (word.length() > 0)
            new WordFsa(word, root);

        return root;
    }

    private static void buildOrCompositeFsa(String pattern, CompositeFsa fsa) {
        String[] parts = pattern.split("\\|");
        for (String part : parts) {
            AndCompositeFsa andFsa = new AndCompositeFsa(fsa);
            buildCompositeFsa(part, andFsa);
        }
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
