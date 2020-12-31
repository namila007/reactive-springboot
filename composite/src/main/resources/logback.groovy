appender("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{HH:mm:ss.SSS} - [%thread] %highlight(%-5level) %cyan(%logger{36}) - %msg%n"
    }
}
root(INFO, ["STDOUT"])