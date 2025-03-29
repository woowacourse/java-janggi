import dao.BoardDao;
import dao.PieceEntity;
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

        Board board = createBoard(inputView);

        Dynasty[] dynasties = Dynasty.values();
        int turn = BoardDao.readTurnEntity();

        while (true) {
            outputView.printBoard(board.getSurvivedPieces());
            try {
                Dynasty currentDynasty = dynasties[turn % 2];

                List<Position> movement = inputView.readPieceMovement(currentDynasty);

                board.processTurn(currentDynasty, movement.getFirst(), movement.getLast());

                double hanTotalScore = board.calculateTotalPoints(Dynasty.HAN);
                double choTotalScore = board.calculateTotalPoints(Dynasty.CHO);

                if (board.isKingDead(Dynasty.CHO)) {
                    outputView.printScore(hanTotalScore, choTotalScore);
                    outputView.printWinner(Dynasty.HAN);
                    BoardDao.deleteBoardEntity();
                    BoardDao.resetTurnEntity();
                    break;
                }

                if (board.isKingDead(Dynasty.HAN)) {
                    outputView.printScore(hanTotalScore, choTotalScore);
                    outputView.printWinner(Dynasty.CHO);
                    BoardDao.deleteBoardEntity();
                    BoardDao.resetTurnEntity();
                    break;
                }

                List<PieceEntity> survivedPiece = BoardConverter.convertToPieceEntities(board.getSurvivedPieces());
                BoardDao.deleteBoardEntity(); // 전체 삭제
                BoardDao.addPieceEntitiesToBoardEntity(survivedPiece); // 전체 추가

                BoardDao.incrementTurn();
                turn = BoardDao.readTurnEntity();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static Board createBoard(InputView inputView) {
        List<PieceEntity> pieceEntities = BoardDao.readPieceEntitiesFromBoardEntity();

        if (pieceEntities.isEmpty()) {
            Board newBoard = settingJanggiGame(inputView);
            List<PieceEntity> settingPieces = BoardConverter.convertToPieceEntities(newBoard.getSurvivedPieces());
            BoardDao.addPieceEntitiesToBoardEntity(settingPieces);
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
