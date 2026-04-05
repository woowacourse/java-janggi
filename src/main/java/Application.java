import dao.JanggiDao;
import java.util.Scanner;

import controller.JanggiController;
import service.JanggiService;
import view.InputView;
import view.OutputView;
import view.OutputViewFormatter;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView(new Scanner(System.in));
        OutputView outputView = new OutputView(new OutputViewFormatter());
        JanggiDao janggiDao = new JanggiDao();
        janggiDao.createTable();

        JanggiService janggiService = new JanggiService(janggiDao);
        try{
            JanggiController janggiController = new JanggiController (inputView, outputView, janggiService);
            janggiController.run();
        } catch (IllegalStateException e){
            outputView.printErrorMessage("입력 횟수를 초과했습니다. 게임을 종료합니다.");
        }
    }
}
