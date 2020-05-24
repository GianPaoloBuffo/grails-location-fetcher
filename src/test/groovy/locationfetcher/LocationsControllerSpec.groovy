package locationfetcher

import grails.testing.web.controllers.ControllerUnitTest
import locationfetcher.util.MockLocation
import spock.lang.Specification

class LocationsControllerSpec extends Specification implements ControllerUnitTest<LocationsController> {

    void "responds with JSON when format=json"() {
        given:
        mockLocationFetcherService()

        when:
        params.format = 'json'
        controller.index()

        then:
        response.contentType == 'application/json;charset=utf-8'
    }

    void "responds with CSV when format=CSV"() {
        given:
        mockLocationFetcherService()

        when:
        params.format = 'csv'
        controller.index()

        then:
        response.contentType == 'text/csv;charset=utf-8'
    }

    private mockLocationFetcherService() {
        controller.locationFetcherService = Mock(LocationFetcherService) {
            locations(_) >> [MockLocation.create()]
        }
    }
}
