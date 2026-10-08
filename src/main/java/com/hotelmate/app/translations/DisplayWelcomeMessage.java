package com.hotelmate.app.translations;

import java.util.Locale;
import java.util.ResourceBundle;

  public class DisplayWelcomeMessage {

  Locale locale;

  public DisplayWelcomeMessage(Locale locale) {

    this.locale = locale;
  }

  public String getWelcomeMessage() {

    ResourceBundle bundle = ResourceBundle.getBundle("translation", locale);

    return bundle.getString("welcome");
  }

}
