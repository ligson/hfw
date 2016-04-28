package org.ligson.fw.string.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 电话号码校验
 *
 */
public class PhoneValidator {
	/** 
	     * 手机号验证 
	     *  
	     * @param  str 
	     * @return 验证通过返回true 
	     */  
	    public static boolean isMobile(String str) {   
	        Pattern p = null;  
	        Matcher m = null;  
	        boolean b = false;   
	        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号  
	        m = p.matcher(str);  
	        b = m.matches();   
	        return b;  
	    }  
	    /** 
	     * 电话号码验证 
	     *  
	     * @param  str 
	     * @return 验证通过返回true 
	     */  
	    public static boolean isPhone(String str) {   
	        Pattern p1 = null,p2 = null;  
	        Matcher m = null;  
	        boolean b = false;    
	        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的  
	        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的  
	        if(str.length() >9)  
	        {   m = p1.matcher(str);  
	            b = m.matches();    
	        }else{  
	            m = p2.matcher(str);  
	            b = m.matches();   
	        }    
	        return b;  
	    }  
	    
	    public static void main(String[] args){
	    	//System.out.println(PhoneValidator.isMobile("13581775101"));
	    	Pattern p = Pattern.compile("[\u4E00-\u9FA5]{2,5}(?:·[\u4E00-\u9FA5]{2,5})*"); 
	    	Matcher m = p.matcher("卡尔马克思卡尔马克思卡尔马克思");
	    	System.out.println(m.matches() );
	    	
	    	
	    }
}

