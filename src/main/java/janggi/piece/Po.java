package janggi.piece;

import janggi.setting.CampType;
import janggi.value.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Po implements Piece {

    private static final int score = 7;
    private static final int height = 2;
    private static final List<Integer> xPositions = List.of(1, 7);

    private final PieceType pieceType;
    private final Position position;

    private Po(final Position position) {
        this.pieceType = PieceType.PO;
        this.position = position;
    }

    public static Po from(final Position position) {
        return new Po(position);
    }

    public static List<Po> generateInitialPos(final CampType campType) {
        int yPosition = Math.abs(campType.getStartYPosition() - height);
        return xPositions.stream()
                .map(xPosition -> new Po(new Position(xPosition, yPosition)))
                .toList();
    }

    @Override
    public Po move(final Position destination, final List<Piece> enemy, final List<Piece> allies) {
        boolean isAble = ableToMove(destination, enemy, allies);
        if (!isAble) {
            throw new IllegalArgumentException("[ERROR] 이동이 불가능합니다.");
        }
        return new Po(destination);
    }

    @Override
    public boolean ableToMove(Position destination, List<Piece> enemy, List<Piece> allies) {
        // 목적지 일직선 상에 있는지 있다면 리턴 false
        if (!isRuleOfMove(destination)) {
            return false;
        }
        //목적지 위치에 아군이 있다면 리턴 false
        boolean isInDestination = allies.stream()
                .anyMatch(alliesPiece -> alliesPiece.getPosition().equals(destination));
        if (isInDestination) {
            return false;
        }

        //현재 위치와 목적지(미포함) 사이에 좌표리스트 계산
        List<Position> pathPositions = calculatePositions(destination);

//        - 좌표리스트 아군 검색
        List<Piece> alliesInPath = new ArrayList<>();
        for (Position pathPosition : pathPositions) {
            allies.stream()
                    .filter(alliesPiece -> alliesPiece.getPosition().equals(pathPosition))
                    .forEach(alliesInPath::add);
        }

//        - 좌표리스트 적군 검색
        List<Piece> enemyInPath = new ArrayList<>();
        for (Position pathPosition : pathPositions) {
            enemy.stream()
                    .filter(enemyPiece -> enemyPiece.getPosition().equals(pathPosition))
                    .forEach(enemyInPath::add);
        }

//        - 아군이나 적군이 없다면 리턴 false
        if (alliesInPath.isEmpty() && enemyInPath.isEmpty()) {
            return false;
        }

//                - 아군이나 적군사이에 포가 있다면 리턴 false
        long alliesPoCountInPath = alliesInPath.stream()
                .filter(alliesPiece -> alliesPiece.checkPieceType(PieceType.PO))
                .count();
        long enemyPoCountInPath = enemyInPath.stream()
                .filter(enemyPiece -> enemyPiece.checkPieceType(PieceType.PO))
                .count();
        if (alliesPoCountInPath + enemyPoCountInPath != 0) {
            return false;
        }

//                - 아군과 적군의 합이 두개이상일 경우 리턴 false
        if (alliesInPath.size() + enemyInPath.size() >= 2) {
            return false;
        }

        return true;
    }

    private boolean isRuleOfMove(Position destination) {
        return position.getX() == destination.getX() || position.getY() == destination.getY();
    }

    private List<Position> calculatePositions(Position destination) {
        if (position.getX() == destination.getX()) {
            if (position.getY() > destination.getY()) {
                return IntStream.rangeClosed(destination.getY() - 1, position.getY())
                        .mapToObj(y -> new Position(position.getX(), y))
                        .toList();
            }
            return IntStream.rangeClosed(position.getY(), destination.getY() - 1)
                    .mapToObj(y -> new Position(position.getX(), y))
                    .toList();
        }
        if (position.getX() > destination.getX()) {
            return IntStream.rangeClosed(destination.getX() - 1, position.getX())
                    .mapToObj(x -> new Position(x, position.getY()))
                    .toList();
        }
        return IntStream.rangeClosed(position.getX(), destination.getX() - 1)
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
