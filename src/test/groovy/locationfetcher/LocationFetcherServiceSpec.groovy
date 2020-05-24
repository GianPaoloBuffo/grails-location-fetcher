package locationfetcher

import com.stehno.ersatz.ErsatzServer
import com.stehno.ersatz.cfg.ContentType
import com.stehno.ersatz.encdec.Encoders
import grails.testing.services.ServiceUnitTest
import locationfetcher.util.MockLocation
import spock.lang.Specification

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
        location == MockLocation.create()

        and:
        ersatz.verify()

        cleanup:
        ersatz.stop()
    }

    private static convertDayOfWeek(value) {
        return value - 1
    }
}
