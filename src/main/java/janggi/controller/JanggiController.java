package janggi.controller;

import janggi.DBConnection;
import janggi.domain.Turn;
import janggi.domain.board.Board;
import janggi.domain.board.BoardFactory;
import janggi.domain.piece.HorseSide;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Position;
import janggi.domain.piece.Team;
import janggi.service.JanggiService;
import janggi.view.InputConverter;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.sql.Connection;
import java.util.Map;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiService janggiService;

    public JanggiController(final InputView inputView, final OutputView outputView, final JanggiService janggiService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.janggiService = janggiService;
    }

    public void startJanggi() {
        Connection connection = DBConnection.getConnection();
        if (connection == null) {
            startJanggiWithoutSave();
            return;
        }
        startJanggiWithSave();
    }

    public void startJanggiWithoutSave() {
        Board board = getInitializedBoardByInput();
        Turn turn = Turn.startWith(Team.BLUE);
        playGame(board, turn, false, -1);
    }

    public void startJanggiWithSave() {
        Board board = loadOrInitializeBoard();
        int gameId = janggiService.getLatestGameId();
        Turn turn = Turn.startWith(janggiService.loadTurn(gameId));
        playGame(board, turn, true, gameId);
    }

    private void playGame(final Board board, final Turn turn, final boolean saveEnabled, final int gameId) {
        if (saveEnabled) {
            janggiService.saveGame(gameId, board, turn.current());
        }

        while (true) {
            Team nowTeam = turn.next();
            outputView.printBoard(board);
            String pieceMovement = inputView.readPieceMovement(nowTeam);
            movePieceByPieceMovement(nowTeam, pieceMovement, board);

            if (saveEnabled) {
                janggiService.saveGame(gameId, board, turn.current());
            }

            if (board.checkGameOver()) {
                printResult(board, turn);
                if (saveEnabled) {
                    janggiService.deleteGame(gameId);
                }
                break;
            }
        }
    }

    private Board loadOrInitializeBoard() {
        Map<Position, Piece> pieces = janggiService.getLatestPiecesOrNull();
        if (pieces != null) {
            outputView.announceLoadGame();
            return BoardFactory.getBoardWithPieces(pieces);
        }
        outputView.announceNewGame();
        Board board = getInitializedBoardByInput();
        janggiService.createNewGame();
        return board;
    }

    private void printResult(final Board board, final Turn turn) {
        Team winner = board.getWinner();
        outputView.printWinner(winner);
        outputView.printScore(Team.RED, board.calculateScoreByTeam(Team.RED, turn));
        outputView.printScore(Team.BLUE, board.calculateScoreByTeam(Team.BLUE, turn));
    }

    private Board getInitializedBoardByInput() {
        String blueHorseSide = inputView.readHorsePosition(Team.BLUE);
        String redHorseSide = inputView.readHorsePosition(Team.RED);
        return BoardFactory.getInitializedBoard(
                getPositionSide(InputConverter.extractLeftHorseSide(blueHorseSide)),
                getPositionSide(InputConverter.extractRightHorseSide(blueHorseSide)),
                getPositionSide(InputConverter.extractLeftHorseSide(redHorseSide)),
                getPositionSide(InputConverter.extractRightHorseSide(redHorseSide))
        );
    }

    private HorseSide getPositionSide(final String position) {
        if (position.equals("마상")) {
            return HorseSide.LEFT;
        }
        if (position.equals("상마")) {
            return HorseSide.RIGHT;
        }
        throw new IllegalArgumentException("올바르지 않은 입력입니다.");
    }

    private void movePieceByPieceMovement(final Team team, final String pieceMovement, final Board board) {
        Position beforePosition = getBeforePosition(pieceMovement);
        Position afterPosition = getAfterPosition(pieceMovement);
        board.movePiece(team, beforePosition, afterPosition);
    }

    private Position getBeforePosition(final String pieceMovement) {
        int parsedInt = Integer.parseInt(pieceMovement.split(" ")[0]);
        return new Position(parsedInt / 10, parsedInt % 10);
    }

    private Position getAfterPosition(final String pieceMovement) {
        int parsedInt = Integer.parseInt(pieceMovement.split(" ")[1]);
        return new Position(parsedInt / 10, parsedInt % 10);
    }
}