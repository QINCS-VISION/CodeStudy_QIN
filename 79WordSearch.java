// 秦的力扣学习笔记：https://github.com/QINCS-VISION/CodeStudy_QIN
// 下面代码实现的是力扣（LeetCode）上的“最小高度树”问题，你可以通过以下链接查看具体题目描述：
// https://leetcode.cn/problems/word-search/description/

import java.util.Arrays;

class Solution {
    // 方法用于判断给定的字符矩阵中是否存在由字符组成的字符串word
    public boolean exist(char[][] board, String word) {
        // 获取字符矩阵的行数
        int h = board.length;
        // 获取字符矩阵的列数
        int w = board[0].length;

        // 创建一个与字符矩阵大小相同的布尔型二维数组，用于记录每个位置是否已被访问过
        boolean[][] visited = new boolean[h][w];

        // 遍历字符矩阵的每一个位置
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                // 调用check方法从当前位置开始检查是否能找到字符串word
                boolean flag = check(board, visited, i, j, word, 0);
                // 如果能找到，直接返回true
                if (flag) {
                    return true;
                }
            }
        }

        // 如果遍历完整个字符矩阵都没有找到字符串word，则返回false
        return false;
    }

    // 递归方法用于从指定位置开始检查是否能找到字符串word
    public boolean check(char[][] board, boolean[][] visited, int i, int j, String s, int k) {
        // 如果当前字符矩阵中的字符与待匹配字符串中当前位置的字符不相等，则返回false
        if (board[i][j]!= s.charAt(k)) {
            return false;
        } else if (k == s.length() - 1) {
            // 如果已经匹配到了待匹配字符串的最后一个字符，说明找到了完整的字符串，返回true
            return true;
        }

        // 将当前位置标记为已访问
        visited[i][j] = true;

        // 定义上下左右四个方向的偏移量数组，用于遍历当前位置的相邻位置
        int[][] directions = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };

        // 初始化结果为false，表示暂时还未找到完整的字符串
        boolean result = false;

        // 遍历四个方向
        for (int[] dir : directions) {
            // 计算相邻位置的行索引
            int newi = i + dir[0];
            // 计算相邻位置的列索引
            int newj = j + dir[1];

            // 判断相邻位置是否在字符矩阵范围内
            if (newi >= 0 && newi < board.length && newj >= 0 && newj < board[0].length) {
                // 如果相邻位置未被访问过
                if (!visited[newi][newj]) {
                    // 递归调用check方法，从相邻位置继续匹配字符串的下一个字符
                    boolean flag = check(board, visited, newi, newj, s, k + 1);
                    // 如果能从相邻位置找到完整的字符串，更新结果为true，并跳出循环
                    if (flag) {
                        result = true;
                        break;
                    }
                }
            }
        }

        // 将当前位置标记为未访问，以便后续其他位置的搜索可以再次访问该位置
        visited[i][j] = false;

        // 返回最终结果，即是否能从当前位置开始找到完整的字符串
        return result;
    }
}
