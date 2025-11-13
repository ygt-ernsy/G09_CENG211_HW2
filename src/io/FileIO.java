package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * FileIO - A utility class for reading files line by line
 */
public class FileIO {
    private String path;
    private BufferedReader bufferedReader;

    public FileIO(String path) {
        this.path = path;
    }

    /**
     * Opens the file for reading. Must be called before getNextLine().
     * 
     * @return true if file opened successfully, false otherwise
     */
    public boolean open() {
        try {
            bufferedReader = new BufferedReader(new FileReader(path));
            return true;
        } catch (IOException e) {
            System.out.println("Error opening file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Reads the next line from the file.
     * 
     * @return the next line, or null if end of file or error
     */
    public String getNextLine() {
        if (bufferedReader == null) {
            System.out.println("File not opened. Call open() first.");
            return null;
        }

        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return null;
        }
    }

    /**
     * Closes the file. Should be called when done reading.
     */
    public void close() {
        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                System.err.println("Error closing file: " + e.getMessage());
            }
        }
    }

    /**
     * Resets the reader to the beginning of the file.
     * Useful for multi-pass reading.
     */
    public void reset() {
        close();
        open();
    }
}
