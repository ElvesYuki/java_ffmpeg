package com.vianstats.media.convert.config;

import com.vianstats.media.convert.advice.VianException;
import com.vianstats.media.convert.dto.TrieNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author LuoHuan
 * @date 2020/8/19
 */
@Configuration
public class SensitiveFilterConfig {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilterConfig.class);

    /**
     * TreeNodeBean
     * @return
     */
    @Bean(name = "treeNodeBean")
    public TrieNode initTreeNodeBean() {

        TrieNode trieNode = this.initACFilter();

        return trieNode;

    }

    /**
     * 初始化AC自动机
     *
     * @return
     */
    public TrieNode initACFilter() {

        //敏感词文件的路径
        String sensitiveWordPath = "sensitiveWord/sensi_words.txt";
        LinkedList<String> stringLinkedList = null;

        try {
            InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(sensitiveWordPath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));
            stringLinkedList = new LinkedList<>();
            for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                stringLinkedList.add(line);
            }
        } catch (IOException e) {
            logger.error("敏感词字典文件读取出错",e);
            new VianException("敏感词字典文件读取出错");
        }

        TrieNode trieInit = this.buildTree(stringLinkedList);
        //printTree(root);
        TrieNode trieRoot = this.addFailNode(trieInit);
        return trieRoot;
    }

    /**
     * 初始化字典树
     */
    private TrieNode buildTree(LinkedList<String> stringLinkedList) {
        final TrieNode root = new TrieNode(' ');
        for (String word : stringLinkedList) {
            TrieNode temp = root;
            int length = 0;
            for (char ch : word.toCharArray()) {
                if (temp.containsSonNode(ch)) {
                    temp = temp.getSonNode(ch);
                } else {
                    TrieNode newNode = new TrieNode(ch);
                    temp.addSonNode(newNode);
                    temp = newNode;
                }
                length++;
            }
            temp.setWordEnd(true);
            //记录语句长度
            temp.setLength(length);
        }
        return root;
    }

    /**
     * BFS遍历树，给每一个节点建立FailNode
     */
    private TrieNode addFailNode(TrieNode root) {
        root.setFailNode(null);
        Queue<TrieNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TrieNode parent = queue.poll();
            TrieNode temp;
            for (TrieNode child : parent.getSonsNode()) {
                if (parent == root) {
                    child.setFailNode(root);
                } else {
                    temp = parent.getFailNode();
                    while (temp != null) {

                        TrieNode node = temp.getSonNode(child.getValue());
                        if (node != null) {
                            child.setFailNode(node);
                            break;
                        }
                        temp = temp.getFailNode();
                    }
                    if (temp == null) {
                        child.setFailNode(root);
                    }
                }
                queue.add(child);
            }
        }
        return root;
    }
}
