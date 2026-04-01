package janggi;

import janggi.controller.JanggiFlow;
import janggi.view.ApplicationView;
import janggi.view.ConsoleReader;
import janggi.view.ConsoleWriter;

public class Application {

    public static void main(String[] args) {
        ApplicationView view = new ApplicationView(new ConsoleWriter(), new ConsoleReader());
        JanggiFlow janggi = new JanggiFlow(view);

        janggi.process();
    }
}
