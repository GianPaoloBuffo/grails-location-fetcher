package locationfetcher

import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.http.uri.UriBuilder
import locationfetcher.location.Location
import locationfetcher.thirdparty.UberallResponse

class LocationFetcherService implements GrailsConfigurationAware {

    private static final String HOST = 'uberall.sandbox.host'
    private static final String LOCATIONS_URI = 'uberall.sandbox.locationsUri'
    private static final String PRIVATE_KEY = 'uberall.sandbox.privateKey'

    String locationsUri
    String privateKey
    BlockingHttpClient client

    @Override
    void setConfiguration(Config co) {
        setupHttpClient(co.getProperty(HOST, String))
        this.locationsUri = co.getProperty(LOCATIONS_URI, String)
        this.privateKey = co.getProperty(PRIVATE_KEY, String)
    }

    Location[] locations(params) {
        try {
            def request = httpRequest(params)
            def uberallResponse = client.retrieve(request, UberallResponse)
            uberallResponse.response.locations
        } catch (HttpClientResponseException e) {
            log.error(e)
            []
        }
    }

    private void setupHttpClient(url) {
        this.client = HttpClient.create(url.toURL()).toBlocking()
    }

    private HttpRequest httpRequest(params) {
        HttpRequest.GET(uri(params)).header('privateKey', this.privateKey)
    }

    private URI uri(params) {
        def uriBuilder = UriBuilder.of(this.locationsUri)
        maxParam(params).ifPresent { p -> uriBuilder.queryParam('max', p) }
        uriBuilder.build()
    }

    private static Optional maxParam(params) {
        Optional.ofNullable(params.max)
    }
}
