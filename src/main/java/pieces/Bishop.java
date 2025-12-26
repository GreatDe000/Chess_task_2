package pieces;

import board.Board;
import board.Cell;

/**
 * Класс, представляющий слона.
 */
public class Bishop extends Piece {

    public Bishop(PieceColor color) {
        super(color, PieceType.BISHOP);
    }

    /**
     * Проверяет корректность хода слона.
     * Слон ходит по диагонали и не перепрыгивает фигуры.
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
        if (dx != dy) return false;

        return board.isPathClear(from, to);
    }
}
