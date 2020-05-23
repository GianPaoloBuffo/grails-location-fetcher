package locationfetcher.formatting.formatters.csv

import org.supercsv.cellprocessor.CellProcessorAdaptor
import org.supercsv.exception.SuperCsvException
import org.supercsv.util.CsvContext

import java.time.DayOfWeek

class DayOfWeekCellProcessor extends CellProcessorAdaptor {

    private static final String ERROR_MESSAGE = 'Input should be of type java.time.DayOfWeek';

    @Override
    Object execute(Object value, CsvContext context) {
        if (!value instanceof DayOfWeek) {
            throw new SuperCsvException(ERROR_MESSAGE)
        }

        ((DayOfWeek) value).ordinal()
    }
}
