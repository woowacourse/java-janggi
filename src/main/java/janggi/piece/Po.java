package janggi.piece;

import janggi.setting.CampType;
import janggi.value.JanggiPosition;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Po extends Piece {

    private Po(final JanggiPosition janggiPosition) {
        super(PieceType.PO, janggiPosition);
    }

    public static Po from(final JanggiPosition janggiPosition) {
        return new Po(janggiPosition);
    }

    public static List<Po> generateInitialPos(final CampType campType) {
        int yPosition = Math.abs(campType.getStartYPosition() - PieceType.PO.getHeight());
        return PieceType.PO.getDefaultXPositions().stream()
                .map(xPosition -> new Po(new JanggiPosition(xPosition, yPosition)))
                .toList();
    }

    @Override
    public Po move(final JanggiPosition destination, final List<Piece> enemy, final List<Piece> allies) {
        boolean isAble = ableToMove(destination, enemy, allies);
        if (!isAble) {
            throw new IllegalArgumentException("[ERROR] 이동이 불가능합니다.");
        }
        return new Po(destination);
    }

    @Override
    public boolean ableToMove(JanggiPosition destination, List<Piece> enemy, List<Piece> allies) {
        //목적지 일직선 상에 있는지 있다면 리턴 false
        if (!isRuleOfMove(destination)) {
            return false;
        }
        //목적지 위치에 아군이 있다면 리턴 false
        boolean isInDestination = isAlliesInDestination(destination, allies);
        if (isInDestination) {
            return false;
        }

        //현재 위치와 목적지(미포함) 사이에 좌표리스트 계산
        List<JanggiPosition> pathJanggiPositions = calculatePositions(destination);

        //좌표리스트 아군 검색
        List<Piece> alliesInPath = searchPieceInPath(allies, pathJanggiPositions);

        //좌표리스트 적군 검색
        List<Piece> enemyInPath = searchPieceInPath(enemy, pathJanggiPositions);
        
        //아군이나 적군이 없다면 리턴 false
        if (alliesInPath.isEmpty() && enemyInPath.isEmpty()) {
            return false;
        }

        //아군이나 적군사이에 포가 있다면 리턴 false
        long alliesPoCountInPath = countPoInPath(alliesInPath);
        long enemyPoCountInPath = countPoInPath(enemyInPath);
        if (alliesPoCountInPath + enemyPoCountInPath != 0) {
            return false;
        }

        //아군과 적군의 합이 두개이상일 경우 리턴 false
        if (alliesInPath.size() + enemyInPath.size() >= 2) {
            return false;
        }

        return true;
    }

    private long countPoInPath(List<Piece> alliesInPath) {
        return alliesInPath.stream()
                .filter(alliesPiece -> alliesPiece.checkPieceType(PieceType.PO))
                .count();
    }

    private List<Piece> searchPieceInPath(List<Piece> allies, List<JanggiPosition> pathJanggiPositions) {
        List<Piece> alliesInPath = new ArrayList<>();
        for (JanggiPosition pathJanggiPosition : pathJanggiPositions) {
            allies.stream()
                    .filter(alliesPiece -> alliesPiece.getPosition().equals(pathJanggiPosition))
                    .forEach(alliesInPath::add);
        }
        return alliesInPath;
    }

    private boolean isAlliesInDestination(JanggiPosition destination, List<Piece> allies) {
        return allies.stream()
                .anyMatch(alliesPiece -> alliesPiece.getPosition().equals(destination));
    }

    private boolean isRuleOfMove(JanggiPosition destination) {
        return getPosition().getX() == destination.getX() || getPosition().getY() == destination.getY();
    }


    private List<JanggiPosition> calculatePositions(JanggiPosition destination) {
        if (getPosition().getX() == destination.getX()) {
            if (getPosition().getY() > destination.getY()) {
                return IntStream.rangeClosed(destination.getY() - 1, getPosition().getY())
                        .filter(y -> y > 0)
                        .mapToObj(y -> new JanggiPosition(getPosition().getX(), y))
                        .toList();
            }
            return IntStream.rangeClosed(getPosition().getY(), destination.getY() - 1)
                    .filter(y -> y > 0)
                    .mapToObj(y -> new JanggiPosition(getPosition().getX(), y))
                    .toList();
        }
        if (getPosition().getX() > destination.getX()) {
            return IntStream.rangeClosed(destination.getX() - 1, getPosition().getX())
                    .filter(x -> x > 0)
                    .mapToObj(x -> new JanggiPosition(x, getPosition().getY()))
                    .toList();
        }

        return IntStream.rangeClosed(getPosition().getX(), destination.getX() - 1)
                .filter(x -> x > 0)
                .mapToObj(x -> new JanggiPosition(x, getPosition().getY()))
                .toList();
    }
}
