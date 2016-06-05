package org.ligson.fw.cg.module.dir.main;

import org.apache.commons.cli.Option;
import org.ligson.fw.cg.main.AbstractCmd;

import java.io.File;

/**
 * Created by ligso on 2016/6/5.
 */
public class CreateApp extends AbstractCmd {

    @Override
    public String getName() {
        return "createApp";
    }

    @Override
    public int exec(String[] args) {
        //第一次个参数是项目名
        String name = args[0];
        File root = new File(name);
        root.mkdirs();
        File serviceRoot = new File(root,name+"_service");
        File apiRoot = new File(root,name+"_api");
        File webRoot = new File(root,name+"_web");
        apiRoot.mkdirs();
        serviceRoot.mkdirs();
        webRoot.mkdirs();

        return 0;
    }

    @Override
    public String getDesciption() {
        return "create a app structure";
    }

    @Override
    public Option getOption() {
        Option option = Option.builder("createApp").hasArg().desc(getDesciption()).build();
        return option;
    }
}
