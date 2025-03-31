package janggi;

import janggi.domain.GameState;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Side;
import janggi.service.BoardPieceService;
import janggi.service.GameService;
import janggi.service.PieceService;
import janggi.view.InputView;
import janggi.view.OutputView;

import java.util.List;
import java.util.Map;

public class JanggiGame {

    private final InputView inputView;
    private final OutputView outputView;
    private final BoardPieceService boardPieceService;
    private final PieceService pieceService;
    private final GameService gameService;

    public JanggiGame(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.boardPieceService = new BoardPieceService();
        this.pieceService = new PieceService();
        this.gameService = new GameService();
    }

    public void playGame() {
        JanggiBoard board = setUpGame();
        playTurns(board);
    }

    private JanggiBoard setUpGame() {
        Map<Integer, String> games = gameService.findAllGames();
        JanggiBoard board;
        if (games.isEmpty()) {
            int newGameId = gameService.makeNewGame();
            pieceService.initializePieceTable();

            board = boardPieceService.initializeBoardPieces(newGameId);
        } else {
            board = boardPieceService.loadBoardPieces();
        }
        return board;
    }

    private void playTurns(JanggiBoard board) {
        List<GameState> turns = gameService.getTurns();
        if (turns.contains(GameState.ENDED)) {
            outputView.printAlreadyEnded();
            return ;
        }
        while (true) {
            for (GameState turn : turns) {
                gameService.updateGameState(turn);

                Side side = Side.getSideByName(turn.getName());
                Piece catchedPiece = playTurn(side, board);

                if (board.checkGameIsOver(side)) {
                    outputView.printEndMessage(side, catchedPiece);
                    printTotalScores(board);
                    gameService.updateGameState(GameState.ENDED);
                    return ;
                }
            }
        }
    }

    private Piece playTurn(final Side side, final JanggiBoard board) {
        while (true) {
            try {
                outputView.printBoard(board);
                outputView.printTurn(side);
                Position selectedPiecePosition = inputView.selectPiece();

                List<Position> reachableDestinations = computeReachableDestinations(side, board, selectedPiecePosition);
                return processMove(board, selectedPiecePosition, reachableDestinations);
            } catch (IllegalArgumentException | IllegalStateException e) {
                outputView.printExceptionMessage(e);
            }
        }
    }

    private List<Position> computeReachableDestinations(final Side side, final JanggiBoard board, final Position selectedPiecePosition) {
        List<Position> reachablePositions = boardPieceService.computeReachableDestination(board, side, selectedPiecePosition);
        outputView.printReachableDestinations(reachablePositions);
        return reachablePositions;
    }

    private Piece processMove(final JanggiBoard board, final Position selectedPiecePosition, final List<Position> reachableDestinations) {
        Position destination = inputView.askMoveDestination();
        validateSelectedDestination(destination, reachableDestinations);

        Piece catchedPiece = boardPieceService.moveOrCatchPiece(board, selectedPiecePosition, destination);
        outputView.printMoveResult(catchedPiece);
        return catchedPiece;
    }

    private void printTotalScores(final JanggiBoard board) {
        Map<Side, Integer> sideTotalScores = boardPieceService.sumTotalPoints(board);
        outputView.printTotalScores(sideTotalScores.get(Side.CHO), sideTotalScores.get(Side.HAN));
    }

    private void validateSelectedDestination(final Position destination, final List<Position> reachableDestinations) {
        if (!reachableDestinations.contains(destination)) {
            throw new IllegalArgumentException("[ERROR] 선택한 목적지로 이동할 수 없습니다.");
        }
    }
}
