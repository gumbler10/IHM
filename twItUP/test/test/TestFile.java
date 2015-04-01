package test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import junit.framework.TestCase;

import org.junit.Test;

import com.iup.tp.twitup.common.Constants;
import com.iup.tp.twitup.datamodel.Twit;
import com.iup.tp.twitup.datamodel.User;
import com.iup.tp.twitup.datamodel.converter.XmlbeanDatamodelConverter;
import com.iup.tp.twitup.datamodel.jaxb.JaxbReader;
import com.iup.tp.twitup.datamodel.jaxb.JaxbWriter;
import com.iup.tp.twitup.datamodel.jaxb.bean.twit.TwitXml;
import com.iup.tp.twitup.datamodel.jaxb.bean.user.UserXml;

/**
 * Classe de test pour la gestion des fichiers XML.
 * 
 * @author S.Lucas
 */
public class TestFile extends TestCase
{
  private static final String TWIT_TEST_FILE = "testTwit.twt";
  private static final String USER_TEST_FILE = "testUser.usr";

  private static final UUID DUMMY_USER_UUID = UUID.randomUUID();
  private static final UUID DUMMY_TWIT_UUID = UUID.randomUUID();
  private static final long DUMMY_TWIT_TIMESTAMP = System.currentTimeMillis();

  /**
   * Retourne un twit fictif pour les tests.
   */
  private Twit getDummyTwit()
  {
    User dummyUser = this.getDummyUser();
    Twit dumyTwit = new Twit(DUMMY_TWIT_UUID, dummyUser, DUMMY_TWIT_TIMESTAMP,
        "plop @user1 test test #tag1 ttt #tag1 ttt #tag2");
    return dumyTwit;
  }

  /**
   * Retourne un utilisateur fictif pour les tests.
   */
  private User getDummyUser()
  {
    Set<String> follows = new HashSet<String>();
    follows.add("f1");
    User dummyUser = new User(DUMMY_USER_UUID, "USER", "ttt", "DummyUser", follows, "pathTo");
    return dummyUser;
  }

  /**
   * Retourne l'utilisateur inconnu.
   */
  private User getUnknownUser()
  {
    Set<String> follows = new HashSet<String>();
    return new User(Constants.UNKNONWN_USER_UUID, "", "--", "<inconnu>", follows, "");
  }

  /**
   * Retourne un map d'utilisateur fictive.
   */
  private Map<UUID, User> getUserMap()
  {
    Map<UUID, User> userMap = new HashMap<UUID, User>();
    userMap.put(DUMMY_USER_UUID, this.getDummyUser());
    userMap.put(Constants.UNKNONWN_USER_UUID, this.getUnknownUser());
    return userMap;
  }

  /**
   * Test la validité du twit par rapport au twit de référence.
   * 
   * @param twitToCheck
   *          , Twit à vérifier.
   */
  private void checkDummyTwitValidity(Twit twitToCheck)
  {
    assertTrue(twitToCheck.getUuid().equals(DUMMY_TWIT_UUID));
    assertTrue(twitToCheck.getTwiter().equals(this.getDummyUser()));
    assertTrue(twitToCheck.getTags().size() == 2);
    assertTrue(twitToCheck.getTags().contains("tag1"));
    assertTrue(twitToCheck.getTags().contains("tag2"));
    assertTrue(twitToCheck.getUserTags().size() == 1);
    assertTrue(twitToCheck.getUserTags().contains("user1"));
  }

  /**
   * Test la validité de l'utilisateur par rapport au twit de référence.
   * 
   * @param twitToCheck
   *          , Twit à vérifier.
   */
  private void checkDummyUserValidity(User userToCheck)
  {
    assertTrue(userToCheck.getUuid().equals(DUMMY_USER_UUID));
    assertTrue(userToCheck.getUserTag().equals("USER"));
    assertTrue(userToCheck.getUserPassword().equals("ttt"));
    assertTrue(userToCheck.getName().equals("DummyUser"));
    assertTrue(userToCheck.getAvatarPath().equals("pathTo"));
    assertTrue(userToCheck.getFollows().size() == 1);
    assertTrue(userToCheck.getFollows().contains("f1"));
  }

  /**
   * Test la gestion du modèle des Twit.
   * 
   * @param args
   */
  @Test
  public void test0101TwitModel()
  {
    Twit dummyTwit = this.getDummyTwit();

    // Test de validité
    this.checkDummyTwitValidity(dummyTwit);
  }

  /**
   * Test de l'écriture des fichiers XML des Twit.
   */
  @Test
  public void test0102TwitWrite()
  {
    // Création et conversion des objets
    Twit dummyTwit = this.getDummyTwit();
    TwitXml xmlTwit = XmlbeanDatamodelConverter.convertAsXmlTwit(dummyTwit);

    // Génération du fichier
    boolean generated = JaxbWriter.writeTwitFile(xmlTwit, TWIT_TEST_FILE);

    assertTrue(generated);
  }

  /**
   * Test de lecture des fichiers XML des Twit.
   */
  @Test
  public void test0103TwitRead()
  {
    // Lecture du fichier
    TwitXml xmlTwit = JaxbReader.readTwit(TWIT_TEST_FILE);
    assertNotNull(xmlTwit);

    // Conversion du bean XML
    Twit twit = XmlbeanDatamodelConverter.convertAsModelTwit(xmlTwit, this.getUserMap());

    // Vérification de la validité
    this.checkDummyTwitValidity(twit);
  }

  /**
   * Test la gestion du modèle des utilisateurs.
   * 
   * @param args
   */
  @Test
  public void test0201UserModel()
  {
    User dummyUser = this.getDummyUser();

    // Test de validité
    this.checkDummyUserValidity(dummyUser);
  }

  /**
   * Test de l'écriture des fichiers XML des utilisateurs.
   */
  @Test
  public void test0202UserWrite()
  {
    // Création et conversion des objets
    User dummyUser = this.getDummyUser();
    UserXml xmlUser = XmlbeanDatamodelConverter.convertAsXmlUser(dummyUser);

    // Génération du fichier
    boolean generated = JaxbWriter.writeUserFile(xmlUser, USER_TEST_FILE);

    assertTrue(generated);
  }

  /**
   * Test de lecture des fichiers XML des utilisateurs.
   */
  @Test
  public void test0203UserRead()
  {
    // Lecture du fichier
    UserXml xmlUser = JaxbReader.readUser(USER_TEST_FILE);
    assertNotNull(xmlUser);

    // Conversion du bean XML
    User user = XmlbeanDatamodelConverter.convertAsModelUser(xmlUser);

    // Vérification de la validité
    this.checkDummyUserValidity(user);
  }
}
