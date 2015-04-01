package com.iup.tp.twitup.ihm;

import com.iup.tp.twitup.core.Twitup;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Romain on 01/04/2015.
 */
public class Accueil extends JPanel {

    Twitup twitup;
    TwitupMainView twitupMainView;


    public Accueil(TwitupMainView twitupMainView) {
        this.setLayout(new GridBagLayout());
        this.twitup = twitupMainView.getTwitup();
        this.twitupMainView = twitupMainView;
        init();
    }

    void init(){

    }

}