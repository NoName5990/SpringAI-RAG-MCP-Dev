package com.jixiaoliu.common;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataSet;

/**
 * @ClassName FormConvert
 * @Author liujxiao
 * @Version 1.0
 * @Description
 * @date 2026/4/26 下午9:24
 */
public class FormConvert {

    /**
     * @param markdownStr
     * @return java.lang.String
     * @Description: markdown转html
     * @Date 2026/4/26 下午9:24
     * @Author liujxiao
     */
    public static String convertToHtml(String markdownStr) {

        MutableDataSet dataSet = new MutableDataSet();
        Parser parser = Parser.builder(dataSet).build();
        HtmlRenderer htmlRenderer = HtmlRenderer.builder(dataSet).build();

        return htmlRenderer.render(parser.parse(markdownStr));
    }
}
