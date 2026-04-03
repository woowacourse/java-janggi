package janggi;

import janggi.controller.JanggiFlow;
import janggi.view.ApplicationView;
import janggi.view.input.ConsoleReader;
import janggi.view.output.ConsoleWriter;

public class Application {

    public static void main(String[] args) {
        ApplicationView view = new ApplicationView(new ConsoleWriter(), new ConsoleReader());
        JanggiFlow janggi = new JanggiFlow(view);

        janggi.process();
    }
}
