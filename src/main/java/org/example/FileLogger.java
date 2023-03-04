package org.example;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileLogger {

    FileLoggerConfiguration flc = new FileLoggerConfiguration();

    // переменная-ссылка на старый файл для передачи в FileMaxSizeReachedException
    File oldFile;

    // list с именами файлов в директории
    private List<String> listFilesInDirectory = new ArrayList<>();

    void debug(String message) {
        info(message);          // TODO: 19.12.2022 вызов уровня логгирования info

        flc.loggingLevel = LoggingLevel.DEBUG;
        // TODO: 19.12.2022 проверить наличие файлов в дирректории
        if (getListOfFiles(flc.file).isEmpty()) {
            // файлов с "log" в имени, в дирректории нет

            // создаём новый файл
            createNewFileWithDate();

            // делаем запись в файл
            writeToFile(message, flc);

        } else {
            // дирректория не пустая, в ней есть файлы с "log" в имени

            // TODO: 19.12.2022 получаем список файлов с "log" в имени
            getListOfFiles(flc.file);

            // TODO: 19.12.2022 определяем последний файл
            getLastModifiedFile();

            //  файл для записи = последний изменённый файл
            flc.file = getLastModifiedFile();

            // TODO: 19.12.2022 проверяем размер последнего файла
            // проверка размера текущего flc.file файла и запись в файл в названии которого есть дата
            long currentFileSize = flc.file.length();

            // TODO: 19.12.2022 если размер не превышает - пишем в него - Exception
            try {
                if (currentFileSize <= flc.logFileSize) {
                    writeToFile(message, flc);
                } else {
                    // TODO: 19.12.2022 если размер превышает - создаём новый файл
                    createNewFileWithDate();

                    // TODO: 19.12.2022 делаем запись в файл
                    writeToFile(message, flc);
                    throw new FileMaxSizeReachedException(flc.logFileSize,
                            oldFile.length(),
                            oldFile.getAbsolutePath());
                }
            } catch (FileMaxSizeReachedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    void info(String message) {

        flc.loggingLevel = LoggingLevel.INFO;
        // TODO: 19.12.2022 проверить наличие файлов в дирректории
        if (getListOfFiles(flc.file).isEmpty()) {
            // файлов с "log" в имени, в дирректории нет

            // создаём новый файл
            createNewFileWithDate();

            // делаем запись в файл
            writeToFile(message, flc);

        } else {
            // дирректория не пустая, в ней есть файлы с "log" в имени

            // TODO: 19.12.2022 получаем список файлов с "log" в имени
            getListOfFiles(flc.file);

            // TODO: 19.12.2022 определяем последний файл
            getLastModifiedFile();

            //  файл для записи = последний изменённый файл
            flc.file = getLastModifiedFile();

            // TODO: 19.12.2022 проверяем размер последнего файла
            // проверка размера текущего flc.file файла и запись в файл в названии которого есть дата
            long currentFileSize = flc.file.length();

            // TODO: 19.12.2022 если размер не превышает - пишем в него - Exception
            try {
                if (currentFileSize <= flc.logFileSize) {
                    writeToFile(message, flc);
                } else {
                    // TODO: 19.12.2022 если размер превышает - создаём новый файл
                    createNewFileWithDate();

                    // TODO: 19.12.2022 делаем запись в файл
                    writeToFile(message, flc);
                    throw new FileMaxSizeReachedException(flc.logFileSize,
                            oldFile.length(),
                            oldFile.getAbsolutePath());
                }
            } catch (FileMaxSizeReachedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    void debugInfoMix(FileLoggerConfiguration flc, String message) {

        // TODO: 19.12.2022 проверить наличие файлов в дирректории
        if (getListOfFiles(flc.file).isEmpty()) {
            // файлов с "log" в имени, в дирректории нет

            // создаём новый файл
            createNewFileWithDate();

            // делаем запись в файл
            writeToFile(message, flc);

        } else {
            // дирректория не пустая, в ней есть файлы с "log" в имени

            // TODO: 19.12.2022 получаем список файлов с "log" в имени
            getListOfFiles(flc.file);

            // TODO: 19.12.2022 определяем последний файл
            getLastModifiedFile();

            //  файл для записи = последний изменённый файл
            flc.file = getLastModifiedFile();

            // TODO: 19.12.2022 проверяем размер последнего файла
            // проверка размера текущего flc.file файла и запись в файл в названии которого есть дата
            long currentFileSize = flc.file.length();

            // TODO: 19.12.2022 если размер не превышает - пишем в него - Exception
            try {
                if (currentFileSize <= flc.logFileSize) {
                    writeToFile(message, flc);
                } else {
                    // TODO: 19.12.2022 если размер превышает - создаём новый файл
                    createNewFileWithDate();

                    // TODO: 19.12.2022 делаем запись в файл
                    writeToFile(message, flc);
                    throw new FileMaxSizeReachedException(flc.logFileSize,
                            oldFile.length(),
                            oldFile.getAbsolutePath());
                }
            } catch (FileMaxSizeReachedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // метод создаёт новый файл
    private void createNewFileWithDate() {
        oldFile = flc.file;
        flc.file = new File(flc.file.getName().substring(0, 3) + " " + getCurrentDateTime());
        try {
            flc.file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // метод пишет в файл
    private void writeToFile(String message, FileLoggerConfiguration flc) {

//        // Java IO - работает
//        try {
//            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(flc.file, true));
////            // без String.format
////            bufferedWriter.append("\n[" + getCurrentDateTime() + "]" + "[" + flc.loggingLevel + "]" +
////                    " Message: " + "[" + message + "]");
//
//            // с использованием String.format
//            bufferedWriter.append(String.format("[%s] [%s] Message: [%s]",
//                    getCurrentDateTime(), flc.loggingLevel, message));
//
//            bufferedWriter.close(); // используется  и без String.format и с String.format
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        // Java NIO
        RandomAccessFile randomAccessFile = null;
        FileChannel fileChannel = null;
        ByteBuffer byteBuffer = null;
        try {
            randomAccessFile = new RandomAccessFile(flc.file, "rw");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        fileChannel = randomAccessFile.getChannel();
        String s = "\n[" + getCurrentDateTime() + "]" + "[" + flc.loggingLevel + "]" +
                " Message: " + "[" + message + "]";
        byteBuffer = ByteBuffer.wrap(s.getBytes(StandardCharsets.UTF_8));
        try {
            long l = flc.file.length();
            randomAccessFile.seek(l);
            fileChannel.write(byteBuffer);
            fileChannel.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // метод возвращает строку с текущей датой и временем
    private String getCurrentDateTime() {
        String currentDateTime;
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss");
        Date date = new Date();
        currentDateTime = dateFormat.format(date);
        return currentDateTime;
    }

    // метод работает в дирректории и записывает в String list файлы с "log" в названии
    private List getListOfFiles(File file) {
        // записываем в переменную dir текщий каталог
        String dir = file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - file.getName().length());
        File f = new File(dir);
        //записываем в String listFilesInDirectory имена файлов
        Collections.addAll(listFilesInDirectory, f.list());

        // удаляем из listFilesInDirectory файлы без "log" в названии
        Iterator iterator = listFilesInDirectory.listIterator();
        while (iterator.hasNext()) {
            String s2 = (String) iterator.next();
            if (!s2.contains(flc.file.getName().substring(0, 3))) {
                iterator.remove();
            }
        }
        // сейчас в listFilesInDirectory содержится список файлов с "log" в названии
        return listFilesInDirectory;
    }

    // метод находит последний по времени файл
    private File getLastModifiedFile() {

        File lastModifiedFile = new File(listFilesInDirectory.get(0));
        for (String s : listFilesInDirectory) {
            File tempFile = new File(s);
            if (lastModifiedFile.lastModified() < tempFile.lastModified()) {
                lastModifiedFile = tempFile;
            }
        }
        return lastModifiedFile;
    }

}


