package locationfetcher

import locationfetcher.formatting.ResponseFormat

import static locationfetcher.formatting.FormatterFactory.format

class LocationsController {

    def locationFetcherService

    def index() {
        def locations = locationFetcherService.locations(params)

        withFormat {
            json {
                def response = format(locations, ResponseFormat.JSON)
                render(contentType: 'application/json', text: response)
            }
            csv {
                def response = format(locations, ResponseFormat.CSV)
                render(contentType: 'text/csv', text: response)
            }
        }
    }
}
