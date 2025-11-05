package edu.wgu.d387_sample_code;

import edu.wgu.d387_sample_code.translations.DisplayWelcomeMessage;
import java.util.Locale;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class D387SampleCodeApplication {

  public static void main(String[] args) {
    SpringApplication.run(D387SampleCodeApplication.class, args);

    DisplayWelcomeMessage displayWelcomeMessageEnglish = new DisplayWelcomeMessage(Locale.US);

    Thread englishMessage = new Thread(displayWelcomeMessageEnglish);

    englishMessage.start();

    DisplayWelcomeMessage displayWelcomemessageFrench =
        new DisplayWelcomeMessage((Locale.CANADA_FRENCH));

    Thread frenchmessage = new Thread(displayWelcomemessageFrench);

    frenchmessage.start();
  }
}
