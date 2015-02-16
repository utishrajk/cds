import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import java.nio.charset.Charset

import static ch.qos.logback.classic.Level.DEBUG
import static ch.qos.logback.classic.Level.WARN
import static ch.qos.logback.classic.Level.INFO
import static ch.qos.logback.classic.Level.OFF

/*
	There are 5 levels of logging:
	FATAL: shows messages at a FATAL level only
	ERROR: Shows messages classified as ERROR and FATAL
	WARNING: Shows messages classified as WARNING, ERROR, and FATAL
	INFO: Shows messages classified as INFO, WARNING, ERROR, and FATAL (Production Preference)
	DEBUG: Shows messages classified as DEBUG, INFO, WARNING, ERROR, and FATAL (Development Preference)
*/

scan()

def appenderList = ["ROLLING"]
def consoleAppender = true;
def WEBAPP_DIR = "."
def loglevel = INFO
def command = System.getProperty("logLevel")

//Hostname is machine name where app is being deployed, Using this variable we control the Appender Type and the Log Level.
//println "Hostname is ${hostname}"

if (hostname =~ /production|development/) {
	//WEBAPP_DIR = "/opt/bham"     
  	consoleAppender = false   
} else {
  	appenderList.add("CONSOLE")
  	loglevel = DEBUG
}

// This option will come handy when developing locally (For instance this is the command to turn off app logs: "gradle debugTomcatRunWar -DlogLevel=--off")
if( command.equals('debug') ) {
    loglevel = DEBUG
} else if ( command.equals('info') ) {
    loglevel = INFO
} else if ( command.equals('off') ) {
    loglevel = OFF
}

if (consoleAppender) {
	appender("CONSOLE", ConsoleAppender) {
	  encoder(PatternLayoutEncoder) {
	    charset = Charset.forName("utf-8")
	    pattern = "%d{HH:mm:ss.SSS} [%thread] [%p] %c - %m%n"
	  }
	}
}

appender("ROLLING", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    Pattern = "%d %level %thread %mdc %logger - %m%n"
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    FileNamePattern = "${WEBAPP_DIR}/log/bham-%d{yyyy-MM-dd}.log"
  }
}

logger("com.feisystems.bham", loglevel)
logger("ch.qos.logback", WARN)
logger("com.codahale.metrics", WARN)
logger("com.ryantenney", WARN)
logger("com.zaxxer", WARN)
logger("net.sf.ehcache", WARN)
logger("org.apache", WARN)
logger("org.apache.catalina.startup.DigesterFactory", OFF)
logger("org.hibernate.validator", WARN)
logger("org.hibernate", WARN)
logger("org.hibernate.ejb.HibernatePersistence", OFF)
logger("org.springframework", WARN)
logger("org.springframework.web", WARN)
logger("org.springframework.security", WARN)
logger("org.springframework.cache", WARN)

root(loglevel, appenderList)