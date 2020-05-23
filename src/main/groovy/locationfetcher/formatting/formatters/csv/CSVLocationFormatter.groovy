package locationfetcher.formatting.formatters.csv

import locationfetcher.formatting.formatters.Formatter
import locationfetcher.location.Location
import org.supercsv.io.dozer.CsvDozerBeanWriter
import org.supercsv.io.dozer.ICsvDozerBeanWriter
import org.supercsv.prefs.CsvPreference

class CSVLocationFormatter implements Formatter {

    @Override
    String format(locations) {
        ICsvDozerBeanWriter beanWriter = null

        def writer = new StringWriter()
        def fieldParser = new CSVLocationFieldParser(locations)
        def processors = fieldParser.parseCellProcessors()

        try {
            beanWriter = new CsvDozerBeanWriter(writer, CsvPreference.STANDARD_PREFERENCE)
            beanWriter.configureBeanMapping(Location, fieldParser.parseFieldMappings())
            beanWriter.writeHeader(fieldParser.parseHeaders())

            locations.each { location -> beanWriter.write(location, processors) }
        }
        finally {
            if (beanWriter != null) {
                beanWriter.close()
            }
        }

        writer.toString()
    }
}
