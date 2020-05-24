package locationfetcher.location

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class Location {

    String name
    String city
    String zip
    String streetAndNumber
    BigDecimal lat
    BigDecimal lng
    List<String> keywords
    List<OpeningHour> openingHours
}
