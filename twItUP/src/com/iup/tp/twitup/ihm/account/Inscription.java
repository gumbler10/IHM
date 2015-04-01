package com.iup.tp.twitup.ihm.account;

import com.iup.tp.twitup.core.Twitup;
import com.iup.tp.twitup.datamodel.User;
import com.iup.tp.twitup.ihm.TwitupMainView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Romain on 12/03/15.
 */
public class Inscription extends JPanel {

    private Twitup twitup;
    private TwitupMainView twitupMainView;
    private JLabel userTag,userpassword,userName;
    private JTextField userTagField,userNameField;
    private JPasswordField userPasswordField;
    private JButton InscriptionButton;

    public Inscription(TwitupMainView twitupMainView){

        this.setLayout(new GridBagLayout());
        this.twitup=twitupMainView.getTwitup();
        this.twitupMainView = twitupMainView;
        init();
    }

    public void init(){
        GridBagConstraints c = new GridBagConstraints();

        userTag = new JLabel("Tag :");
        userpassword = new JLabel("Password :");
        userName = new JLabel("Name:");
        userTagField = new JTextField();
        userTagField.setPreferredSize(new Dimension(200,30));
        userPasswordField = new JPasswordField();
        userPasswordField.setPreferredSize(new Dimension(200,30));
        userNameField = new JTextField();
        userNameField.setPreferredSize(new Dimension(200,30));

        InscriptionButton = new JButton("Inscription");


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

        this.add(userName,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 3;

        this.add(userNameField,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 2;

        this.add(InscriptionButton,c);

        InscriptionButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("taille user: " + twitup.getmDatabase().getUsers().size());
                        Set<String> followsUserCurrent = new HashSet<String>(0);
                        User currentUser = new User(UUID.randomUUID(),userTagField.getText(),userPasswordField.getText(),userNameField.getText(), followsUserCurrent, "");
                        twitup.getmEntityManager().sendUser(currentUser);
                        invisible();
                            twitupMainView.InscriptionInvisible();

                    }
                }
        );

    }

    void invisible(){
        this.setVisible(false);
    }
}
