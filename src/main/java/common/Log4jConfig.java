package common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.xml.XmlConfigurationFactory;

public class Log4jConfig {
    private static Logger logger  =null;
    public Log4jConfig(Class inputClass){
        System.setProperty(XmlConfigurationFactory.CONFIGURATION_FILE_PROPERTY, "./log4j.xml");
        logger = (Logger) LogManager.getLogger(inputClass);
    }


    public Logger getLogger() {
        return logger;
    }
}
