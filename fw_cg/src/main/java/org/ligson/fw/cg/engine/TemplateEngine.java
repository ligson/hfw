package org.ligson.fw.cg.engine;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

import java.io.*;
import java.util.Map;

/**
 * Created by ligson on 2016/5/24.
 */
public class TemplateEngine {
    private static Configuration cfg = new Configuration(Configuration.getVersion());

    static {
        // 指定FreeMarker模板文件的位置
        String ftlPath = TemplateEngine.class.getClassLoader().getResource("./ftl").getFile();
        File ftlRootDir = new File(ftlPath);
        try {
            cfg.setDirectoryForTemplateLoading(ftlRootDir);
            cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.getVersion()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void write(String templateName, Map<String, Object> varMap, File destFile) {
        if (!destFile.exists()) {
            try {
                destFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            Template temp = cfg.getTemplate(templateName, "UTF-8");
            temp.process(varMap, new FileWriter(destFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}