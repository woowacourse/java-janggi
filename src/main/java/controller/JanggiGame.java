package controller;

import common.exception.JanggiException;
import service.JanggiService;
import database.context.BoardIdContext;
import database.dto.GameResult;
import view.dto.BoardSelectCommand;
import domain.board.Formation;
import domain.board.JanggiBoard;
import domain.board.JanggiIntersectionGenerator;
import view.dto.JanggiBoardDto;
import database.dto.Moved;
import domain.piece.Team;
import view.dto.MoveCommand;
import view.InputReader;
import view.OutputWriter;

import java.util.function.Supplier;

public class JanggiGame {

    private final InputReader reader;
    private final OutputWriter writer;
    private final JanggiService janggiService;

    public JanggiGame(InputReader reader, OutputWriter writer, JanggiService janggiService) {
        this.reader = reader;
        this.writer = writer;
        this.janggiService = janggiService;
    }

    public void run() {
        JanggiBoard janggiBoard;
        showDoesntEndBoardList();
        while ((janggiBoard = requestAndGetBoard()) != null) {
            startGame(janggiBoard);
            applyGameResult(janggiBoard);
            announceWinner(janggiBoard.getWinner());
            showDoesntEndBoardList();
        }
    }

    private void showDoesntEndBoardList() {
        writer.printExistingPlayingBoard(janggiService.readExistPlayingBoard());
    }

    private JanggiBoard requestAndGetBoard() {
        return retry(() -> {
            BoardSelectCommand selectCommand = reader.requestBoardSelectCommand();
            return getJanggiBoard(selectCommand);
        });
    }

    public JanggiBoard getJanggiBoard(BoardSelectCommand selectCommand) {
        if (selectCommand.isExitCommand()) {
            return null;
        }

        if (selectCommand.isNewGameCommand()) {
            JanggiBoard janggiBoard = generateJanggiBoard();
            Long boardId = janggiService.createBoard(janggiBoard);
            BoardIdContext.setBoardId(boardId);
            return janggiBoard;
        }

        BoardIdContext.setBoardId(selectCommand.select());
        return janggiService.getExistBoard(selectCommand.select());
    }

    private JanggiBoard generateJanggiBoard() {
        Formation hanFormation = requestFormation(Team.HAN);
        Formation choFormation = requestFormation(Team.CHO);
        return new JanggiBoard(new JanggiIntersectionGenerator(hanFormation, choFormation));
    }

    private Formation requestFormation(Team team) {
        return retry(() -> reader.requestFormation(team));
    }

    private void startGame(JanggiBoard janggiBoard) {
        printJanggiBoard(JanggiBoardDto.from(janggiBoard));
        progressGame(janggiBoard);
    }

    private void progressGame(JanggiBoard janggiBoard) {
        while (!janggiBoard.isGameOver()) {
            Moved moved = progressTurn(janggiBoard);
            janggiService.updateTurn(moved, janggiBoard.getCurrentTurn());
        }
    }

    public Moved progressTurn(JanggiBoard janggiBoard) {
        return retry(() -> {
            MoveCommand command = requestCommand(janggiBoard.getCurrentTurn());
            Moved moved = janggiBoard.processTurn(command.start(), command.end());
            printJanggiBoard(JanggiBoardDto.from(janggiBoard));
            return moved;
        });
    }

    private void printJanggiBoard(JanggiBoardDto boardView) {
        writer.printJanggiBoard(boardView);
    }

    private MoveCommand requestCommand(Team team) {
        return reader.requestCommand(team);
    }

    public void applyGameResult(JanggiBoard janggiBoard) {
        GameResult gameResult = GameResult.from(janggiBoard);
        janggiService.updateBoardResult(gameResult);
        BoardIdContext.clear();
    }

    public void announceWinner(Team team) {
        writer.printWinner(team);
    }

    private <T> T retry(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (JanggiException e) {
            writer.printErrorMessage(e.getMessage());
            return retry(supplier);
        }
    }

}

