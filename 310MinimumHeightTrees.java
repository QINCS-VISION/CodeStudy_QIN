// 秦的力扣学习笔记：https://github.com/QINCS-VISION/CodeStudy_QIN
// 下面代码实现的是力扣（LeetCode）上的“最小高度树”问题，你可以通过以下链接查看具体题目描述：
// https://leetcode.cn/problems/minimum-height-trees/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Solution {
    // 定义函数findMinHeightTrees，用于找到给定无向图的所有最小高度树的根节点
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        // 用于存储最终找到的最小高度树的根节点列表
        List<Integer> ans = new ArrayList<Integer>();

        // 如果图中只有一个节点，那么这个节点就是最小高度树的根节点，直接将其添加到结果列表中并返回
        if (n == 1) {
            ans.add(0);
            return ans;
        }

        // 创建一个大小为n的邻接表，用于存储图的连接关系
        // 每个元素adj[i]是一个列表，用于存储与节点i相邻的节点
        List<Integer>[] adj = new List[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }

        // 遍历边的数组，将每条边的两个节点添加到彼此的邻接表中
        // 这样就构建好了图的邻接表表示
        for (int[] edge : edges) {
            adj[edge[0]].add(edge[1]);
            adj[edge[1]].add(edge[0]);
        }

        // 创建一个大小为n的数组，用于存储每个节点的父节点
        // 初始时将所有元素设置为-1，表示没有父节点
        int[] parent = new int[n];
        Arrays.fill(parent, -1);

        // 找到与节点0最远的节点x
        // 这里调用findLongestNode函数，传入节点0、父节点数组和邻接表
        int x = findLongestNode(0, parent, adj);

        // 找到与节点x最远的节点y
        // 再次调用findLongestNode函数，传入节点x、更新后的父节点数组和邻接表
        int y = findLongestNode(x, parent, adj);

        // 创建一个列表，用于存储从节点x到节点y的路径上的节点
        List<Integer> path = new ArrayList<Integer>();

        // 将节点x的父节点设置为-1，作为路径的起始点
        parent[x] = -1;

        // 通过不断访问当前节点的父节点，从节点y回溯到节点x，将路径上的节点依次添加到路径列表中
        while (y!= -1) {
            path.add(y);
            y = parent[y];
        }

        // 获取路径的长度
        int m = path.size();

        // 如果路径长度为偶数
        if (m % 2 == 0) {
            // 将路径中间偏左的节点添加到结果列表中
            // m / 2 - 1是因为列表索引从0开始，要获取中间偏左的节点索引
            ans.add(path.get(m / 2 - 1));
        }

        // 将路径中间的节点添加到结果列表中
        ans.add(path.get(m / 2));

        // 返回存储最小高度树的根节点的列表
        return ans;
    }

    // 定义函数findLongestNode，用于找到给定节点u在图中的最远节点
    public int findLongestNode(int u, int[] parent, List<Integer>[] adj) {
        // 获取邻接表的长度，也就是图中节点的数量
        int n = adj.length;

        // 创建一个队列，用于进行广度优先搜索
        Queue<Integer> queue = new ArrayDeque<Integer>();

        // 创建一个布尔数组，用于标记节点是否已被访问过
        boolean[] visit = new boolean[n];

        // 将起始节点u添加到队列中，并标记为已访问
        queue.offer(u);
        visit[u] = true;

        // 用于存储找到的最远节点，初始时设置为-1
        int node = -1;

        // 当队列不为空时，进行广度优先搜索
        while (!queue.isEmpty()) {
            // 从队列中取出一个节点
            int curr = queue.poll();

            // 将当前取出的节点设置为找到的最远节点（暂时的，可能后续会更新）
            node = curr;

            // 遍历当前节点的所有相邻节点
            for (int v : adj[curr]) {
                // 如果相邻节点未被访问过
                if (!visit[v]) {
                    // 标记为已访问
                    visit[v] = true;

                    // 将当前节点设置为相邻节点的父节点
                    parent[v] = curr;

                    // 将相邻节点添加到队列中，以便后续继续搜索
                    queue.offer(v);
                }
            }
        }

        // 返回找到的最远节点
        return node;
    }
}
