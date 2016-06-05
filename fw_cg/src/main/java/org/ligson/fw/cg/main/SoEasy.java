package org.ligson.fw.cg.main;

import org.apache.commons.cli.*;
import org.ligson.fw.cg.module.doc.main.CreateApiDoc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ligson on 2016/5/24.
 */
public class SoEasy {
    private static CreateApiDoc cad = new CreateApiDoc();
    private static Options options = new Options();
    private static List<AbstractCmd> cmds = new ArrayList<>();

    static {
        options.addOption(cad.getOption());
        cmds.add(cad);
    }

    public static void main(String[] args) throws Exception {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmdLine = parser.parse(options, args);
        for (AbstractCmd cmd : cmds) {
            if (cmdLine.hasOption(cmd.getOption().getOpt())) {
                cmd.exec(cmd.getOption().getValues());
                break;
            }
        }
    }
}
