package locationfetcher

import grails.converters.JSON

import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class BootStrap {

    def init = { servletContext ->
        registerLocalTimeConverter()
        registerDayOfWeekConverter()
    }

    private static registerLocalTimeConverter() {
        JSON.registerObjectMarshaller(LocalTime) {
            return it?.format(DateTimeFormatter.ofPattern("HH:mm"))
        }
    }

    private static registerDayOfWeekConverter() {
        JSON.registerObjectMarshaller(DayOfWeek) {
            return it?.ordinal()
        }
    }
}
