import dao.BoardDao;
import dao.PieceEntity;
import dao.TurnDao;
import dao.converter.BoardConverter;
import janggiGame.Board;
import janggiGame.Position;
import janggiGame.arrangement.ArrangementOption;
import janggiGame.arrangement.ArrangementStrategy;
import janggiGame.piece.character.Dynasty;
import java.util.List;
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
        int turn = turnDao.readTurnEntity();

        while (true) {
            outputView.printBoard(board.getSurvivedPieces());
            try {
                Dynasty currentDynasty = dynasties[turn % 2];

                List<Position> movement = inputView.readPieceMovement(currentDynasty);

                board.processTurn(currentDynasty, movement.getFirst(), movement.getLast());

                double hanTotalScore = board.calculateTotalPoints(Dynasty.HAN);
                double choTotalScore = board.calculateTotalPoints(Dynasty.CHO);

                outputView.printScore(hanTotalScore, choTotalScore);

                if (board.isKingDead(Dynasty.CHO)) {

                    outputView.printWinner(Dynasty.HAN);
                    boardDao.deleteBoardEntity();
                    turnDao.resetTurnEntity();
                    break;
                }

                if (board.isKingDead(Dynasty.HAN)) {
                    outputView.printWinner(Dynasty.CHO);
                    boardDao.deleteBoardEntity();
                    turnDao.resetTurnEntity();
                    break;
                }

                List<PieceEntity> survivedPiece = BoardConverter.convertToPieceEntities(board.getSurvivedPieces());
                boardDao.deleteBoardEntity(); // 전체 삭제
                boardDao.createBoardEntity(survivedPiece); // 전체 추가

                turnDao.incrementTurn();
                turn = turnDao.readTurnEntity();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static Board createBoard(BoardDao boardDao, InputView inputView) {
        List<PieceEntity> pieceEntities = boardDao.readBoardEntity();

        if (pieceEntities.isEmpty()) {
            Board newBoard = settingJanggiGame(inputView);
            List<PieceEntity> settingPieces = BoardConverter.convertToPieceEntities(newBoard.getSurvivedPieces());
            boardDao.createBoardEntity(settingPieces);
            return newBoard;
        }
        return BoardConverter.convertToBoard(pieceEntities);
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
}
