package com.iup.tp.twitup.ihm;

import com.iup.tp.twitup.common.PropertiesManager;
import com.iup.tp.twitup.core.Twitup;
import com.iup.tp.twitup.ihm.account.Connexion;
import com.iup.tp.twitup.ihm.account.Inscription;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Properties;

/**
 * Classe de la vue principale de l'application.
 */
public class TwitupMainView
{
    Twitup twitup;
    JFrame frame;
    ImageIcon logo20 = new ImageIcon("/Users/Romain/Desktop/IHM/twItUP/src/resources/images/logoIUP_20.jpg");
    ImageIcon logo50 = new ImageIcon("/Users/Romain/Desktop/IHM/twItUP/src/resources/images/logoIUP_50.jpg");
    ImageIcon exit20 = new ImageIcon("/Users/Romain/Desktop/IHM/twItUP/src/resources/images/exitIcon_20.png");
    JMenuBar menuBar = new JMenuBar();
    JMenu fichier, interrogation;
    JMenuItem quitter,importer,aPropos;
    Container mainPanel;



    Properties propertiesLF = PropertiesManager.loadProperties("/Users/Romain/Desktop/IHM/twItUP/src/resources/configuration.properties");

    public TwitupMainView(Twitup twitup) {
        this.twitup=twitup;
        init();
    }

    public void initMenu(){


        frame = new JFrame("Twit'Heure");
        mainPanel = frame.getContentPane();
        frame.setIconImage(logo20.getImage());
        frame.setSize(1000,600);


//Build the first menu.
        fichier = new JMenu("Fichier");
        menuBar.add(fichier);

        quitter = new JMenuItem("Quitter",exit20);
        fichier.add(quitter);

        quitter.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                }
        );

        importer = new JMenuItem("Import");
        fichier.add(importer);

        interrogation = new JMenu("?");
        menuBar.add(interrogation);

        aPropos = new JMenuItem("A Propos",logo20);
        interrogation.add(aPropos);

        aPropos.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(frame, "UBO M2-TIIL 2015\n Département Informatique",
                                "A Propos",JOptionPane.INFORMATION_MESSAGE,logo50);
                    }
                }
        );

        importer.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                       initShowBox();
                    }
                }
        );

        frame.setJMenuBar(menuBar);

    }

        public void initShowBox(){
            Boolean valider = false;

                while(!valider) {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    chooser.setCurrentDirectory(new File(propertiesLF.getProperty("EXCHANGE_DIRECTORY")));


                    int returnVal = chooser.showOpenDialog(frame);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        valider = true;
                        propertiesLF.setProperty("EXCHANGE_DIRECTORY", chooser.getSelectedFile().getPath());
                        PropertiesManager.writeProperties(propertiesLF, "/Users/Romain/Desktop/IHM/twItUP/src/resources/configuration.properties");
                    }
                }

        }

    public void accueil(){
        mainPanel.add(new Accueil(this));
    }


    public void InscriptionInvisible(){
        mainPanel.add(new CreationTwit(this));
    }

    public void EnvoiTwit(){
        mainPanel.add(new Connexion(this));
    }

    private void init(){

        initMenu();

        mainPanel.add(new Connexion(this));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public Twitup getTwitup() {
        return twitup;
    }

    public void setTwitup(Twitup twitup) {
        this.twitup = twitup;
    }
}

 /*      menuItem = new JMenuItem("Both text and icon");
        menuItem.setMnemonic(KeyEvent.VK_B);
        menu.add(menuItem);

        menuItem = new JMenuItem(new ImageIcon("images/middle.gif"));
        menuItem.setMnemonic(KeyEvent.VK_D);
        menu.add(menuItem);

//a group of radio button menu items
        menu.addSeparator();
        ButtonGroup group = new ButtonGroup();
        rbMenuItem = new JRadioButtonMenuItem("A radio button menu item");
        rbMenuItem.setSelected(true);
        rbMenuItem.setMnemonic(KeyEvent.VK_R);
        group.add(rbMenuItem);
        menu.add(rbMenuItem);

        rbMenuItem = new JRadioButtonMenuItem("Another one");
        rbMenuItem.setMnemonic(KeyEvent.VK_O);
        group.add(rbMenuItem);
        menu.add(rbMenuItem);

//a group of check box menu items
        menu.addSeparator();
        cbMenuItem = new JCheckBoxMenuItem("A check box menu item");
        cbMenuItem.setMnemonic(KeyEvent.VK_C);
        menu.add(cbMenuItem);

        cbMenuItem = new JCheckBoxMenuItem("Another one");
        cbMenuItem.setMnemonic(KeyEvent.VK_H);
        menu.add(cbMenuItem);

//a submenu
        menu.addSeparator();
        submenu = new JMenu("A submenu");
        submenu.setMnemonic(KeyEvent.VK_S);

        menuItem = new JMenuItem("An item in the submenu");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_2, ActionEvent.ALT_MASK));
        submenu.add(menuItem);

        menuItem = new JMenuItem("Another item");
        submenu.add(menuItem);
        menu.add(submenu);

//Build second menu in the menu bar.
        menu = new JMenu("Another Menu");
        menu.setMnemonic(KeyEvent.VK_N);
        menu.getAccessibleContext().setAccessibleDescription(
                "This menu does nothing");
        menuBar.add(menu); */
