package janggi.board;

import static janggi.position.Column.A;
import static janggi.position.Column.B;
import static janggi.position.Column.C;
import static janggi.position.Column.D;
import static janggi.position.Column.E;
import static janggi.position.Column.F;
import static janggi.position.Column.G;
import static janggi.position.Column.H;
import static janggi.position.Column.I;
import static janggi.position.Row.ZERO;
import static janggi.position.Row.ONE;
import static janggi.position.Row.TWO;
import static janggi.position.Row.THREE;
import static janggi.position.Row.FOUR;
import static janggi.position.Row.FIVE;
import static janggi.position.Row.SIX;
import static janggi.position.Row.SEVEN;
import static janggi.position.Row.EIGHT;
import static janggi.position.Row.NINE;

import janggi.position.Position;

public enum InitPositions {

    A0(new Position(A, ZERO)),
    A1(new Position(A, ONE)),
    A2(new Position(A, TWO)),
    A3(new Position(A, THREE)),
    A4(new Position(A, FOUR)),
    A5(new Position(A, FIVE)),
    A6(new Position(A, SIX)),
    A7(new Position(A, SEVEN)),
    A8(new Position(A, EIGHT)),
    A9(new Position(A, NINE)),

    B0(new Position(B, ZERO)),
    B1(new Position(B, ONE)),
    B2(new Position(B, TWO)),
    B3(new Position(B, THREE)),
    B4(new Position(B, FOUR)),
    B5(new Position(B, FIVE)),
    B6(new Position(B, SIX)),
    B7(new Position(B, SEVEN)),
    B8(new Position(B, EIGHT)),
    B9(new Position(B, NINE)),

    C0(new Position(C, ZERO)),
    C1(new Position(C, ONE)),
    C2(new Position(C, TWO)),
    C3(new Position(C, THREE)),
    C4(new Position(C, FOUR)),
    C5(new Position(C, FIVE)),
    C6(new Position(C, SIX)),
    C7(new Position(C, SEVEN)),
    C8(new Position(C, EIGHT)),
    C9(new Position(C, NINE)),

    D0(new Position(D, ZERO)),
    D1(new Position(D, ONE)),
    D2(new Position(D, TWO)),
    D3(new Position(D, THREE)),
    D4(new Position(D, FOUR)),
    D5(new Position(D, FIVE)),
    D6(new Position(D, SIX)),
    D7(new Position(D, SEVEN)),
    D8(new Position(D, EIGHT)),
    D9(new Position(D, NINE)),

    E0(new Position(E, ZERO)),
    E1(new Position(E, ONE)),
    E2(new Position(E, TWO)),
    E3(new Position(E, THREE)),
    E4(new Position(E, FOUR)),
    E5(new Position(E, FIVE)),
    E6(new Position(E, SIX)),
    E7(new Position(E, SEVEN)),
    E8(new Position(E, EIGHT)),
    E9(new Position(E, NINE)),

    F0(new Position(F, ZERO)),
    F1(new Position(F, ONE)),
    F2(new Position(F, TWO)),
    F3(new Position(F, THREE)),
    F4(new Position(F, FOUR)),
    F5(new Position(F, FIVE)),
    F6(new Position(F, SIX)),
    F7(new Position(F, SEVEN)),
    F8(new Position(F, EIGHT)),
    F9(new Position(F, NINE)),

    G0(new Position(G, ZERO)),
    G1(new Position(G, ONE)),
    G2(new Position(G, TWO)),
    G3(new Position(G, THREE)),
    G4(new Position(G, FOUR)),
    G5(new Position(G, FIVE)),
    G6(new Position(G, SIX)),
    G7(new Position(G, SEVEN)),
    G8(new Position(G, EIGHT)),
    G9(new Position(G, NINE)),

    H0(new Position(H, ZERO)),
    H1(new Position(H, ONE)),
    H2(new Position(H, TWO)),
    H3(new Position(H, THREE)),
    H4(new Position(H, FOUR)),
    H5(new Position(H, FIVE)),
    H6(new Position(H, SIX)),
    H7(new Position(H, SEVEN)),
    H8(new Position(H, EIGHT)),
    H9(new Position(H, NINE)),

    I0(new Position(I, ZERO)),
    I1(new Position(I, ONE)),
    I2(new Position(I, TWO)),
    I3(new Position(I, THREE)),
    I4(new Position(I, FOUR)),
    I5(new Position(I, FIVE)),
    I6(new Position(I, SIX)),
    I7(new Position(I, SEVEN)),
    I8(new Position(I, EIGHT)),
    I9(new Position(I, NINE)),
    ;

    public final Position position;

    InitPositions(Position position) {
        this.position = position;
    }
}
