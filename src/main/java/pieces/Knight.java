package pieces;

import board.Board;
import board.Cell;

/**
 * Класс, представляющий коня.
 */
public class Knight extends Piece {

    public Knight(PieceColor color) {
        super(color, PieceType.KNIGHT);
    }

    @Override
/**
 * Выполняет действие: isValidMove.
 * @param from параметр метода
 * @param to параметр метода
 * @param board параметр метода
 * @return результат выполнения
 */
    public boolean isValidMove(Cell from, Cell to, Board board) {
        int dx = Math.abs(from.getX() - to.getX());
        int dy = Math.abs(from.getY() - to.getY());

        return (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
    }
}
