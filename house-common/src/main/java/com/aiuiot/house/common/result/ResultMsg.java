package com.aiuiot.house.common.result;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.eclipse.jetty.util.UrlEncoded;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;




/**
 * 该类用于返回结果信息
 * 作用是用来返回页面信息（成功 错误）
 * @author aiuiot
 *
 */
public class ResultMsg {
	/**
	 * 这两个Msg都分别对于一个Key
	 * errorMsg 对应 errorMsgKey
	 * successMsg 对应 successMsgKey
	 * 作用：可以在 ModelMap 中设置这个 key到相关 value的映射关系
	 * 以便于模版引擎文件获取到
	 * 
	 */
	public static final String errorMsgKey = "errorMsg";
	
	public static final String successMsgKey = "successMsg";
	
	private String errorMsg;	//错误信息
	
	private String successMsg;	//成功信息
	
	//判断是否成功
	public boolean isSuccess() {
		return errorMsg == null;	//当errorMsg为null就是成功
	};	

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getSuccessMsg() {
		return successMsg;
	}

	public void setSuccessMsg(String successMsg) {
		this.successMsg = successMsg;
	}

	public static String getErrormsgkey() {
		return errorMsgKey;
	}

	public static String getSuccessmsgkey() {
		return successMsgKey;
	}
	
	/**
	 * 为方便使用，创建两个静态方法
	 * @param msg
	 * @return
	 */
	
	/**
	 * 错误消息
	 * @param msg
	 * @return
	 */
	public static ResultMsg errorMsg(String msg) {
		ResultMsg resultMsg = new ResultMsg();
		resultMsg.setErrorMsg(msg);
		return resultMsg;
	}
	
	/**
	 * 成功消息
	 * @param msg
	 * @return
	 */
	public static ResultMsg successMsg(String msg) {
		ResultMsg resultMsg = new ResultMsg();
		resultMsg.setSuccessMsg(msg);
		return resultMsg;
	}
	
	//创建一个 asMap
	public Map<String, String> asMap() {
		Map<String, String> map = Maps.newHashMap();
		map.put(successMsgKey, successMsg);
		map.put(errorMsgKey, errorMsg);
		return map;
	}
	
	// 该方法序列化为URL	
	public String asUrlParams() {
		Map<String, String> map = asMap();
		Map<String, String> newMap = Maps.newHashMap();
		//遍历Map
		//map.forEach((k,v)->{if(v!=null) newMap.put(k, UrlEncoded.encodeString(v,"utf-8"))});
		//map.forEach((k,v)->{if(v!=null) newMap.put(k, UrlEncoded.encodeString(v, "utf-8"))});
		
		map.forEach((k,v) -> {if(v!=null)
			try {
				newMap.put(k, URLEncoder.encode(v,"utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}});
		return Joiner.on("&").useForNull("").withKeyValueSeparator("=").join(newMap);
		
	}
}
