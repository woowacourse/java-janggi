package janggi.repository;

import janggi.model.Janggi;
import janggi.model.position.absolute.Position;
import java.util.Optional;

public class JdbcBoardRepository implements BoardRepository{

    @Override
    public Optional<Janggi> findInProgressGame() {
        //진행 중인 게임이 있으면 해당 게임을, 없으면 Optional.empty()를 반환한다.
        return Optional.empty();
    }

    @Override
    public  void updateBoardWith(Position from, Position to) {
       //from에 있는 기물을 to로 옮긴다.
    }

    @Override
    public void deleteGame() {
        //진행 중인 게임이 있으면 해당 게임을 삭제한다.
    }
}
