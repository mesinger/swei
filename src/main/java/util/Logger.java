package util;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Priority;

public class Logger {
    public static void log(Class cl, Priority prio, Object obj) {
        BasicConfigurator.configure();

        org.apache.log4j.Logger logger = LogManager.getLogger(cl);
        logger.log(prio, obj);
    }
}
