package locationfetcher.location

import groovy.transform.EqualsAndHashCode

import java.time.DayOfWeek
import java.time.LocalTime

@EqualsAndHashCode
class OpeningHour {

    DayOfWeek dayOfWeek
    LocalTime from1
    LocalTime to1
}
