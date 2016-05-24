package org.ligson.fw.cg.main;

import org.apache.commons.cli.*;
import org.ligson.fw.cg.module.doc.main.CreateApiDoc;

/**
 * Created by ligson on 2016/5/24.
 */
public class SoEasy {
    public static void main(String[] args) throws Exception {
        Option help = new Option("h", "help command");
        Option createApiDocOpt = Option.builder("cad").hasArg().desc("create api documnet").longOpt("createApiDoc").build();
        Options options = new Options();
        options.addOption(help);
        options.addOption(createApiDocOpt);
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        if (cmd.hasOption(createApiDocOpt.getOpt())) {
            String apiName = cmd.getOptionValue(createApiDocOpt.getOpt());
            CreateApiDoc.exec(apiName, apiName + ".md");
        }
    }
}
