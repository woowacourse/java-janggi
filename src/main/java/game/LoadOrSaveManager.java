package game;

import dao.BoardDao;
import dao.TurnDao;
import dto.PieceDto;
import dto.TurnDto;
import java.util.List;
import piece.Country;

public class LoadOrSaveManager {
    private final BoardDao boardDao = new BoardDao();
    private final TurnDao turnDao = new TurnDao();

    public Board loadBoard() {

        List<PieceDto> saved = boardDao.loadAll();
        if (!saved.isEmpty()) {
            System.out.println("저장된 게임을 불러옵니다.");
            return Board.toBoard(saved);
        }
        throw new IllegalArgumentException("저장된 게임이 없습니다.");

    }

    public Country loadTurnCountry() {
        TurnDto saved = turnDao.loadTurnCountry();
        return Country.of(saved.country());
    }

    public void save(Board board, Country country) {
        boardDao.deleteAll();
        turnDao.deleteAll();
        boardDao.saveAll(PieceDto.toDtoFromBoard(board));
        turnDao.saveTurnCountry(new TurnDto(country.name()));
    }

    public void clear() {
        boardDao.deleteAll();
    }
}
