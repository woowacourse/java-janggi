import config.DBInitializer;
import dao.JanggiGameDao;
import dao.PieceDao;
import java.util.Scanner;

import controller.JanggiController;
import service.JanggiService;
import view.InputView;
import view.OutputView;
import view.ViewFormatter;

public class Application {
    public static void main(String[] args) {
        ViewFormatter viewFormatter = new ViewFormatter();
        InputView inputView = new InputView(new Scanner(System.in), viewFormatter);
        OutputView outputView = new OutputView(new ViewFormatter());

        JanggiGameDao janggiGameDao = new JanggiGameDao();
        PieceDao pieceDao = new PieceDao();

        DBInitializer.init();

        JanggiService janggiService = new JanggiService(janggiGameDao, pieceDao);
        try{
            JanggiController janggiController = new JanggiController (inputView, outputView, janggiService);
            janggiController.run();
        } catch (IllegalStateException e){
            outputView.printErrorMessage("입력 횟수를 초과했습니다. 게임을 종료합니다.");
        }
    }
}
