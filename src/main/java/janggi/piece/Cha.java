package janggi.piece;

import janggi.setting.CampType;
import janggi.value.Position;
import java.util.List;
import java.util.stream.IntStream;

public class Cha implements Piece {

    private static final int score = 13;
    private static final int height = 0;
    private static final List<Integer> xPositions = List.of(0, 8);

    private final PieceType pieceType;
    private final Position position;

    private Cha(final Position position) {
        this.pieceType = PieceType.CHA;
        this.position = position;
    }

    public static Cha from(final Position position) {
        return new Cha(position);
    }

    public static List<Cha> generateInitialChas(final CampType campType) {
        int yPosition = Math.abs(campType.getStartYPosition() - height);
        return xPositions.stream()
                .map(xPosition -> new Cha(new Position(xPosition, yPosition)))
                .toList();
    }

    @Override
    public Cha move(final Position destination, final List<Piece> enemy, final List<Piece> allies) {
        boolean isAble = ableToMove(destination, enemy, allies);
        if (!isAble) {
            throw new IllegalArgumentException("[ERROR] 이동이 불가능합니다.");
        }
        return new Cha(destination);
    }

    @Override
    public boolean ableToMove(Position destination, List<Piece> enemy, List<Piece> allies) {
        if (!isRuleOfMove(destination)) {
            return false;
        }
        return isNotHurdle(destination, enemy, allies);
    }

    private boolean isRuleOfMove(Position destination) {
        return position.getX() == destination.getX() || position.getY() == destination.getY();
    }

    private boolean isNotHurdle(Position destination, List<Piece> enemy, List<Piece> allies) {
        // 경로상에 있는 좌표 리스트 구하기
        List<Position> positions = calculatePositions(destination);
        for (Position position : positions) {
            if (position.equals(destination)) {
                continue;
            }
            boolean isEnemyExistence = enemy.stream()
                    .anyMatch(enemyPiece -> enemyPiece.getPosition().equals(position));
            if (isEnemyExistence) {
                return false;
            }
        }
        for (Position position : positions) {
            boolean isAlliesExistence = allies.stream()
                    .anyMatch(alliesPiece -> alliesPiece.getPosition().equals(position));
            if (isAlliesExistence) {
                return false;
            }
        }
        return true;
    }

    private List<Position> calculatePositions(Position destination) {
        if (position.getX() == destination.getX()) {
            if (position.getY() > destination.getY()) {
                return IntStream.rangeClosed(destination.getY(), position.getY())
                        .mapToObj(y -> new Position(position.getX(), y))
                        .toList();
            }
            return IntStream.rangeClosed(position.getY(), destination.getY())
                    .mapToObj(y -> new Position(position.getX(), y))
                    .toList();
        }
        if (position.getX() > destination.getX()) {
            return IntStream.rangeClosed(destination.getX(), position.getX())
                    .mapToObj(x -> new Position(x, position.getY()))
                    .toList();
        }
        return IntStream.rangeClosed(position.getX(), destination.getX())
                .mapToObj(x -> new Position(x, position.getY()))
                .toList();
    }

    @Override
    public boolean checkPieceType(PieceType pieceType) {
        return this.pieceType == pieceType;
    }


    @Override
    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
