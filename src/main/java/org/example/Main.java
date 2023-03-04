package org.example;

import java.io.File;

public class Main {
    public static void main(String[] args) {

        FileLogger fileLogger = new FileLogger();

        fileLogger.info("test string for info logging");
        fileLogger.debug("test string for debug logging");


        FileLoggerConfigurationLoader loader = new FileLoggerConfigurationLoader();

        fileLogger.debugInfoMix(loader.load(new File("configDebug")),
                "test string for debugInfoMix - configDebug");

        fileLogger.debugInfoMix(loader.load(new File("configInfo")),
                "test string for debugInfoMix - configInfo");



    }
}