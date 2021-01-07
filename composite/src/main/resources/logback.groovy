appender("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{HH:mm:ss.SSS} - [%thread] %highlight(%-5level) %cyan(%logger{36}) - %yellow([%X{CORRELATION-ID}]) - %msg%n"
    }
}
root(INFO, ["STDOUT"])