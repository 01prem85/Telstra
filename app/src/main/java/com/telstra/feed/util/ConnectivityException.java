package com.telstra.feed.util;

import java.io.IOException;

public class ConnectivityException extends IOException {

    @Override
    public String getMessage() {
        return "No internet available!";
    }
}
