package game;

import dao.BoardDao;
import dao.PieceDao;
import piece.Piece;
import piece.PieceType;
import location.Position;
import location.PathUtility;
import piece.Pieces;
import view.AnswerType;
import view.InputView;
import view.OutputView;

public class JanggiGame {

    private static final int HORIZONTAL_START = 1;
    private static final int HORIZONTAL_END = 9;
    private static final int VERTICAL_START = 1;
    private static final int VERTICAL_END = 10;

    private final BoardDao boardDao;
    private final PieceDao pieceDao;
    private final BoardInitializer boardInitializer;

    public JanggiGame(BoardDao boardDao, PieceDao pieceDao) {
        this.boardDao = boardDao;
        this.pieceDao = pieceDao;
        this.boardInitializer = new BoardInitializer(pieceDao);
    }

    public void showInitialBoard() {
        OutputView.displayBoard(pieceDao);
    }

    public void run() {
        Team winTeam = play();

        Pieces catchPiecesByGreen = pieceDao.findCatchAllBy(Team.GREEN);
        Pieces catchPiecesByRed = pieceDao.findCatchAllBy(Team.RED);
        double greenPlayerTotalScore = Team.calculateFinalScore(Team.GREEN, catchPiecesByGreen);
        double redPlayerTotalScore = Team.calculateFinalScore(Team.RED, catchPiecesByRed);

        if (winTeam.isNotDecided()) {
            winTeam = decideWinTeam(greenPlayerTotalScore, redPlayerTotalScore);
            OutputView.displayResult(winTeam, greenPlayerTotalScore, redPlayerTotalScore);
            return;
        }
        boardInitializer.initialize();
        boardDao.resetCurrentTeam();
    }

    private Team play() {
        while (true) {
            Team currentTeam = boardDao.findCurrentTeam();
            Pieces currentPieces = pieceDao.findByTeam(currentTeam);

            if (requestEndGame().isPositive()) {
                return Team.NONE;
            }
            Position start = requestMovementStartPosition(currentPieces);
            Position end = requestMovementEndPosition();

            move(currentPieces, start, end);

            Team opponent = Team.findOpponentBy(currentTeam);
            boolean isGeneralCatch = catchPiece(opponent, end);
            boardDao.updateCurrentTeam(opponent);

            if (isGeneralCatch) {
                return currentTeam;
            }
            OutputView.displayBoard(pieceDao);
        }
    }

    private AnswerType requestEndGame() {
        return InputView.requestEndGame();
    }

    private Position requestMovementStartPosition(Pieces currentPieces) {
        while (true) {
            Position start = InputView.requestMoveStartPosition();
            validateRange(start);

            if (currentPieces.isContainedPieceAtPosition(start)) {
                return start;
            }
            OutputView.displayWrongPoint();
        }
    }

    private Position requestMovementEndPosition() {
        Position end = InputView.requestMovementEndPosition();
        validateRange(end);
        return end;
    }

    public void move(Pieces currentPieces, Position start, Position end) {
        currentPieces.checkNotExistedPieceInPosition(end);
        PathUtility.checkNotSameStartWithEnd(start, end);

        Pieces allPieces = pieceDao.findAll();
        Piece piece = currentPieces.getByPosition(start);

        piece.validateDestination(end);
        piece.validatePaths(allPieces, end);
        piece.move(end);

        pieceDao.update(piece, end);
    }

    private boolean catchPiece(Team opponent, Position end) {
        Pieces opponentPieces = pieceDao.findByTeam(opponent);
        if (opponentPieces.isContainedPieceAtPosition(end)) {
            Piece opponentPiece = opponentPieces.getByPosition(end);
            opponentPiece.catchByOpponent();
            return PieceType.isGeneral(opponentPiece);
        }
        return false;
    }

    private Team decideWinTeam(double greenPlayerTotalScore, double redPlayerTotalScore) {
        if (greenPlayerTotalScore > redPlayerTotalScore) {
            return Team.GREEN;
        }
        return Team.RED;
    }

    private void validateRange(Position position) {
        if (position.x() < HORIZONTAL_START || position.x() > HORIZONTAL_END ||
                position.y() < VERTICAL_START || position.y() > VERTICAL_END) {
            throw new IllegalArgumentException("[ERROR] 위치할 수 없는 좌표입니다.");
        }
    }
}
