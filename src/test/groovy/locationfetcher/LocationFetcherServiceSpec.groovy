package locationfetcher

import com.stehno.ersatz.ErsatzServer
import com.stehno.ersatz.cfg.ContentType
import com.stehno.ersatz.encdec.Encoders
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

import java.time.DayOfWeek
import java.time.LocalTime

class LocationFetcherServiceSpec extends Specification implements ServiceUnitTest<LocationFetcherService> {

    void "builds a Location from Uberall response"() {
        given:
        def ersatz = new ErsatzServer()
        ersatz.expectations {
            GET('/api/locations') {
                called(1)
                responder {
                    encoder(ContentType.APPLICATION_JSON, Map, Encoders.json)
                    code(200)
                    body([
                            response: [
                                    locations: [
                                            city           : 'Kempen',
                                            keywords       : [],
                                            lat            : 51.3740233,
                                            lng            : 6.4182039,
                                            name           : 'OBI Markt Kempen',
                                            streetAndNumber: 'Kleinbahnstraße 32',
                                            zip            : '47906',
                                            openingHours   : [
                                                    [
                                                            dayOfWeek: convertDayOfWeek(1),
                                                            from1    : "08:00",
                                                            to1      : "20:00"
                                                    ]
                                            ]
                                    ]
                            ]
                    ], ContentType.APPLICATION_JSON)
                }
            }
        }
        service.setupHttpClient(ersatz.httpUrl)

        when:
        def location = service.locations([max: 1])[0]

        then:
        location

        location.city == 'Kempen'
        location.keywords == []
        location.lat == 51.3740233
        location.lng == 6.4182039
        location.name == 'OBI Markt Kempen'
        location.streetAndNumber == 'Kleinbahnstraße 32'
        location.zip == '47906'
        location.openingHours[0].dayOfWeek == DayOfWeek.MONDAY
        location.openingHours[0].from1 == LocalTime.of(8, 0)
        location.openingHours[0].to1 == LocalTime.of(20, 0)

        and:
        ersatz.verify()

        cleanup:
        ersatz.stop()
    }

    private static convertDayOfWeek(value) {
        return value - 1
    }
}
