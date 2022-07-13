package com.unsky.myblog.util;

import org.commonmark.Extension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import org.commonmark.ext.autolink.AutolinkExtension ;
import org.commonmark.ext.gfm.tables.TablesExtension ;
import org.commonmark.ext.ins.InsExtension ;
import org.commonmark.ext.image.attributes.ImageAttributesExtension ;
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension ;
import org.commonmark.ext.front.matter.YamlFrontMatterExtension ;

import java.util.Arrays;
import java.util.List;

/**
 * @author UNSKY
 * @date 2022/5/20 20:45
 */
public class MarkDownUtil {
    /**
     * 转换md格式为html
     *
     * @param markdownString
     * @return
     */
//    public static String mdToHtml(String markdownString) {
//        if (StringUtils.isEmpty(markdownString)) {
//            return "";
//        }
//        java.util.List<Extension> extensions = Arrays.asList(TablesExtension.create());
//        Parser parser = Parser.builder().extensions(extensions).build();
//        Node document = parser.parse(markdownString);
//        HtmlRenderer renderer = HtmlRenderer.builder().extensions(extensions).build();
//        String content = renderer.render(document);
//        return content;
//    }
//
//    public static String markdownToHtml(String markdown){
//        // 新建一个解析器
//        Parser parser = Parser.builder().build();
//        Node document = parser.parse(markdown) ;
//        // 新建一个html代码渲染器
//        HtmlRenderer renderer = HtmlRenderer.builder().build();
//        // 将解析出来的document进行渲染
//        return renderer.render(document) ;
//    }

    public static String mdToHtml(String markdown){
        List<Extension> extensions = Arrays.asList(AutolinkExtension.create(),
                TablesExtension.create(),
                InsExtension.create(),
                ImageAttributesExtension.create(),
                StrikethroughExtension.create(),
                YamlFrontMatterExtension.create()) ;
        //extensions.add(TablesExtension.create()) ;
        Parser parser = Parser.builder().extensions(extensions).build() ;

        HtmlRenderer renderer = HtmlRenderer.builder().extensions(extensions).build() ;

        Node document = parser.parse(markdown) ;

        return renderer.render(document) ;
    }
}
