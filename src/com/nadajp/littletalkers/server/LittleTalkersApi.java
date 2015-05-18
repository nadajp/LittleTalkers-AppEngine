package com.nadajp.littletalkers.server;

import static com.nadajp.littletalkers.service.OfyService.ofy;
import static com.nadajp.littletalkers.service.OfyService.factory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.VoidWork;

@Api(name = "littletalkersapi", version = "v1", scopes = { Constants.EMAIL_SCOPE }, clientIds = {
      Constants.WEB_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID,
      Constants.ANDROID_CLIENT_ID }, audiences = { Constants.WEB_CLIENT_ID }, description = "LittleTalkers API", namespace = @ApiNamespace(ownerDomain = "nadajp.com", ownerName = "nadajp.com", packagePath = "littletalkers.server"))
public class LittleTalkersApi
{
   public static class UserDataWrapper
   {
      private List<Kid> mKids;
      private List<Word> mWords;
      
      // Empty default constructor
      public UserDataWrapper()
      { 
      }
      public List<Kid> getKids()
      {
         return mKids;
      }
      public void setKids(List<Kid> kids)
      {
         this.mKids = kids;
      }
      public List<Word> getWords()
      {
         return mWords;
      }
      public void setWords(List<Word> words)
      {
         this.mWords = words;
      }
   }
   
   /**
    * This method gets the entity having primary key id. It uses HTTP GET
    * method.
    *
    * @param id the primary key of the entity
    * @return The entity with primary key id.
    */
   @ApiMethod(name = "getProfileById")
   public UserProfile getProfileById(final User user, @Named("id") Long id)
         throws UnauthorizedException
   {
      if (id == null)
      {
         return getProfile(user);
      }
      if (user == null)
      {
         throw new UnauthorizedException("Authorization required");
      }

      return ofy().load().key(Key.create(UserProfile.class, id)).now();
   }
   
   /**
    * This method gets the entity from email address in the user parameter. 
    * It uses HTTP GET method.
    *
    * @return The entity with email address user.getEmail()
    */
   @ApiMethod(name = "getProfile")
   public UserProfile getProfile(final User user)
         throws UnauthorizedException
   {
      if (user == null)
      {
         throw new UnauthorizedException("Authorization required");
      }
      String email = user.getEmail();
      return ofy().load().type(UserProfile.class).filter("email", email).first().now();
   }
   
   private Long getIdFromEmail(String email)
   {
      UserProfile user = ofy().load().type(UserProfile.class).filter("email", email).first().now();
      return user.getId();
   }

   /**
    * This inserts a new entity into App Engine datastore. If the entity already
    * exists in the datastore, an exception is thrown. It uses HTTP POST method.
    *
    * @param profile
    *           the entity to be inserted.
    * @return The inserted entity.
    */
   @ApiMethod(name = "insertProfile")
   public UserProfile insertProfile(final User user)
         throws UnauthorizedException
   {
      if (user == null)
      {
         throw new UnauthorizedException("Authorization required");
      }
      UserProfile profile = new UserProfile(user.getEmail());    
      if (containsProfile(profile))
      {
         return profile;
      }
      ofy().save().entity(profile).now();
      return profile;
   }

   /**
    * This method is used for updating an existing entity. If the entity does
    * not exist in the datastore, an exception is thrown. It uses HTTP PUT
    * method.
    *
    * @param profile
    *           the entity to be updated.
    * @return The updated entity.
    */
   @ApiMethod(name = "updateProfile")
   public UserProfile updateProfile(UserProfile profile) throws EntityNotFoundException
   {
      return profile;
   }

   /**
    * This method removes the entity with primary key id. It uses HTTP DELETE
    * method.
    *
    * @param id
    *           the primary key of the entity to be deleted.
    */
   @ApiMethod(name = "removeProfile")
   public void removeProfile(@Named("id") String id)
   {

   }

   private boolean containsProfile(UserProfile profile)
   {
      if (profile.getId() == null)
      {
         return false;
      }
      profile = ofy().load().key(Key.create(UserProfile.class, profile.getId()))
            .now();
      if (profile == null)
      {
         return false;
      }
      return true;
   }

   /**
    * This inserts a new entity into App Engine datastore. If the entity already
    * exists in the datastore, an exception is thrown. It uses HTTP POST method.
    *
    * @param kid the entity to be inserted.
    * @return The inserted entity.
    */
   @ApiMethod(name = "insertKid")
   public Kid insertKid(User user, Kid kid) throws UnauthorizedException
   {
      if (user == null)
      {
         throw new UnauthorizedException("Authorization required");
      }
      ofy().save().entity(kid).now();
      return kid;
   }


