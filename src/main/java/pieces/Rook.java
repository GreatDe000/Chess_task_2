package pieces;

import board.Board;
import board.Cell;

/**
 * Класс, представляющий ладью.
 */
public class Rook extends Piece {

    public Rook(PieceColor color) {
        super(color, PieceType.ROOK);
    }

    /**
     * Проверяет корректность хода ладьи.
     * Ладья ходит по вертикали или горизонтали, не перепрыгивая фигуры.
     */
    @Override
/**
 * Выполняет действие: isValidMove.
 * @param from параметр метода
 * @param to параметр метода
 * @param board параметр метода
 * @return результат выполнения
 */
    public boolean isValidMove(Cell from, Cell to, Board board) {
        if (from == to) return false;

        boolean straight = from.getX() == to.getX() || from.getY() == to.getY();
        if (!straight) return false;

        return board.isPathClear(from, to);
    }
}
