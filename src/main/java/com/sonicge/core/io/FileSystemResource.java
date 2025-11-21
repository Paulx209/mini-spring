package com.sonicge.core.io;

import java.io.*;

public class FileSystemResource implements Resource{

    private final String filePath;

    public FileSystemResource(String filePath){
        this.filePath = filePath;
    }
    @Override
    public InputStream getInputStream() throws IOException {
        try {
            File file = new File(filePath);
            InputStream is = new FileInputStream(file);
            return is;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
