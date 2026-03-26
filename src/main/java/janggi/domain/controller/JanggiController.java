package janggi.domain.controller;

import janggi.domain.board.Board;
import janggi.domain.board.BoardDesignPolicy;
import janggi.domain.board.HorseElephantPosition;
import janggi.domain.dynasty.Dynasty;
import janggi.dto.DynastyDto;
import janggi.dto.BoardDto;
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
        Map<Dynasty, HorseElephantPosition> horseElephantPositions = new EnumMap<>(Dynasty.class);
        for (Dynasty dynasty : Dynasty.values()) {
            int ordinal = inputView.readHorseElephantPosition(DynastyDto.from(dynasty));
            HorseElephantPosition position = HorseElephantPositionMapper.from(ordinal);
            horseElephantPositions.put(dynasty, position);
        }

        BoardDesignPolicy policy = new BoardDesignPolicy(horseElephantPositions);
        Board board = new Board(policy);
        outputView.printBoard(BoardDto.from(board.board()));

    }

}
