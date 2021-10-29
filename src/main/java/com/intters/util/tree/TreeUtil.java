package com.intters.util.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 树工具类
 *
 * @author Ruison
 * @date 2018/7/24.
 */
public class TreeUtil {
    /**
     * Map建树
     *
     * @param treeNodes 传入的树节点列表
     * @param root      父节点索引
     * @param <T>       返回类型
     * @return List T
     */
    public static <T extends TreeNode> List<T> buildByMap(List<T> treeNodes, Object root) {
        // 将所有的树节点放入Map
        Map<Integer, T> treeNodeMap = treeNodes.stream().collect(Collectors.toMap(TreeNode::getId, Function.identity(), (k1, k2) -> k2));
        List<T> trees = new ArrayList<>();
        for (Map.Entry<Integer, T> nodeEntry : treeNodeMap.entrySet()) {
            T treeNode = treeNodeMap.get(nodeEntry.getKey());
            // 判断是否是上级
            if (root.equals(treeNode.getParentId())) {
                trees.add(treeNode);
            } else {
                // 获取上级
                T parentNode = treeNodeMap.get(treeNode.getParentId());
                if (parentNode.getChildren() == null) {
                    parentNode.setChildren(new ArrayList<>());
                }
                parentNode.add(treeNode);
            }
        }
        return trees;
    }

    /**
     * 两层循环实现建树
     *
     * @param treeNodes 传入的树节点列表
     * @param root      父节点索引
     * @param <T>       返回类型
     * @return List T
     */
    public static <T extends TreeNode> List<T> build(List<T> treeNodes, Object root) {
        List<T> trees = new ArrayList<>();

        for (T treeNode : treeNodes) {
            // 判断是否是上级
            if (root.equals(treeNode.getParentId())) {
                trees.add(treeNode);
            }
            for (T it : treeNodes) {
                // 获取上级
                if (it.getParentId() == treeNode.getId()) {
                    if (treeNode.getChildren() == null) {
                        treeNode.setChildren(new ArrayList<>());
                    }
                    treeNode.add(it);
                }
            }
        }
        return trees;
    }

    /**
     * 使用递归方法建树
     *
     * @param treeNodes 传入的树节点列表
     * @param root      父节点索引
     * @param <T>       泛型
     * @return List T
     */
    public static <T extends TreeNode> List<T> buildByRecursive(List<T> treeNodes, Object root) {
        List<T> trees = new ArrayList<T>();
        for (T treeNode : treeNodes) {
            if (root.equals(treeNode.getParentId())) {
                trees.add(findChildren(treeNode, treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     *
     * @param treeNode  树节点
     * @param treeNodes 传入的树节点列表
     * @param <T>       泛型
     * @return 泛型
     */
    public static <T extends TreeNode> T findChildren(T treeNode, List<T> treeNodes) {
        for (T it : treeNodes) {
            if (treeNode.getId() == it.getParentId()) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }
}
