package game;

import dao.BoardDao;
import dao.TurnDao;
import dto.PieceDto;
import dto.TurnDto;
import java.util.List;
import piece.Country;
import position.Position;
import view.InputView;
import view.OutputView;

public class JanggiGame {

    private final InputView inputView;
    private final OutputView outputView;
    private final BoardDao boardDao = new BoardDao(); // 추가
    private final TurnDao turnDao = new TurnDao();
    private Country turnCountry = Country.CHO;

    public JanggiGame(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        boolean isLoad = inputView.askLoadSavedGame();
        Board board = setBoard(isLoad);
        setTurnCountry(isLoad);

        outputView.displayBoard(board);
        while (true) {
            if (isGameFinished(board)) {
                boardDao.deleteAll();
                outputView.displayGameFinished();
                break;
            }
            if (!playTurn(board)) {
                break;
            }
        }
    }

    private boolean playTurn(final Board board) {
        try {
            outputView.displayTurnCountry(turnCountry);
            outputView.displayCountryScore(turnCountry, board.getCountryScore(turnCountry));
            List<String> moveInfo = inputView.readMoveCommand();

            if (moveInfo.get(0).equals("quit")) {
                saveBoard(board);
                System.out.println("게임을 저장하고 종료합니다.");
                return false;
            }

            Position fromPosition = Position.of(moveInfo.get(0), moveInfo.get(1));
            Position toPosition = Position.of(moveInfo.get(2), moveInfo.get(3));

            board.movePiece(fromPosition, toPosition, turnCountry);
            outputView.displayBoard(board);
            turnCountry = turnCountry.reverseCountry();

        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return true;
    }

    private Board setBoard(boolean isLoad) {
        if (isLoad) {
            List<PieceDto> saved = boardDao.loadAll();
            if (!saved.isEmpty()) {
                System.out.println("저장된 게임을 불러옵니다.");
                return Board.toBoard(saved);
            } else {
                System.out.println("저장된 게임이 없습니다. 새 게임을 시작합니다.");
            }
        }
        StartSet choSet = inputView.getStartingPosition(Country.CHO);
        StartSet hanSet = inputView.getStartingPosition(Country.HAN);
        return new Board(choSet, hanSet);
    }

    private void setTurnCountry(boolean isLoad) {
        if (isLoad) {
            TurnDto saved = turnDao.loadTurnCountry();
            turnCountry = Country.of(saved.country());
        }
    }

    private void saveBoard(Board board) {
        List<PieceDto> dtoList = PieceDto.toDtoFromBoard(board);
        boardDao.saveAll(dtoList);
    }

    private boolean isGameFinished(Board board) {
        return board.isGeneralDead();
    }
}
