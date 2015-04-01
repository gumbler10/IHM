package com.iup.tp.twitup.ihm;

import com.iup.tp.twitup.core.Twitup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Romain on 12/03/15.
 */
public class CreationTwit extends JPanel {

    private Twitup twitup;
    private TwitupMainView twitupMainView;
    private JLabel twitLabel;
    private JTextArea twitText;
    private JButton EnvoiTwit;
    boolean ignoreInput = false;
    int charMax = 150;
    JLabel charCntLabel = new JLabel();
    JScrollPane splitPane;


    public CreationTwit(TwitupMainView twitupMainView){
        this.setLayout(new GridBagLayout());
        this.twitup=twitupMainView.getTwitup();
        this.twitupMainView = twitupMainView;
        init();
    }


    public void init(){
        GridBagConstraints c = new GridBagConstraints();

        twitLabel = new JLabel("Twit :");
        twitText = new JTextArea();
        twitText.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                return;
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // If we should be ignoring input then set make sure we
                // enforce max character count and remove the newly typed key.
                if (ignoreInput) {
                    twitText.setText(twitText.getText().substring(0, 140));
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                String charsRemaining = " characters remaining";
                int newLen = 0;

                // The key has just been pressed so Swing hasn't updated
                // the text area with the new KeyEvent.
                int currLen = twitText.getText().length();

                // Adjust newLen depending on whether the user just pressed
                // the backspace key or not.
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    newLen = currLen - 1;
                    ignoreInput = false;
                } else
                    newLen = currLen + 1;

                if (newLen < 0)
                    newLen = 0;

                if (newLen == 0)
                    charCntLabel.setText(charMax + " characters maximum!");
                else if (newLen >= 0 && newLen < charMax)
                    charCntLabel.setText((charMax - newLen) + charsRemaining);
                else if (newLen >= charMax) {
                    ignoreInput = true;
                    charCntLabel.setText("0 " + charsRemaining);
                }
            }
        });

        twitText.setBounds(10, 15, 470, 500);
        twitText.setLineWrap(true);
        twitText.setAutoscrolls(true);
        splitPane = new JScrollPane(twitText, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        splitPane.setBounds(new Rectangle(10, 15, 470, 500));
        splitPane.setAutoscrolls(true);

        EnvoiTwit = new JButton("Envoyer Twit");


        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;

        this.add(twitLabel,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 2;

        this.add(splitPane,c);

        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 3;

        this.add(EnvoiTwit,c);

        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 3;
        c.insets = new Insets(0,5,0,0);

        this.add(charCntLabel,c);


        EnvoiTwit.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        invisible();
                        twitupMainView.EnvoiTwit();

                    }
                }
        );

    }

    void invisible(){
        this.setVisible(false);
    }
}
