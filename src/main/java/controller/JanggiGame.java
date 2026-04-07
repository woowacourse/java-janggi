package controller;

import common.exception.JanggiException;
import database.JanggiService;
import database.connection.BoardIdContext;
import domain.board.BoardSelectCommand;
import domain.board.Formation;
import domain.board.JanggiBoard;
import domain.board.JanggiIntersectionGenerator;
import domain.board.dto.JanggiBoardDto;
import domain.board.dto.Moved;
import domain.piece.Team;
import domain.point.dto.MoveCommand;
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
        showDoesntEndBoardList();
        BoardSelectCommand selectCommand = reader.requestBoardSelectCommand();
        JanggiBoard janggiBoard = getJanggiBoard(selectCommand);
        startGame(janggiBoard, Team.CHO); // TODO 이 부분을 DB값과 일치하도록 or currentTeam을 Board가 가지도록.
        announceWinner(janggiBoard.getWinner());
    }

    private void showDoesntEndBoardList() {
        writer.printExistingPlayingBoard(janggiService.readExistPlayingBoard());
    }

    // Board PK 추가
    public JanggiBoard getJanggiBoard(BoardSelectCommand selectCommand) {
        if (selectCommand.isNewGameCommand()) {
            JanggiBoard janggiBoard = generateJanggiBoard();
            Long boardId = janggiService.createBoard(janggiBoard);
            BoardIdContext.setBoardId(boardId);
            return janggiBoard;
        }

        BoardIdContext.setBoardId(selectCommand.select());
        return janggiService.getExistBoard(selectCommand);
    }

    private JanggiBoard generateJanggiBoard() {
        Formation hanFormation = requestFormation(Team.HAN);
        Formation choFormation = requestFormation(Team.CHO);
        return new JanggiBoard(new JanggiIntersectionGenerator(hanFormation, choFormation));
    }

    private Formation requestFormation(Team team) {
        return retry(() -> reader.requestFormation(team));
    }

    private void startGame(JanggiBoard janggiBoard, Team startTurn) {
        printJanggiBoard(JanggiBoardDto.from(janggiBoard));
        progressGame(janggiBoard, startTurn);
    }

    private void progressGame(JanggiBoard janggiBoard, Team currentTeam) {
        while (!janggiBoard.isGameOver() || janggiBoard.hasNotEnoughPieceScore()) {
            Moved moved = progressTurn(janggiBoard, currentTeam);
            currentTeam = currentTeam.nextTurn();
            janggiService.updateTurn(moved, currentTeam);
        }
    }

    public Moved progressTurn(JanggiBoard janggiBoard, Team currentTeam) {
        return retry(() -> {
            MoveCommand command = requestCommand(currentTeam);
            Moved moved = janggiBoard.tryToMove(command.start(), command.end(), currentTeam);
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

    public void announceWinner(Team team) {
        writer.printWinner(team);
    }

    private void retry(Runnable action) {
        try {
            action.run();
        } catch (JanggiException e) {
            writer.printErrorMessage(e.getMessage());
            retry(action);
        }
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

