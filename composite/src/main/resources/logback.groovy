appender("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{HH:mm:ss.SSS}  [%thread] %highlight(%-5level) %green(%logger{36}) - %msg%n"
    }
}
root(DEBUG, ["STDOUT"])