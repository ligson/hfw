package org.ligson.fw.web.convert;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ligson on 2016/5/12.
 */
public class DateEditor extends PropertyEditorSupport {
    public static List<String> dateFormats = new ArrayList<>();

    static {
        dateFormats.add("yyyyMMdd");
        dateFormats.add("yyyyMMddHHmmss");
        dateFormats.add("yyyy-MM-dd");
        dateFormats.add("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public void setAsText(String dateStr) throws IllegalArgumentException {
        Date d = this.parseToDate(dateStr);
        this.setValue(d);
    }

    private Date parseToDate(String date) {
        Date dt = null;
        for (String format : dateFormats) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            try {
                dt = sdf.parse(date);
                break;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (dt == null) {
            throw new RuntimeException("没有找到日期" + date + "的匹配格式");
        }
        return dt;
    }
}