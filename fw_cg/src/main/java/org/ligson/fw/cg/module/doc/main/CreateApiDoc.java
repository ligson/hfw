package org.ligson.fw.cg.module.doc.main;

import org.ligson.fw.cg.engine.TemplateEngine;
import org.ligson.fw.cg.module.doc.model.ApiCollection;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ligson on 2016/5/24.
 */
public class CreateApiDoc {


    public static void exec(String apiName, String destFile) throws Exception {
        Class clz = Class.forName(apiName);
        ApiCollection collection = new ApiCollection(clz);
        Map<String, Object> map = new HashMap<>();
        map.put("collection", collection);
        TemplateEngine.write("API.ftl", map, new File(destFile));
    }
}
