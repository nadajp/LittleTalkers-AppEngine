package com.nadajp.littletalkers.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class LittleTalkersApp implements EntryPoint, ValueChangeHandler<String>
{
   /**
    * This is the entry point method.
    */
   @Override
   public void onModuleLoad()
   {        
      AppBundle bundle = GWT.create(AppBundle.class);
      
      HorizontalPanel navigation_bar = new HorizontalPanel();
      RootPanel.get("top_menu").add(navigation_bar);
      navigation_bar.setStyleName("top_menu");
      navigation_bar.setSize("100%", "60px");
      
      Image logo = new Image(bundle.logo());
      logo.setStyleName("logo");
      navigation_bar.add(logo);     
      navigation_bar.setCellHeight(logo, "50px");
      navigation_bar.setCellWidth(logo, "39px");
      navigation_bar.setCellVerticalAlignment(logo, HasVerticalAlignment.ALIGN_MIDDLE);
      
      InlineHyperlink nlnhprlnkLittleTalkers = new InlineHyperlink("Little Talkers", false, "home");
      navigation_bar.add(nlnhprlnkLittleTalkers);
      navigation_bar.setCellVerticalAlignment(nlnhprlnkLittleTalkers, HasVerticalAlignment.ALIGN_MIDDLE);
      nlnhprlnkLittleTalkers.setSize("224px", "26px");
      nlnhprlnkLittleTalkers.addStyleName("title");
      
      String shareButtons = 
            "<span class='st_sharethis_large' displayText='ShareThis'></span>" +
            "<span class='st_facebook_large' displayText='Facebook'></span>" +
            "<span class='st_twitter_large' displayText='Tweet'></span>" +
            "<span class='st_linkedin_large' displayText='LinkedIn'></span>" +
            "<span class='st_pinterest_large' displayText='Pinterest'></span>" +
            "<span class='st_email_large' displayText='Email'></span>";
   
      HTML htmlShare = new HTML(shareButtons);
      navigation_bar.add(htmlShare);
      navigation_bar.setCellVerticalAlignment(htmlShare, HasVerticalAlignment.ALIGN_MIDDLE);
      navigation_bar.setCellHorizontalAlignment(htmlShare, HasHorizontalAlignment.ALIGN_CENTER);
          
      Hyperlink hprlnkHelp = new Hyperlink("HELP", false, "help");
      hprlnkHelp.addStyleName("myMenuLink");
      navigation_bar.setCellVerticalAlignment(hprlnkHelp, HasVerticalAlignment.ALIGN_MIDDLE);
      navigation_bar.setCellHorizontalAlignment(hprlnkHelp, HasHorizontalAlignment.ALIGN_RIGHT);
      navigation_bar.add(hprlnkHelp);
      
      Hyperlink hprlnkPrivacy = new Hyperlink("PRIVACY", false, "privacy");
      hprlnkPrivacy.addStyleName("myMenuLink");
      navigation_bar.add(hprlnkPrivacy);
      
      HorizontalPanel banner = new HorizontalPanel();
      banner.setStyleName("banner");
      banner.setWidth("100%");
      RootPanel.get("banner").add(banner);
      
      Image welcome = new Image(bundle.welcome());
      banner.add(welcome);
      welcome.setStyleName("welcome-image");
      
      VerticalPanel verticalPanel = new VerticalPanel();
      banner.add(verticalPanel);
      verticalPanel.setWidth("100%");
         
      String html = new String("<h2>Never forget your child's first words and expressions with Little Talkers!</h2>" +
            "<h3>Little Talkers is a mobile app for recording your child's first words or expressions, questions and answers. " +
            "It allows users to write out the phrases as well as make audio recordings of the little ones.</h3>");
      
      HTML featureText = new HTML(html, true);
      verticalPanel.add(featureText);
      featureText.setStyleName("featureTitle");
      featureText.setSize("90%", "");
      
      Image googlePlay = new Image(bundle.googlePlay());
      googlePlay.setStyleName("google_play");
      googlePlay.getElement().getStyle().setCursor(Cursor.POINTER); 
      googlePlay.addClickHandler(new ClickHandler() {
        public void onClick(ClickEvent event) {
          Window.Location.assign("https://play.google.com/store/apps/details?id=com.nadajp.littletalkers");
        }
      });
      verticalPanel.add(googlePlay);
      verticalPanel.setCellVerticalAlignment(googlePlay, HasVerticalAlignment.ALIGN_BOTTOM);
      verticalPanel.setCellHorizontalAlignment(googlePlay, HasHorizontalAlignment.ALIGN_CENTER);
         
      FlexTable table_footer = new FlexTable();
      table_footer.setCellSpacing(20);
      table_footer.setStyleName("footer");
      RootPanel.get("footer").add(table_footer);
      table_footer.setWidth("100%");
      
      Label lblCredits = new Label("CREDITS");
      lblCredits.setStyleName("heading");

      table_footer.setWidget(0, 0, lblCredits);
      table_footer.setText(1, 0, "Blue Cave Holdings, LLC");
      table_footer.setText(2, 0, "Development: Nada Jaksic Pivcevic");
      table_footer.setText(3, 0, "App Design: Mathieu Marée");
      
      Label lblContactUs = new Label("CONTACT US");
      lblContactUs.setStyleName("heading");
      table_footer.setWidget(0, 1, lblContactUs);
      Anchor contactEmail = new Anchor("bluecaveholdings@gmail.com", "mailto:bluecaveholdings@gmail.com");
      table_footer.setWidget(1, 1, contactEmail);
   
      RootPanel.get("content").add(createContent("home"));
      // If the application starts with no history token, start it off in the
      // 'home' state.
      String initToken = History.getToken();
      if (initToken.length() == 0)
        initToken = "home";
      
      // Add history listener
      History.addValueChangeHandler(this);

      // Now that we've setup our listener, fire the initial history state.
      History.fireCurrentHistoryState();
      /*+
                               "<li>See your child's progress by clicking on the word details page, where there is a list of all previous versions of the particular word, " +
                               "sorted by date.</li></ul>");*/
   }

   @Override
   public void onValueChange(ValueChangeEvent<String> event)
   {
      RootPanel.get("content").clear();
      RootPanel.get("content").add(createContent(event.getValue()));
   }

   public Widget createContent(String token)
   {
      AppBundle bundle = GWT.create(AppBundle.class);
      if (token.equals("help"))
      {
         VerticalPanel vertical = new VerticalPanel();
         RootPanel.get("content").add(vertical);

         Image help1 = new Image(bundle.help());
         help1.setStyleName("help");
         vertical.add(help1);
         vertical.setCellHorizontalAlignment(help1,
               HasHorizontalAlignment.ALIGN_CENTER);
         return vertical;
      } 
      else if (token.equals("privacy"))
      {     
         String strPrivacy = "<h1>PRIVACY POLICY</h1>"
               + "<p>This privacy policy governs your use of a software application Little Talkers (\"Application\") on a mobile device "
               + "that was created by Blue Cave Holdings.  The Application is used for recording children's words, expressions, questions "
               + "and answers. It allows users to write out the phrases as well as make audio recordings of the children.</p>"
               + "<h2>What information does the Application obtain and how is it used?</h2>"
               + "<h3>User Provided Information</h3>"
               + "<p>The Application obtains the information you provide when you download the Application, such as your name and email address.  "
               + "Personal Data may be freely provided by the User, including names and birth dates of children. At this time, there is no "
               + "registration and thus no user-entered data is collected by Blue Cave Holdings. Please check for changes to this privacy policy "
               + "with each new release, as optional registration may become available.  Users are responsible for any Personal Data of third parties "
               + "obtained, published or shared through this Application and confirm that they have the third party's consent to provide the Data to "
               + "the Owner.</p>"
               + "<h3>Automatically Collected Information</h3>"
               + "<p>In addition, the Application may collect certain anonymous information automatically, such as the type of mobile device "
               + "you use, your mobile devices unique device ID, the IP address of your mobile device and your mobile operating system.</p>"
               + "<h2>Does the Application collect precise real time location information of the device?</h2>"
               + "<p>This Application does not collect precise information about the location of your mobile device.</p>"
               + "<h2>Do third parties see and/or have access to information obtained by the Application?</h2>"
               + "<p>Not at this time, as user-entered data is not being collected by Blue Cave Holdings.</p>"
               + "<h2>What are my opt-out rights?</h2>"
               + "<p>You can stop all collection of information by the Application easily by uninstalling the Application. You may use the "
               + "standard uninstall processes as may be available.</p>"
               + "<h2>Children</h2>"
               + "<p>The Application is intended for use by parents/guardians to record information about their children. We do not use "
               + "the Application to knowingly solicit data from or market to children under the age of 13. If a parent or guardian becomes "
               + "aware that his or her child has provided us with information without their consent, he or she should contact us at "
               + "<a href='mailto:bluecaveholdings@gmail.com'>bluecaveholdings@gmail.com</a>. We will delete such information from our files within a reasonable time.</p>"
               + "<h2>Changes</h2>"
               + "<p>We will notify you of any changes to our Privacy Policy by posting the new Privacy Policy here "
               + "littletalkersapp.appspot.com/privacy. You are advised to consult this Privacy Policy regularly for any changes.</p>"
               + "<h2>Your Consent</h2>"
               + "<p>By using the Application, you are consenting to our processing of User Provided and Automatically Collected information "
               + "as set forth in this Privacy Policy now and as amended by us. \"Processing\" means using cookies on a computer/hand held device "
               + "or using or touching information in any way, including, but not limited to, collecting, storing, deleting, using, combining "
               + "and disclosing information.</p>"
               + "<h2>Contact us</h2>"
               + "<p>If you have any questions regarding privacy while using the Application, or have questions about our practices, "
               + "please contact us via email at <a href='mailto:bluecaveholdings@gmail.com'>bluecaveholdings@gmail.com</a>.</p>";
         HTML privacyText = new HTML(strPrivacy); 
         privacyText.setWidth("80%");
         privacyText.setStyleName("privacy");
         RootPanel.get("content").add(privacyText);
         return privacyText;         
      }
      else
      {            
         FlexTable table_features = new FlexTable();
         RootPanel.get("content").add(table_features);
         table_features.setCellSpacing(20);
         table_features.setWidth("100%");

         Image iconPlus = new Image(bundle.plus());
         table_features.setWidget(0, 0, iconPlus);
         String html = new String(
               "Enter an expression or question and answer. "
                     + "Add details such as date, location, language, actual meaning, to whom the child was speaking, and more.");
         table_features.setText(0, 1, html);

         Image iconMic = new Image(bundle.blueMic());
         table_features.setWidget(1, 0, iconMic);
         html = new String("Audio record your child speaking the phrase.");
         table_features.setText(1, 1, html);

         Image iconDictionary = new Image(bundle.dictionary());
         table_features.setWidget(2, 0, iconDictionary);
         html = new String(
               "View all your entries in a dictionary, where you can sort by phrase or date and listen to audio.");
         table_features.setText(2, 1, html);

         Image iconAddKid = new Image(bundle.add());
         table_features.setWidget(3, 0, iconAddKid);
         html = new String(
               "Add multiple children, then easily switch between them using an easy drop down navigation menu");
         table_features.setText(3, 1, html);

         Image iconShare = new Image(bundle.share());
         table_features.setWidget(4, 0, iconShare);
         html = new String(
               "Share your child's expressions with others using a variety of apps");
         table_features.setText(4, 1, html);

         Image iconExport = new Image(bundle.export());
         table_features.setWidget(5, 0, iconExport);
         html = new String(
               "Export your data as a CSV file, then email to yourself or your family for easy viewing in a spreadsheet application");
         table_features.setText(5, 1, html);

         Image screenshot = new Image(bundle.screenshot());
         table_features.setWidget(0, 2, screenshot);
         table_features.getFlexCellFormatter().setRowSpan(0, 2, 6);
         table_features.getCellFormatter().setHorizontalAlignment(0, 2,
               HasHorizontalAlignment.ALIGN_CENTER);
         table_features.getCellFormatter().setVerticalAlignment(0, 2,
               HasVerticalAlignment.ALIGN_TOP);

         return table_features;
      }
   }
}