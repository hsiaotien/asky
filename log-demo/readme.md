日志测试

https://www.cnblogs.com/Sinte-Beuve/p/5758971.html

https://logback.qos.ch/manual/configuration.html

https://docs.spring.io/spring-boot/docs/2.0.5.RELEASE/reference/htmlsingle/#howto-logging


note:
 可以通过配置application.yml
 可以通过配置文件logback-spring.xml
 ```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <include resource="org/springframework/boot/logging/logback/defaults.xml"/>
    ​
    <springProperty scope="context" name="springAppName" source="spring.application.name"/>
    <springProperty scope="context" name="logRootPath" source="log.root.path"/>

    <!-- Example for logging into the build folder of your project -->
    <property name="LOG_FILE" value="${logRootPath}/${springAppName}/${springAppName}.log"/>

    <!-- Appender to log to console -->
    <appender name="console" class="ch.qos.logback.core.ConsoleAppender">
        <!--<filter class="ch.qos.logback.classic.filter.ThresholdFilter">-->
            <!--&lt;!&ndash; Minimum logging level to be presented in the console logs &ndash;&gt;-->
            <!--<level>INFO</level>-->
        <!--</filter>-->
        <encoder>
            <pattern>${CONSOLE_LOG_PATTERN}</pattern>
            <charset>utf8</charset>
        </encoder>
    </appender>

    <!-- Appender to log to file -->
    <appender name="flatfile" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${LOG_FILE}</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${LOG_FILE}.%d{yyyy-MM-dd}.gz</fileNamePattern>
            <maxHistory>7</maxHistory>
        </rollingPolicy>
        <encoder>
            <pattern>${FILE_LOG_PATTERN}</pattern>
            <charset>utf8</charset>
        </encoder>
    </appender>
    ​
    <root level="INFO">
        <appender-ref ref="console"/>
        <appender-ref ref="flatfile"/>
    </root>
</configuration>
```