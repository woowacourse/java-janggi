package janggi;

import janggi.domain.board.BoardInitializer;
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

    public JanggiBoard setUpGame() {
        Map<Integer, String> games = gameService.findAllGames();
        JanggiBoard board;
        if (games.isEmpty()) {
            int newGameId = gameService.makeNewGame();
            pieceService.initializePieceTable();

            board = JanggiBoard.initializeWithPieces();
            BoardInitializer boardInitializer = new BoardInitializer(boardPieceService);
            boardInitializer.initializeBoard(newGameId, board);
        } else {
            Map<Position, Piece> positionPieces = boardPieceService.findAllBoardPieces();
            board = JanggiBoard.fillEmptyPiece(positionPieces);
        }
        return board;
    }

    public void play(JanggiBoard board) {
        List<Side> turns = getTurns();
        if (turns.contains(Side.NONE)) {
            System.out.println("이미 게임이 종료되었습니다.");
            return ;
        }
        while (true) {
            for (Side turn : turns) {
                gameService.updateGameState(getGameId(), turn);
                Piece catchedPiece = playTurn(turn, board);

                if (board.checkGameIsOver(turn)) {
                    outputView.printEndMessage(turn, catchedPiece);
                    printTotalScores(board);
                    gameService.updateGameState(getGameId(), Side.NONE);
                    return ;
                }
            }
        }
    }

    private List<Side> getTurns() {
        Side turn = gameService.getState(getGameId());
        return turn.getSideByState();
    }

    private Piece playTurn(final Side side, JanggiBoard board) {
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
        List<Position> reachablePositions = board.computeReachableDestination(side, selectedPiecePosition);
        outputView.printReachableDestinations(reachablePositions);
        return reachablePositions;
    }

    private Piece processMove(final JanggiBoard board, final Position selectedPiecePosition, final List<Position> reachableDestinations) {
        Position destination = inputView.askMoveDestination();
        validateSelectedDestination(destination, reachableDestinations);

        Piece catchedPiece = board.moveOrCatchPiece(selectedPiecePosition, destination);
        boardPieceService.updatePiecePosition(selectedPiecePosition, destination);
        outputView.printMoveResult(catchedPiece);
        return catchedPiece;
    }

    public void printTotalScores(final JanggiBoard board) {
        int choTotalScore = board.sumSideTotalScore(Side.CHO);
        int hanTotalScore = board.sumSideTotalScore(Side.HAN);
        outputView.printTotalScores(choTotalScore, hanTotalScore);
    }

    private void validateSelectedDestination(final Position destination, final List<Position> reachableDestinations) {
        if (!reachableDestinations.contains(destination)) {
            throw new IllegalArgumentException("[ERROR] 선택한 목적지로 이동할 수 없습니다.");
        }
    }

    private int getGameId() {
        Map<Integer, String> allGames = gameService.findAllGames();
        return allGames.keySet().stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElseThrow(() -> new IllegalStateException("[ERROR] 진행 중인 게임을 찾을 수 없습니다."));
    }
}
