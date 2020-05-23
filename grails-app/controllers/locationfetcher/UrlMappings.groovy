package locationfetcher

class UrlMappings {

    static mappings = {
        get "/api/$controller"(action: "index")

        "/"(controller: 'application', action: 'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
