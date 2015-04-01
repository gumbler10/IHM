package com.iup.tp.twitup.ihm.account;

import com.iup.tp.twitup.core.Twitup;
import com.iup.tp.twitup.datamodel.User;
import com.iup.tp.twitup.ihm.TwitupMainView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/**
 * Created by Romain on 12/03/15.
 */
public class Connexion extends JPanel {

    private Twitup twitup;
    private TwitupMainView twitupMainView;
    private JLabel userTag,userpassword,erreur;
    private JTextField userTagField;
    private JPasswordField userPasswordField;
    private JButton connexion;

    public Connexion(TwitupMainView twitupMainView){
        this.setLayout(new GridBagLayout());
        this.twitup=twitupMainView.getTwitup();
        this.twitupMainView = twitupMainView;
        init();
    }


    public void init(){
        GridBagConstraints c = new GridBagConstraints();

        userTag = new JLabel("Login :");
        userpassword = new JLabel("Password :");
        erreur = new JLabel("Login/Mdp Incorrect");
        userTagField = new JTextField();
        userTagField.setPreferredSize(new Dimension(200,30));
        userPasswordField = new JPasswordField();
        userPasswordField.setPreferredSize(new Dimension(200,30));
        connexion = new JButton("Connexion");

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;

        this.add(erreur, c);
        erreur.setVisible(false);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;

        this.add(userTag,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 2;

        this.add(userpassword,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 1;

        this.add(userTagField,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 2;

        this.add(userPasswordField,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 2;

        this.add(connexion, c);

        userPasswordField.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    connexion();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        userTag.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    connexion();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        connexion.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        connexion();
                    }
                }
        );
    }

    void connexion() {
        boolean trouver = false;
        for (User u : twitup.getmEntityManager().getmDatabase().getUsers()) {
            if (userTagField.getText().equals(u.getUserTag()) && userPasswordField.getText().equals(u.getUserPassword()))
                trouver = true;
        }

        if (trouver) {
            erreur.setVisible(false);
            this.setVisible(false);
            twitupMainView.accueil();
        }else{
            erreur.setVisible(true);
        }
    }
}
