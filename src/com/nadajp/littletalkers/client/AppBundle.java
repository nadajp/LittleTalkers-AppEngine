package com.nadajp.littletalkers.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface AppBundle extends ClientBundle {

   @Source("images/ic_plus.png")
   ImageResource plus();
   
   @Source("images/en_generic_rgb_wo_60.png")
   ImageResource googlePlay();
   
   @Source("images/help.png")
   ImageResource help();
   
   @Source("images/ic_circle_blue_mic.png")
   ImageResource blueMic();
   
   @Source("images/ic_dictionary.png")
   ImageResource dictionary();
   
   @Source("images/ic_menu_invite.png")
   ImageResource add();
   
   @Source("images/ic_menu_share.png")
   ImageResource share();
   
   @Source("images/logo.png")
   ImageResource logo();
   
   @Source("images/screenshot.png")
   ImageResource screenshot();
   
   @Source("images/welcome_web_blue.png")
   ImageResource welcome();
   
   @Source("images/ic_export.png")
   ImageResource export();
   
   public static final AppBundle INSTANCE = GWT.create(AppBundle.class);
}