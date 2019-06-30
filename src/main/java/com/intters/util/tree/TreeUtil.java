package com.intters.util.tree;

import com.intters.util.tree.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ruison
 * @date 2018/7/24.
 */
public class TreeUtil {

    /**
     * 两层循环实现建树
     * @param treeNodes 传入的树节点列表
     * @param root 父节点索引
     * @param <T> 返回类型
     * @return
     */
    public static <T extends TreeNode> List<T> bulid(List<T> treeNodes, Object root) {
        List<T> trees = new ArrayList<T>();

        for (T treeNode : treeNodes) {
            if (root.equals(treeNode.getParentId())) {
                trees.add(treeNode);
            }
            for (T it : treeNodes) {
                if (it.getParentId() == treeNode.getId()) {
                    if (treeNode.getChildren() == null) {
                        treeNode.setChildren(new ArrayList<TreeNode>());
                    }
                    treeNode.add(it);
                }
            }
        }
        return trees;
    }

    /**
     * 使用递归方法建树
     * @param treeNodes 传入的树节点列表
     * @param root 父节点索引
     * @param <T> 返回类型
     * @return
     */
    public static <T extends TreeNode> List<T> buildByRecursive(List<T> treeNodes,Object root) {
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
     * @param treeNode 树节点
     * @param treeNodes 传入的树节点列表
     * @param <T>
     * @return
     */
    public static <T extends TreeNode> T findChildren(T treeNode, List<T> treeNodes) {
        for (T it : treeNodes) {
            if (treeNode.getId() == it.getParentId()) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<TreeNode>());
                }
                treeNode.add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }
}
