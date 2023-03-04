package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileLoggerConfigurationLoader {

    FileLoggerConfiguration flc = new FileLoggerConfiguration();

    FileLoggerConfiguration load(File file) {
        try {
            String s = Files.readString(file.toPath());
            flc.file = new File(s.substring(6, 9));
            if (s.substring(17, 22).equals("DEBUG")) {
                flc.loggingLevel = LoggingLevel.DEBUG;
                flc.logFileSize = Integer.parseInt(s.substring(33, 37));
                flc.format = s.substring(46);
            }
            else {
                flc.loggingLevel = LoggingLevel.INFO;
                flc.logFileSize = Integer.parseInt(s.substring(32, 36));
                flc.format = s.substring(45);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return flc;
    }
}
