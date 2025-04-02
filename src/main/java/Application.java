import dao.BoardDao;
import dao.PieceInfo;
import dao.TurnDao;
import janggiGame.Board;
import janggiGame.Position;
import janggiGame.arrangement.ArrangementOption;
import janggiGame.arrangement.ArrangementStrategy;
import janggiGame.piece.character.Dynasty;
import java.util.List;
import service.converter.BoardConverter;
import service.converter.DBConverter;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        BoardDao boardDao = new BoardDao();
        TurnDao turnDao = new TurnDao();

        Board board = createBoard(boardDao, inputView);

        Dynasty[] dynasties = Dynasty.values();
        int turn = turnDao.readTurnDB();

        while (true) {
            outputView.printBoard(board.getSurvivedPieces());
            try {
                Dynasty currentDynasty = dynasties[turn % 2];

                List<Position> movement = inputView.readPieceMovement(currentDynasty);

                board.processTurn(currentDynasty, movement.getFirst(), movement.getLast());

                printTotalScoreByDynasty(board, outputView);

                if (board.isKingDead(Dynasty.CHO)) {

                    outputView.printWinner(Dynasty.HAN);
                    resetGameStatus(boardDao, turnDao);
                    break;
                }

                if (board.isKingDead(Dynasty.HAN)) {
                    outputView.printWinner(Dynasty.CHO);
                    resetGameStatus(boardDao, turnDao);
                    break;
                }

                List<PieceInfo> survivedPiece = BoardConverter.convertToPieceInfos(board.getSurvivedPieces());
                boardDao.deleteBoardDB(); // 전체 삭제
                boardDao.createBoardDB(survivedPiece); // 전체 추가

                turnDao.incrementTurnDB();
                turn = turnDao.readTurnDB();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static Board createBoard(BoardDao boardDao, InputView inputView) {
        List<PieceInfo> pieceEntities = boardDao.readBoardDB();

        if (pieceEntities.isEmpty()) {
            Board newBoard = settingJanggiGame(inputView);
            List<PieceInfo> settingPieces = BoardConverter.convertToPieceInfos(newBoard.getSurvivedPieces());
            boardDao.createBoardDB(settingPieces);
            return newBoard;
        }
        return DBConverter.convertToBoard(pieceEntities);
    }

    private static Board settingJanggiGame(InputView inputView) {
        ArrangementStrategy hanStrategy = choiceArrangementStrategy(Dynasty.HAN, inputView);
        ArrangementStrategy choStrategy = choiceArrangementStrategy(Dynasty.CHO, inputView);
        return new Board(hanStrategy, choStrategy);
    }

    private static ArrangementStrategy choiceArrangementStrategy(Dynasty dynasty, InputView inputView) {
        int option = inputView.readArrangementStrategyByDynasty(dynasty);
        return ArrangementOption.findBy(option).getArrangementStrategy();
    }

    private static void printTotalScoreByDynasty(Board board, OutputView outputView) {
        double hanTotalScore = board.calculateTotalPoints(Dynasty.HAN);
        double choTotalScore = board.calculateTotalPoints(Dynasty.CHO);

        outputView.printScore(hanTotalScore, choTotalScore);
    }

    private static void resetGameStatus(BoardDao boardDao, TurnDao turnDao) {
        boardDao.deleteBoardDB();
        turnDao.resetTurnDB();
    }
}
