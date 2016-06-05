package org.ligson.fw.cg.main;


import org.apache.commons.cli.Option;

/**
 * Created by ligso on 2016/6/5.
 */
public abstract class AbstractCmd {
    public abstract String getName();

    public abstract int exec(String args[]);

    public abstract String getDesciption();

    public abstract Option getOption();

}
