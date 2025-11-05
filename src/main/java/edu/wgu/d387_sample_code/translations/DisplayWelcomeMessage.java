package edu.wgu.d387_sample_code.translations;

import java.util.Locale;
import java.util.ResourceBundle;

public class DisplayWelcomeMessage implements Runnable {

  Locale locale;

  public DisplayWelcomeMessage(Locale locale) {

    this.locale = locale;
  }

  public String getWelcomeMessage() {

    ResourceBundle bundle = ResourceBundle.getBundle("translation", locale);

    return bundle.getString("welcome");
  }

  @Override
  public void run() {}
}
