package domain.janggi;

import domain.janggi.controller.JanggiController;
import domain.janggi.domain.Board;
import domain.janggi.domain.BoardInitializer;
import domain.janggi.domain.Parser;
import domain.janggi.domain.Position;
import domain.janggi.domain.Turn;
import domain.janggi.piece.Piece;
import domain.janggi.view.InputView;
import domain.janggi.view.OutputView;
import java.util.List;

public class JanggiApplication {

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        JanggiController janggiController = new JanggiController(inputView, outputView);
        janggiController.run();
    }
}
