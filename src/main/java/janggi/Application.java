package janggi;

import janggi.controller.JanggiFlow;
import janggi.strategy.ArrangementStrategy;
import janggi.strategy.MaSangMaSang;
import janggi.strategy.MaSangSangMa;
import janggi.strategy.SangMaMaSang;
import janggi.strategy.SangMaSangMa;
import janggi.view.ApplicationView;
import janggi.view.ConsoleReader;
import janggi.view.ConsoleWriter;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        List<ArrangementStrategy> strategies = List.of(
                MaSangMaSang.getInstance(),
                MaSangSangMa.getInstance(),
                SangMaMaSang.getInstance(),
                SangMaSangMa.getInstance()
        );
        ApplicationView view = new ApplicationView(new ConsoleWriter(), new ConsoleReader());
        JanggiFlow janggi = new JanggiFlow(strategies, view);

        janggi.process();
    }
}
