package janggi.controller;

import janggi.domain.board.Board;
import janggi.domain.board.BoardFormation;
import janggi.domain.board.BoardInitiator;
import janggi.domain.common.Position;
import janggi.domain.common.Team;
import janggi.dto.BoardResponse;
import janggi.dto.ScoreResponse;
import janggi.dto.TeamResponse;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;

public class JanggiController {

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final BoardInitiator boardInitiator = new BoardInitiator();

    public void run() {
        Team team = Team.CHO;
        boolean isGameOver = false;

        Board board = new Board();
        choiceBoardFormation(board);
        outputView.printBoard(BoardResponse.from(board));

        while (!isGameOver) {
            playGame(team, board);
            outputView.printTeamScore(ScoreResponse.from(board.calculateScore()));
            isGameOver = board.isKingDead();
            team = team.next();
        }
        outputView.printWinner(TeamResponse.from(board.findWinner()));
    }

    private void choiceBoardFormation(Board board) {
        askBoardFormation(board, Team.HAN);
        askBoardFormation(board, Team.CHO);
    }

    private void askBoardFormation(Board board, Team team) {
        outputView.printBoardFormation(TeamResponse.from(team));
        int boardFormationChoice = inputView.readBoardFormationChoice();
        BoardFormation formation = BoardFormation.selectByChoice(boardFormationChoice);
        boardInitiator.initializeByFormation(board, formation, team);
    }

    private void playGame(Team team, Board board) {
        outputView.printTurnMessage(TeamResponse.from(team));

        Position movePiecePosition = askMovePiecePositionUntilValid(board);

        List<Position> availablePositions = board.findAvailablePositions(movePiecePosition);

        outputView.printBoard(BoardResponse.of(board, availablePositions));

        Position movePosition = askMovePositionUntilValid(board, movePiecePosition);

        board.movePiece(movePiecePosition, movePosition);

        outputView.printBoard(BoardResponse.from(board));
    }

    private Position askMovePiecePositionUntilValid(Board board) {
        boolean isInvalid = true;
        Position position = null;
        while (isInvalid) {
            try {
                outputView.printMoveInfo();
                position = inputView.readPosition();
                board.validateMovePiecePosition(position);
                List<Position> positions = board.findAvailablePositions(position);
                board.validateAvailablePositions(positions);
                isInvalid = false;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
        return position;
    }

    private Position askMovePositionUntilValid(Board board, Position movePiecePosition) {
        boolean isInvalid = true;
        Position position = null;
        while (isInvalid) {
            try {
                outputView.printMoveChoiceInfo();
                position = inputView.readPosition();
                board.validateDestination(movePiecePosition, position);
                isInvalid = false;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
        return position;
    }
}
