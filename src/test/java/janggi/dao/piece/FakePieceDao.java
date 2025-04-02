package janggi.dao.piece;

import janggi.dto.PieceDto;
import janggi.dto.PieceMove;
import janggi.piece.players.Team;
import janggi.position.Position;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FakePieceDao implements PieceDao {

    private final List<PieceDto> dtos = new ArrayList<>();

    @Override
    public List<PieceDto> select(final Team team) {
        return dtos.stream()
                .filter(dto -> dto.team().equals(team))
                .toList();
    }

    @Override
    public List<PieceDto> selectAll() {
        return Collections.unmodifiableList(dtos);
    }

    @Override
    public void insert(final PieceDto pieceDto) {
        dtos.add(pieceDto);
    }

    @Override
    public void update(final PieceMove pieceMove) {
        final Position currentPosition = pieceMove.currentPosition();
        final PieceDto pieceDto = new PieceDto(pieceMove.team(), pieceMove.pieceType(), currentPosition.getY(),
                currentPosition.getX());
        dtos.remove(pieceDto);
        final Position arrivalPosition = pieceMove.arrivalPosition();
        final PieceDto updatedDto = new PieceDto(pieceMove.team(), pieceMove.pieceType(), arrivalPosition.getY(),
                arrivalPosition.getX());
        dtos.add(updatedDto);
    }

    @Override
    public void delete(final PieceMove pieceMove) {
        final Position arrivalPosition = pieceMove.arrivalPosition();
        final PieceDto pieceDto = new PieceDto(pieceMove.team().getOppositeTeam(), pieceMove.caughtPieceType(),
                arrivalPosition.getY(), arrivalPosition.getX());
        dtos.remove(pieceDto);
        System.out.println(pieceDto);
    }

    @Override
    public void deleteAll() {
        dtos.clear();
    }
}
