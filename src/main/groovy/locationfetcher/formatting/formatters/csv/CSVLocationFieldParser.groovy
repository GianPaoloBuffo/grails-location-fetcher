package locationfetcher.formatting.formatters.csv

import locationfetcher.location.Location
import org.supercsv.cellprocessor.constraint.NotNull
import org.supercsv.cellprocessor.ift.CellProcessor

import java.util.stream.IntStream

class CSVLocationFieldParser {

    private static final String OPENING_HOURS = 'openingHours'
    private static final String DAY_OF_WEEK = 'dayOfWeek'
    private static final String FROM = 'from1'
    private static final String TO = 'to1'

    private final Location[] locations

    CSVLocationFieldParser(locations) {
        this.locations = locations
    }

    String[] parseFieldMappings() {
        parseFields(false)
    }

    String[] parseHeaders() {
        parseFields(true)
    }

    CellProcessor[] parseCellProcessors() {
        new CSVLocationCellParser().parse()
    }

    private parseFields(isHeader) {
        fieldNames()
                .collect { it == OPENING_HOURS ? openingHours(isHeader) : it }
                .flatten()
    }

    private openingHours(isHeader) {
        IntStream.range(0, openingHoursColCount())
                .collect { openingHoursFields(it, isHeader) }
                .flatten()
    }

    private openingHoursFields(index, isHeader) {
        def leftSeparator = isHeader ? '/' : '['
        def rightSeparator = isHeader ? '/' : '].'

        [
                "${OPENING_HOURS}${leftSeparator}${index}${rightSeparator}${DAY_OF_WEEK}",
                "${OPENING_HOURS}${leftSeparator}${index}${rightSeparator}${FROM}",
                "${OPENING_HOURS}${leftSeparator}${index}${rightSeparator}${TO}",
        ]
    }

    class CSVLocationCellParser {
        CellProcessor[] parse() {
            fieldNames()
                    .collect { it == OPENING_HOURS ? openingHoursProcessors() : new NotNull() }
                    .flatten() as CellProcessor[]
        }

        private openingHoursProcessors() {
            IntStream.range(0, openingHoursColCount())
                    .collect { [new DayOfWeekCellProcessor(), new NotNull(), new NotNull()] }
                    .flatten()
        }
    }

    private int openingHoursColCount() {
        locations.collect { it.openingHours.size }.max()
    }

    private static fieldNames() {
        Location.declaredFields
                .findAll { filterDomainFields(it) }*.name
    }

    private static filterDomainFields(field) {
        !field.synthetic
    }
}
