package locationfetcher.formatting

import grails.converters.JSON
import locationfetcher.formatting.formatters.csv.CSVLocationFormatter

class FormatterFactory {

    static format(locations, format) {
        switch (format) {
            case ResponseFormat.CSV:
                return new CSVLocationFormatter().format(locations)
            case ResponseFormat.JSON:
                return locations as JSON
        }
    }
}
