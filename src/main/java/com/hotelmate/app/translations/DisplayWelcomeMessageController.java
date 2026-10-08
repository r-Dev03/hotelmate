package com.hotelmate.app.translations;

import java.util.Locale;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class DisplayWelcomeMessageController {

  @GetMapping("/welcome")
  public ResponseEntity<String> showWelcome(@RequestParam("lang") String lang) {

    Locale locale = Locale.forLanguageTag(lang);

    DisplayWelcomeMessage message = new DisplayWelcomeMessage(locale);

    return new ResponseEntity<String>(message.getWelcomeMessage(), HttpStatus.OK);
  }
}
