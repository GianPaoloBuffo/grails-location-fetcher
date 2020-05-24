package locationfetcher.util


import locationfetcher.location.Location
import locationfetcher.location.OpeningHour

import java.time.DayOfWeek
import java.time.LocalTime

class MockLocation {

    static Location create() {
        def location = new Location()

        location.city = 'Kempen'
        location.keywords = []
        location.lat = 51.3740233
        location.lng = 6.4182039
        location.name = 'OBI Markt Kempen'
        location.streetAndNumber = 'Kleinbahnstraße 32'
        location.zip = '47906'
        location.openingHours = [createOpeningHour()]

        location
    }

    static OpeningHour createOpeningHour() {
        def openingHour = new OpeningHour()

        openingHour.dayOfWeek = DayOfWeek.MONDAY
        openingHour.from1 = LocalTime.of(8, 0)
        openingHour.to1 = LocalTime.of(20, 0)

        openingHour
    }
}
