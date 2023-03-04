package org.example;

public class FileMaxSizeReachedException extends Exception {

    public FileMaxSizeReachedException(int limitFileSize, long previousFileSize, String previousFilePath ) {
        super("Limit file size : " + limitFileSize + " bytes  is reached" +
                "\nThe exact size of the previous file: " + previousFileSize +
                "\nThe path of the previous file: " + previousFilePath);
    }
}
