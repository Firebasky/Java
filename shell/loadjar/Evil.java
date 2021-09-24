package com.company;
import java.io.IOException;

public class Evil {
    public void exec(String cmd) throws IOException {
        Runtime.getRuntime().exec(cmd);
    }
}
