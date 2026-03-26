package janggi.controller;

import janggi.domain.board.HorseElephantPosition;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.game.Game;
import janggi.dto.BoardDto;
import janggi.dto.DynastyDto;
import janggi.util.HorseElephantPositionMapper;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.EnumMap;
import java.util.Map;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        // 장기판 상차림 입력
        Map<Dynasty, HorseElephantPosition> horseElephantPositions = new EnumMap<>(Dynasty.class);
        for (Dynasty dynasty : Dynasty.values()) {
            int ordinal = inputView.readHorseElephantPosition(DynastyDto.from(dynasty));
            HorseElephantPosition position = HorseElephantPositionMapper.from(ordinal);
            horseElephantPositions.put(dynasty, position);
        }

        Game game = Game.initGame(horseElephantPositions);
        outputView.printBoard(BoardDto.from(game.boardMap()));

        // 움직이고 싶은 기물의 좌표 입력
        inputView.readPieceWantToMove(DynastyDto.from(game.currentTurn().currentDynasty()));


    }

}
