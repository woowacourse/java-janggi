package controller;

import domain.Board;
import domain.Country;
import java.util.function.Supplier;

//import domain.MaSangPosition;
import service.JanggiService;
import service.dto.BoardDto;
import view.InputView;
import view.OutputView;

public class JanggiController {
    private static final int MAX_RETRY = 10;

    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiService janggiService;


    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.janggiService = new JanggiService();
    }

    public void run() {
        init();


    }

    private void init() {
        outputView.printGameStartMessage();
        outputView.printCountry(Country.CHO);
        int choMasangChoice = doRetry(inputView::requestMaSangPosition);

        outputView.printCountry(Country.HAN);
        int hanMasangChoice = doRetry(inputView::requestMaSangPosition);

        Board board = janggiService.createBoard(choMasangChoice, hanMasangChoice );
        outputView.printTurnStartMessage();

        BoardDto boardDto = janggiService.getBoard(board);
        outputView.printBoard(boardDto);
    }


    private <T> T doRetry(Supplier<T> supplier) {
        int retry = 0;
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e){
                outputView.printErrorMessage(e.getMessage());
                retry++;
                if (retry > MAX_RETRY) {
                    throw new IllegalStateException("입력횟수를 초과했습니다.");
                }
            }
        }
    }
}
