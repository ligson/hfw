package org.ligson.fw.string.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 电话号码校验
 *
 */
public class NameValidator {
	
	/**
	 * 验证姓名有效性，需要考虑支持少数民族的人名，或者外国人的中译名，例如：阿沛·阿旺晋美、卡尔·马克思等
	 * @param str
	 * @return
	 */
	public static boolean isName(String str) {   
        Pattern p = null;
        Matcher m = null;  
        boolean b = false;    
        p = Pattern.compile("[\u4E00-\u9FA5]{2,5}(?:·[\u4E00-\u9FA5]{2,5})*"); 
        m = p.matcher(str);  
        b = m.matches();    
        return b;  
    }  
    
    public static void main(String[] args){
    	System.out.println(NameValidator.isName("西门吹雪"));
    	System.out.println(NameValidator.isName("刘小小刘小小"));
    	
    }
}

