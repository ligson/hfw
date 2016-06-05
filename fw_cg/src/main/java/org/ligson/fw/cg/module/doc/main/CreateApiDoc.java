package org.ligson.fw.cg.module.doc.main;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.ligson.fw.cg.engine.TemplateEngine;
import org.ligson.fw.cg.main.AbstractCmd;
import org.ligson.fw.cg.module.doc.model.ApiCollection;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ligson on 2016/5/24.
 */
public class CreateApiDoc extends AbstractCmd {

    private void exec(String apiName, String destFile) throws Exception {
        Class clz = Class.forName(apiName);
        ApiCollection collection = new ApiCollection(clz);
        Map<String, Object> map = new HashMap<>();
        map.put("collection", collection);
        TemplateEngine.write("API.ftl", map, new File(destFile));
    }

    @Override
    public String getName() {
        return "cad";
    }

    @Override
    public int exec(String[] args) {
        try {
            exec(args[0], args[1]);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public String getDesciption() {
        return "create api documnet";
    }

    @Override
    public Option getOption() {
        Option createApiDocOpt = Option.builder("cad").hasArg().desc("create api documnet").longOpt("createApiDoc").build();
        return createApiDocOpt;
    }
}
