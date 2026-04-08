import java.util.Scanner;

import config.ConnectionManager;
import controller.JanggiController;
import view.InputView;
import view.OutputView;
import view.OutputViewFormatter;

public class Application {
    public static void main(String[] args){
        ConnectionManager.startH2Server();
        InputView inputView = new InputView(new Scanner(System.in));
        OutputView outputView = new OutputView(new OutputViewFormatter());
        try{
            JanggiController janggiController = new JanggiController (inputView, outputView);
            janggiController.run();
        } catch (IllegalStateException e){
            System.out.println(e.getMessage());
        }
        ConnectionManager.stopH2Server();
    }
}