   /**
    * This method gets the entity having primary key id. It uses HTTP GET
    * method.
    *
    * @param userId: id of the user (parent)
    * @param id: id of the child
    * @return The entity with primary key id.
    */
   @ApiMethod(name = "getKid")
   public Kid getKid(User user, @Named("userId") Long userId, @Named("id") Long id) throws UnauthorizedException
   {
      if (user == null)
      {
         throw new UnauthorizedException("Authorization required");
      }
      Key<UserProfile> parentKey = Key.create(UserProfile.class, userId);
      Kid kid = ofy().load().type(Kid.class).parent(parentKey).id(id).now();
      return kid;
   }
   
   /**
    * This inserts a new entity into App Engine datastore. If the entity already
    * exists in the datastore, an exception is thrown. It uses HTTP POST method.
    *
    * @param list
    *           of kid entities to be inserted.
    * @return The inserted entities.
    */
   @ApiMethod(name = "insertUserData")
   public UserDataWrapper insertUserData(User user, @Named("userId") Long userId, final UserDataWrapper data)
         throws UnauthorizedException
   {
      if (user == null)
      {
         throw new UnauthorizedException("Authorization required");
      }
      Key<UserProfile> parentKey = Key.create(UserProfile.class, userId);

      List<Kid> kids = data.getKids();
      for (Kid kid : kids)
      {
         kid.setParentId(userId);
         //kid.setParentKey(parentKey);
      }
      List<Word> words = data.getWords();
      for (Word word : words)
      {
         word.setParentId(userId);
      }
      //ofy().transact(new VoidWork() 
     // {
       //  public void vrun() 
       //  {
             ofy().save().entities(kids).now();
             ofy().save().entities(words).now();
       //  }
      // });
            
      //Queries within transactions require ancestor()
      words = ofy().load().type(Word.class).ancestor(parentKey).list();
      kids = ofy().load().type(Kid.class).ancestor(parentKey).list();
      
      UserDataWrapper result = new UserDataWrapper();
      result.setWords(words);
      result.setKids(kids);
      
      return result;
   }
   

   /**
    * This inserts a new entity into App Engine datastore. If the entity already
    * exists in the datastore, an exception is thrown. It uses HTTP POST method.
    *
    * @param list of kid entities to be inserted.
    * @return The inserted entities.
    */
   @ApiMethod(name = "getWordsForKid")
   public List<Word> getWordsForKid(User user, @Named("userId") Long userId, 
         @Named("kidId") long kidId)
         throws UnauthorizedException
   {
      if (user == null)
      {
         throw new UnauthorizedException("Authorization required");
      }
      Key<UserProfile> parent = Key.create(UserProfile.class, userId);
      List<Word> words = ofy().load().type(Word.class).ancestor(parent).filter("kidId", kidId).list();
      return words;
   }

   /**
    * This inserts a word into App Engine datastore. If the entity already
    * exists in the datastore, an exception is thrown. It uses HTTP POST method.
    *
    * @param user id
    * @param word to be inserted
    * @return The inserted entity
    */
   @ApiMethod(name = "insertWord")
   public Word insertWord(User user, @Named("userId") Long userId, 
         final Word word)
         throws UnauthorizedException
   {
      if (user == null)
      {
         throw new UnauthorizedException("Authorization required");
      }
      word.setParentId(userId);
      ofy().save().entity(word).now();
      return word;
   }
   
   /**
    * This retrieves a word from datastore by userId, kidId and word. 
    *
    * @param userId
    * @param kidId
    * @param word
    * @return The word entity meeting the criteria
    */
   @ApiMethod(name = "getWordDetails")
   public Word getWordDetails(User user, @Named("userId") Long userId, 
         @Named("kidId") Long kidId,
         @Named("word") String word)
         throws UnauthorizedException
   {
      if (user == null)
      {
         throw new UnauthorizedException("Authorization required");
      }
      Key<UserProfile> parent = Key.create(UserProfile.class, userId);
      Word result =  ofy().load().type(Word.class).ancestor(parent).filter("kidId", kidId).filter("word", word).first().now();
      return result;
   }

   
   /**
    * This method is used for updating an existing entity. If the entity does
    * not exist in the datastore, an exception is thrown. It uses HTTP PUT
    * method.
    *
    * @param kid - the entity to be updated.
    * @return The updated entity.
    */
   @ApiMethod(name = "updateKid", httpMethod = HttpMethod.PUT)
   public Kid updateKid(Kid kid)
   {

      return kid;
   }

   /**
    * This method removes the entity with primary key id. It uses HTTP DELETE
    * method.
    *
    * @param id
    *           the primary key of the entity to be deleted.
    */
   @ApiMethod(name = "removeKid")
   public void removeKid(@Named("id") Long id)
   {

   }

   private boolean containsKid(Kid kid)
   {

      return false;
   }
}
