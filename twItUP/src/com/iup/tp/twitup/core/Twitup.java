package com.iup.tp.twitup.core;

import java.io.File;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import com.iup.tp.twitup.common.PropertiesManager;
import com.iup.tp.twitup.datamodel.Database;
import com.iup.tp.twitup.datamodel.IDatabase;
import com.iup.tp.twitup.datamodel.Twit;
import com.iup.tp.twitup.datamodel.User;
import com.iup.tp.twitup.events.file.IWatchableDirectory;
import com.iup.tp.twitup.events.file.WatchableDirectory;
import com.iup.tp.twitup.ihm.TwitupMainView;
import com.iup.tp.twitup.ihm.TwitupMock;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Classe principale l'application.
 * 
 * @author S.Lucas
 */
public class Twitup
{
  /**
   * Base de données.
   */
  protected IDatabase mDatabase;

  /**
   * Gestionnaire des entités contenu de la base de données.
   */
  protected EntityManager mEntityManager;

  /**
   * Vue principale de l'application.
   */
  protected TwitupMainView mMainView;

  /**
   * Classe de surveillance de répertoire
   */
  protected IWatchableDirectory mWatchableDirectory;

  /**
   * Répertoire d'échange de l'application.
   */
  protected String mExchangeDirectoryPath;

  /**
   * Idnique si le mode bouchoné est activé.
   */
  protected boolean mIsMockEnabled = false;

  /**
   * Nom de la classe de l'UI.
   */
  protected String mUiClassName;

  protected Properties propertiesLF = PropertiesManager.loadProperties("/Users/Romain/Desktop/IHM/twItUP/src/resources/configuration.properties");

    /**
   * Constructeur.
   */
  public Twitup()
  {
    // Init du look and feel de l'application
    this.initLookAndFeel();

    // Initialisation de la base de données
    this.initDatabase();

    if (this.mIsMockEnabled)
    {
      // Initialisation du bouchon de travail
      this.initMock();
    }

    // Initialisation de l'IHM
    this.initGui();

    // Initialisation du répertoire d'échange
    this.initDirectory();
  }

  /**
   * Initialisation du look and feel de l'application.
   */
  protected void initLookAndFeel()
  {
      try
      {
          UIManager.setLookAndFeel(propertiesLF.getProperty("LOOK_AND_FEEL"));
      } catch(Exception e){

      }
  }

  /**
   * Initialisation de l'interface graphique.
   */
  protected void initGui() {

      this.mMainView = new TwitupMainView(this);

      Set<Twit> lstTwit = this.mDatabase.getTwits();
      System.out.println("Récupère le nombre de twits : " + lstTwit.size());

      Set<String> followsUserCurrent = null;
      User currentUser  = new User(UUID.randomUUID(), "root", "", "Philippe", followsUserCurrent, "");

      this.mEntityManager.sendUser(currentUser);

      System.out.println(this.mDatabase.getUsers().size());

      System.out.println("Ajout d'un premier twit de la part de Philippe.");
      Twit twit1 = new Twit(currentUser, "Hello !!!");
      this.mDatabase.addTwit(twit1);

      System.out.println(this.mDatabase.getUsers().size());


      System.out.println("Ajout d'un second twit de la part de Philippe.");
      Twit twit2 = new Twit(currentUser, "J'améliore mes compétences de jour en jour !!!");
      this.mDatabase.addTwit(twit2);

      System.out.println("Ajout d'un premier twit de la part de Philippe.");
      Twit twit3 = new Twit(currentUser, "Viva l'U.B.O");
      this.mDatabase.addTwit(twit3);

      lstTwit = this.mDatabase.getTwits();
      System.out.println("Récupère le nombre de twits : " + lstTwit.size());
      for (Twit currentTwit : lstTwit) {
          System.out.println("Twit posté par User = " + currentTwit.getTwiter().getName() + " - Text = " + currentTwit.getText());
      }

      System.out.println("Nombre de Twit avant la suppression = " + this.mDatabase.getTwits().size());
      this.mDatabase.removeTwit(twit1);
      System.out.println("Nombre de Twit après la suppression = " + this.mDatabase.getTwits().size());
  }

  /**
   * Initialisation du répertoire d'échange (depuis la conf ou depuis un file chooser). <br/>
   * <b>Le chemin doit obligatoirement avoir été saisi et être valide avant de pouvoir utiliser l'application</b>
   */
  protected void initDirectory()
  {
      Boolean valider = false;

      if(!new File(propertiesLF.getProperty("EXCHANGE_DIRECTORY")).exists()) {

          while(!valider) {
              JFileChooser chooser = new JFileChooser();
              chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
              chooser.setCurrentDirectory(new File(propertiesLF.getProperty("EXCHANGE_DIRECTORY")));


              int returnVal = chooser.showOpenDialog(null);
              if (returnVal == JFileChooser.APPROVE_OPTION) {
                  valider = true;
                  propertiesLF.setProperty("EXCHANGE_DIRECTORY", chooser.getSelectedFile().getPath());
                  PropertiesManager.writeProperties(propertiesLF, "/Users/Romain/Desktop/IHM/twItUP/src/resources/configuration.properties");
              }
          }
      }

      initDirectory(propertiesLF.getProperty("EXCHANGE_DIRECTORY"));
  }

  /**
   * Indique si le fichier donné est valide pour servire de répertoire d'échange
   * 
   * @param directory
   *          , Répertoire à tester.
   */
  protected boolean isValideExchangeDirectory(File directory)
  {
    // Valide si répertoire disponible en lecture et écriture
    return directory != null && directory.exists() && directory.isDirectory() && directory.canRead()
        && directory.canWrite();
  }

  /**
   * Initialisation du mode bouchoné de l'application
   */
  protected void initMock()
  {
    TwitupMock mock = new TwitupMock(this.mDatabase, this.mEntityManager);
    mock.showGUI();
  }

  /**
   * Initialisation de la base de données
   */
  protected void initDatabase()
  {
    mDatabase = new Database();
    mEntityManager = new EntityManager(mDatabase);
  }

  /**
   * Initialisation du répertoire d'échange.
   * 
   * @param directoryPath
   */
  protected void initDirectory(String directoryPath)
  {
    mExchangeDirectoryPath = directoryPath;
    mWatchableDirectory = new WatchableDirectory(directoryPath);
    mEntityManager.setExchangeDirectory(directoryPath);

    mWatchableDirectory.initWatching();
    mWatchableDirectory.addObserver(mEntityManager);
  }

    public EntityManager getmEntityManager() {
        return mEntityManager;
    }

    public void setmEntityManager(EntityManager mEntityManager) {
        this.mEntityManager = mEntityManager;
    }

    public IDatabase getmDatabase() {
        return mDatabase;
    }

    public void setmDatabase(IDatabase mDatabase) {
        this.mDatabase = mDatabase;
    }
}
