package blossom.project.designmode.strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 策略接口
interface SortingStrategy {
    void sort(List<Integer> list);
}

// 具体策略实现之一
class BubbleSortStrategy implements SortingStrategy {
    @Override
    public void sort(List<Integer> list) {
        System.out.println("Sorting using bubble sort");
        // 实现冒泡排序
    }
}

// 具体策略实现之二
class QuickSortStrategy implements SortingStrategy {
    @Override
    public void sort(List<Integer> list) {
        System.out.println("Sorting using quick sort");
        // 实现快速排序
    }
}

// 上下文角色
class SortedList {
    private SortingStrategy strategy;

    public void setSortingStrategy(SortingStrategy strategy) {
        this.strategy = strategy;
    }

    public void sort(List<Integer> list) {
        strategy.sort(list);
    }
}

// 使用策略模式
public class StrategyPatternDemo {
    public static void main(String[] args) {
        SortedList list = new SortedList();

        list.setSortingStrategy(new BubbleSortStrategy());
        list.sort(new ArrayList<>(Arrays.asList(1, 3, 2)));

        list.setSortingStrategy(new QuickSortStrategy());
        list.sort(new ArrayList<>(Arrays.asList(1, 3, 2)));
    }
}