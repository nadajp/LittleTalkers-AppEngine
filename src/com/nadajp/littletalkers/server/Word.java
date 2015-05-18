package com.nadajp.littletalkers.server;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Word
{
   @Parent
   @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
   private Key<UserProfile> parentKey;
   
   /**
    * Use automatic id assignment for entities of Kid class  
    */
   @Id
   private Long id;     
   /**
    * MySQL database ID of the kid
    */
   @Index
   private long kidId;
   /**
    * The actual phrase
    */
   String word;
   @Index
   String language;
   /**
    * Date in milliseconds
    */
   @Index
   long date;
   String location;
   @Index
   String translation;
   String towhom;
   String notes;
   String audioFileUri;
     
   // Empty constructor
   public Word()
   {}
    
   // constructor
   public Word(String word, String language, long date, String location, 
             String translation, String towhom, String notes, String audioFileUri)
   {
      this.word = word;
      this.language = language;
      this.date = date;
      this.location = location;
      this.translation = translation;
      this.towhom = towhom;
      this.notes = notes;
      this.audioFileUri = audioFileUri;
   }
     
   // getting Id
   public Long getId()
   {
      return this.id;
   }
   
   // setting id
   public void setId(Long id)
   {
      this.id = id;
   }
     
   public long getKidId() 
   {
      return kidId;
   }
   
   public void setKidId(long kidId)
   {
      this.kidId = kidId; 
   }
     
   @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
   public Key<UserProfile> getParentKey() 
   {
      return parentKey;
   }
   
   public void setParentId(long userId)
   {
      this.parentKey = Key.create(UserProfile.class, userId);  
   }
   
   // getting word
   public String getWord()
   {
      return this.word;
   }
     
   // setting word
   public void setWord(String word)
   {
      this.word = word;
   }
   
   // getting date
   public long getDate()
   {
      return this.date;
   }
     
   // setting date
   public void setDate(long date)
   {
      this.date = date;
   }
   
   // getting language
   public String getLanguage()
   {
      return this.language;
   }
     
   // setting language
   public void setLanguage(String language)
   {
      this.language = language;
   }
   
   // getting translation
   public String getTranslation()
   {
      return this.translation;
   }
     
   // setting translation
   public void setTranslation(String translation)
   {
      this.translation = translation;
   }
   
   // getting location
   public String getLocation()
   {
      return this.location;
   }
     
   // setting location
   public void setLocation(String location)
   {
      this.location = location;
   }
   
   // getting towhom
   public String getToWhom()
   {
      return this.towhom;
   }
     
   // setting towhom
   public void setToWhom(String towhom)
   {
      this.towhom = towhom;
   }
   
   // getting notes
   public String getNotes()
   {
      return this.notes;
   }
     
   // setting notes
   public void setNotes(String notes)
   {
      this.notes = notes;
   }
   
   // getting audioFileUri
   public String getAudioFileUri()
   {
      return this.audioFileUri;
   }
     
   // setting audioFileUri
   public void setAudioFileUri(String audioFileUri)
   {
      this.audioFileUri = audioFileUri;
   }
}
