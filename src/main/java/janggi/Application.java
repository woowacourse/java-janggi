package janggi;

import janggi.config.ConnectionManager;

public class Application {
    public static void main(String[] args) {
        ConnectionManager connectionManager = new ConnectionManager();
        Runner runner = new Runner(connectionManager);
        runner.run();
    }
}