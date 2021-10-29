package com.intters.util.tree;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 建树
 *
 * @author Ruison
 * @date 2018/7/24.
 */
@Data
public class TreeNode {
    /**
     * 节点ID
     */
    protected int id;
    /**
     * 父节点ID
     */
    protected int parentId;
    /**
     * 子类
     */
    List<TreeNode> children = new ArrayList<>();

    /**
     * 添加子节点
     *
     * @param node 子节点
     */
    public void add(TreeNode node) {
        children.add(node);
    }
}
