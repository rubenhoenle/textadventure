package de.dhbw.project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

public class Terminal extends JFrame {

    Vector v = new Vector();
    JTextArea area;
    int pos = 0;

    private Game game;

    public Terminal(Game game, String title) {
        this.game = game;

        setTitle(title);
        JPanel j = new JPanel();
        setLayout(new GridLayout(1, 1));
        setSize(400,250);
        j.setLayout(new GridLayout(1, 1));
        area = new JTextArea();
        area.setBackground(Color.black);
        area.setForeground(Color.white);
        area.setCaretColor(Color.white);
        area.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                area(evt);
            }

            private void area(KeyEvent evt) {
                int keyCode = evt.getKeyCode();
                if (keyCode == 38) {
                    try {
                        String store = (String) v.get(v.size() - 1 - pos);
                        replacer(store);
                    } catch (Exception ex) {
                        //ex.printStackTrace();
                    }
                    if((pos + 1) < v.size()){
                        pos++;
                    }
                    evt.consume();
                } else if (keyCode == 40) {
                    try {
                        String store = (String) v.get(v.size() - 1 - pos);
                        replacer(store);
                    } catch (Exception ex) {
                        //ex.printStackTrace();
                    }
                    if(pos >= 1)
                    {
                        pos--;
                    }
                    evt.consume();
                } else if (keyCode == 10) {
                    v.add(linetext());
                    game.inputFromTerminal(linetext());
                }
            }
        });
        j.add(area);
        add(j);
        setVisible(true);
    }

    void replacer(String rep) {

        try {
            int caretOffset = area.getCaretPosition();
            int lineNumber = area.getLineOfOffset(caretOffset);
            int startOffset = area.getLineStartOffset(lineNumber);
            int endOffset = area.getLineEndOffset(lineNumber);

            area.replaceRange(rep, startOffset, endOffset);
        } catch (BadLocationException ex) {
            //ex.printStackTrace();
        }
    }

    String linetext() {
        String text = null;
        try {
            JTextArea ta = area;
            int offset = ta.getLineOfOffset(ta.getCaretPosition());
            int start = ta.getLineStartOffset(offset);
            int end = ta.getLineEndOffset(offset);
            text = ta.getText(start, (end - start));
        } catch (BadLocationException ex) {
            //ex.printStackTrace();
        }
        return text;

    }
}