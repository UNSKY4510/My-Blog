package com.unsky.myblog.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * @author UNSKY
 * @date 2022/6/7 16:36
 */
@Component
public class BackUpDataBaseManager {
    private static final Logger log = LoggerFactory.getLogger(BackUpDataBaseManager.class);
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String userName;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.backupPath}")
    private String sqlPath;

    /**
     * 获取数据库名
     */
    public String getDataBaseName() {
        return url.substring(url.indexOf("3306"), url.indexOf("?")).replaceAll("/", "").replaceAll("3306", "");
    }

    /**
     * 获取主机地址
     */
    private String getHost() {
        return url.substring(url.indexOf("mysql"), url.indexOf("3306")).replace(":", "").replace("//", "").replace("mysql", "");
    }

    /**
     * 导出 sql 并返回相关信息
     */
    public void exportSql(String time) {
        // 指定导出的 sql 存放的文件夹
        String year = time.substring(0,4);
        String month = time.substring(4,6);
        String pathName = sqlPath + year +"/" + month + "/";
        File saveFile = new File(pathName);
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }
        String host = getHost();
        String dataBaseName = getDataBaseName();
        String fileName = time + "_" + "backup.sql";
        StringBuilder sb = new StringBuilder();
        // 拼接备份命令
        sb.append("mysqldump").append(" --opt").append(" -h ").append(host).append(" --user=").append(userName).append(" --password=").append(password);
        sb.append(" --result-file=").append(pathName + fileName).append(" --default-character-set=utf8 ").append(dataBaseName);
        try {
            log.info("执行语句："+sb.toString());
            Process exec = Runtime.getRuntime().exec(sb.toString());
            if (exec.waitFor() == 0) {
                log.info("数据库备份成功，保存路径：" + pathName);
            } else {
                System.out.println("process.waitFor()=" + exec.waitFor());
            }
        } catch (IOException e) {
            log.error("备份 数据库 出现 IO异常 ", e);
        } catch (InterruptedException e) {
            log.error("备份 数据库 出现 线程中断异常 ", e);
        } catch (Exception e) {
            log.error("备份 数据库 出现 其他异常 ", e);
        }
    }
}
