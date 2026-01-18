package academy.maze.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO для работы с решением лабиринта
 *
 * <p>Point parent - точка-родитель текущей точки int realPath - реальный путь от начала до текущей точки int
 * heuristicPath - эвристика для подсчета быстрого пути от начала до текущей точки boolean inOpenSet - переменная для
 * обозначения, использовали её в решении или нет
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NodeData {

    private Point parent;
    private int realPath;
    private int heuristicPath;
    private boolean inOpenSet;

    public void initMax() {
        realPath = Integer.MAX_VALUE;
        heuristicPath = Integer.MAX_VALUE;
        inOpenSet = false;
    }
}
