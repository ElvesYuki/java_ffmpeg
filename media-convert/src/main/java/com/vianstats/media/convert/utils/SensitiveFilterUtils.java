package com.vianstats.media.convert.utils;


import com.vianstats.media.convert.advice.VianException;
import com.vianstats.media.convert.dto.TrieNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author LuoHuan
 * @date 2020/7/8
 */
@Component
public class SensitiveFilterUtils {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilterUtils.class);

    /**
     * AC自动机根节点
     * 工具类初始化时需构造完成
     */
    public static TrieNode trieRoot;

    @Resource
    private TrieNode trieNodeBean;

    /**
     * 初始化工具类
     *
     * @return
     */
    @PostConstruct
    public void init() {

        SensitiveFilterUtils.trieRoot = trieNodeBean;

    }

    /**
     * 过滤主方法
     * @param content 传入的文本类容
     * @return
     */
    public static String sensitiveFilter(String content) {

        long startTime = System.currentTimeMillis();

        String decodeUTF8 = SensitiveFilterUtils.decodeUTF8(content);

        //过滤需要更换的信息
        HashMap<Integer, Integer> replaceInfo = find(decodeUTF8, SensitiveFilterUtils.trieRoot);
        char[] chars = decodeUTF8.toCharArray();

        if (replaceInfo.size() != 0){
            logger.info(String.valueOf(chars));
        }

        //敏感文字换成*
        replaceInfo.forEach((key, value) -> {
            for (int i = 0; i < value; i++) {
                chars[key - value + 1 + i] = 42;
            }
        });

        logger.info(String.valueOf(chars));
        //获取结束时间
        long endTime = System.currentTimeMillis();
        //输出程序运行时间
        System.out.println("程序运行时间3：" + (endTime - startTime) + "ms");
        return String.valueOf(chars);

    }

    /**
     * 字符转换为UTF8编码
     * @param content
     * @return
     */
    public static String decodeUTF8(String content){
        String contentUTF8 = null;
        try {
            contentUTF8 = URLDecoder.decode(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return contentUTF8;
    }

    /**
     * 读取敏感词字典文本，构造stringLinkedList（未使用）
     *
     * @return stringLinkedList
     */
    private static LinkedList<String> initDic() {

        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("sensitiveWord/sensi_words.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));

        LinkedList<String> stringLinkedList = new LinkedList<>();
        try {
            for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                stringLinkedList.add(line);
            }
        } catch (IOException e) {
            logger.error("敏感词字典文件读取出错",e);
            new VianException("敏感词字典文件读取出错");
        }

        return stringLinkedList;
    }






    /**
     * 查找是否包含目标字符串
     */
    private static HashMap<Integer, Integer> find(String text, TrieNode TrieRoot) {
        int len = text.length();
        TrieNode node = TrieRoot;
        HashMap<Integer, Integer> replaceInfo = new HashMap<>(8);

        for (int index = 0; index < len; index++) {
            char c = text.charAt(index);

            while (node != null && node.getSonNode(c) == null) {
                node = node.getFailNode();
            }

            node = (node == null ? TrieRoot : node.getSonNode(c));

            TrieNode temp = node;

            while (temp != null) {
                if (temp.isWordEnd() && temp.getValue() != 32) {
                    replaceInfo.put(index, temp.getLength());
                }
                temp = temp.getFailNode();
            }
        }
        return replaceInfo;
    }



    /**
     * 添加节点
     */
    public static void addWordToTree(TrieNode rootNode, String word) {
        if (word == null || word.length() == 0) {
            return;
        }
        for (char c : word.toCharArray()) {
            if (rootNode.containsSonNode(c)) {
                rootNode = rootNode.getSonNode(c);
            } else {
                TrieNode newNode = new TrieNode(c);
                rootNode.addSonNode(newNode);
                rootNode = newNode;
            }
        }
        rootNode.setWordEnd(true);
    }

    public static void printTree(TrieNode root) {
        Queue<TrieNode> queue = new LinkedList<>();
        queue.offer(root);
        TrieNode enterNode = new TrieNode('\n');
        queue.add(enterNode);
        while (!queue.isEmpty()) {
            TrieNode parent = queue.poll();
            System.out.print(parent.getValue() + ";");
            if (parent == enterNode && queue.size() > 1) {
                queue.offer(enterNode);
                continue;
            }
            queue.addAll(parent.getSonsNode());
        }

    }



}
