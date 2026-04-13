import domain.board.Board;
import domain.piece.Camp;
import domain.piece.Piece;
import domain.position.ElephantFormation;
import domain.position.Position;
import repository.JanggiRepository;

import java.util.Map;

public class JanggiService {

    private final JanggiRepository janggiRepository;
    private int gameId;

    public JanggiService(JanggiRepository janggiRepository) {
        this.janggiRepository = janggiRepository;
    }

    public Board loadBoard() {
        Board board = new Board();
        Map<Position, Piece> boardStatus = janggiRepository.readBoard(gameId);
        for (Position position : boardStatus.keySet()) {
            board.locatePiece(position, boardStatus.get(position));
        }
        return board;
    }

    public Camp loadTurn(boolean loadSaveBoard) {
        if(loadSaveBoard) {
            gameId = janggiRepository.getLatestGameId();
            return janggiRepository.readTurn(gameId);
        }

        gameId = janggiRepository.createNewGame(Camp.CHO);
        return Camp.CHO;
    }

    public void saveGame(Map<Position, Piece> boardStatus, Camp camp) {
        janggiRepository.saveGame(gameId, boardStatus, camp);
    }

    public Board generateNewBoard(Map<Camp, ElephantFormation> initBoardInfo) {
        Board board = new Board();
        for (Map.Entry<Camp, ElephantFormation> entry : initBoardInfo.entrySet()) {
            board.generatePiecesBy(entry.getKey(), entry.getValue());
        }

        return board;
    }

}
