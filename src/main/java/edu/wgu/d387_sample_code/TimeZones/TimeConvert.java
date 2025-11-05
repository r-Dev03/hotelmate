package edu.wgu.d387_sample_code.TimeZones;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public class TimeConvert {

  public static String getTime() {

    ZonedDateTime time = ZonedDateTime.now();

    ZonedDateTime ET = time.withZoneSameInstant(ZoneId.of("America/New_York"));

    ZonedDateTime MT = time.withZoneSameInstant(ZoneId.of("America/Denver"));

    ZonedDateTime UTC = time.withZoneSameInstant(ZoneId.of("UTC"));
    String timePattern = "hh:mm a";

    DateTimeFormatter timePatternFormatter = DateTimeFormatter.ofPattern(timePattern);

    String displayTimes =
        ET.format(timePatternFormatter)
            + " ET / "
            + MT.format(timePatternFormatter)
            + " MT / "
            + UTC.format(timePatternFormatter)
            + " UTC";

    return displayTimes;
  }
}
