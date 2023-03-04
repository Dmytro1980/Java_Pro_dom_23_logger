package org.example;

import java.io.File;

public class FileLoggerConfiguration {
    File file;                      // файл
    LoggingLevel loggingLevel;      // видимость log-файла
    int logFileSize;                // размер log-файла в байтах
    String format;                  // ???
    

    public FileLoggerConfiguration() {
        file = new File("log");
        logFileSize =   1000;
    }
}


//Формат запису: [ПОТОЧНИЙ_ЧАС][DEBUG] Повідомлення: [СТРОКА-ПОВІДОМЛЕННЯ]  -- ?????