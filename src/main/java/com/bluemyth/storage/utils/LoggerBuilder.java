package com.bluemyth.storage.utils;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.rolling.FixedWindowRollingPolicy;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy;
import ch.qos.logback.core.util.FileSize;
import ch.qos.logback.core.util.OptionHelper;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 日志工具
 *
 * @author xiaot
 * @date 2020-8-14 12:38
 */
public class LoggerBuilder {  
  
    private static final Map<String,Logger> container = new HashMap<>();

    /**
     *
     * @param name
     * @return
     */
    public static Logger getLogger(String name) {  
        Logger logger = container.get(name);  
        if(logger != null) {  
            return logger;  
        }  
        synchronized (LoggerBuilder.class) {  
            logger = container.get(name);  
            if(logger != null) {  
                return logger;  
            }  
            logger = build(name);  
            container.put(name,logger);  
        }  
        return logger;  
    }  
  
    private static Logger build(String name) {  
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger logger = context.getLogger("FILE-" + name);
        logger.setLevel(Level.valueOf(OptionHelper.substVars("${LEVEL}", context)));
        logger.setAdditive(false);  
        RollingFileAppender appender = new RollingFileAppender();  
        appender.setContext(context);  
        appender.setName("FILE-" + name);  
        appender.setFile(OptionHelper.substVars("${LOG_BUILDER_HOME}/" + name + ".log",context));
        appender.setAppend(true);  
        appender.setPrudent(false);  
        FixedWindowRollingPolicy  policy = new FixedWindowRollingPolicy();
        String fp = OptionHelper.substVars("${LOG_BUILDER_HOME}/" + name + ".log.%i",context);

        policy.setFileNamePattern(fp);
        policy.setMinIndex(1);
        policy.setMaxIndex(3);
        policy.setParent(appender);
        policy.setContext(context);  
        policy.start();

        SizeBasedTriggeringPolicy triggeringPolicy = new SizeBasedTriggeringPolicy();
        triggeringPolicy.setMaxFileSize(FileSize.valueOf("100MB"));
        triggeringPolicy.setContext(context);
        triggeringPolicy.start();
  
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();  
        encoder.setContext(context);  
        encoder.setPattern("%d{yyyy-MM-dd HH:mm:ss.SSS, Asia/Shanghai} [%t] %-5level %logger{50} [%file : %line] - %msg%n");  
        encoder.start();

        appender.setRollingPolicy(policy);
        appender.setTriggeringPolicy(triggeringPolicy);
        appender.setEncoder(encoder);
        appender.start();  
        logger.addAppender(appender);  
        return logger;  
    }  
}  