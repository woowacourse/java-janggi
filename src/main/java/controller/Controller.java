package controller;

import domain.BoardStatus;
import domain.JanggiGame;
import domain.SettingType;
import domain.piece.Piece;
import domain.position.Position;
import java.util.List;
import java.util.Map;
import view.BoardStatusDto;
import view.InputView;
import view.ResultView;

public class Controller {
    private final InputView inputView;
    private final ResultView resultView;

    public Controller(InputView inputView, ResultView resultView) {
        this.inputView = inputView;
        this.resultView = resultView;
    }

    public void initializeGame() {
        List<SettingType> settingTypes = inputView.readSettings();
        SettingType choSettingType = settingTypes.getFirst();
        SettingType hanSettingType = settingTypes.getLast();

        JanggiGame game = JanggiGame.init(choSettingType, hanSettingType);
        BoardStatus status = game.getJanggiGameStatus();

        //TODO : Print game status.
        Map<Position, Piece> boardStatus = status.getBoardStatus();
        BoardStatusDto statusDto = BoardStatusDto.from(boardStatus);

        resultView.printBoard(statusDto);


    }
}
