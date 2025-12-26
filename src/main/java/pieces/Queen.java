package pieces;

import board.Board;
import board.Cell;

/**
 * Класс, представляющий ферзя.
 */
public class Queen extends Piece {

    public Queen(PieceColor color) {
        super(color, PieceType.QUEEN);
    }

    /**
     * Проверяет корректность хода ферзя.
     * Ферзь ходит как ладья или как слон и не перепрыгивает фигуры.
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

        int dx = Math.abs(from.getX() - to.getX());
        int dy = Math.abs(from.getY() - to.getY());

        boolean straight = from.getX() == to.getX() || from.getY() == to.getY();
        boolean diagonal = dx == dy;

        if (!(straight || diagonal)) return false;

        return board.isPathClear(from, to);
    }
}
