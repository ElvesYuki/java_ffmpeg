package com.vianstats.media.convert.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author LuoHuan
 * @date 2020/7/9
 */
public class TrieNode implements Serializable {

    private static final long serialVersionUID = -5854376885812710249L;

    private TrieNode failNode;

    private char value;

    private boolean isWordEnd = false;

    private Map<Character, TrieNode> sons;

    private int length;

    public TrieNode() {
        sons = new LinkedHashMap<Character, TrieNode>();
    }

    public TrieNode(char value) {

        this.value = value;
        sons = new LinkedHashMap<Character, TrieNode>();
    }

    // 添加子节点
    public void addSonNode(TrieNode node) {
        sons.put(node.value, node);
    }

    // 获取子节点中指定字符节点
    public TrieNode getSonNode(char ch) {
        return sons.get(ch);
    }

    // 判断子节点中是否存在该字符
    public boolean containsSonNode(char ch) {
        return sons.containsKey(ch);
    }

    // 获取字符
    public char getValue() {
        return value;
    }

    // 设置失败指针并且返回
    public void setFailNode(TrieNode failNode) {
        this.failNode = failNode;
    }

    public TrieNode getFailNode() {
        return failNode;
    }

    // 获取所有的孩子节点
    public Collection<TrieNode> getSonsNode() {
        return sons.values();
    }

    public boolean isWordEnd() {
        return isWordEnd;
    }

    public void setWordEnd(boolean isWordEnd) {
        this.isWordEnd = isWordEnd;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setValue(char value) {
        this.value = value;
    }
}
