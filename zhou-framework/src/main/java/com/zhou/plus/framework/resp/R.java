package com.zhou.plus.framework.resp;

import java.io.Serializable;


public class R<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer code;

	private String message;

	private T data;

	private R(){}

	private static R getInstance() {
		return new R();
	}

	/**
	 * 200 成功状态码返回,不带消息
	 * @return
	 */
	public static R ok() {
		return ok("success",null);
	}

	/**
	 * 200 成功状态码返回
	 * @param obj
	 * @param <T>
	 * @return
	 */
	public static <T> R<T> ok(T obj) {
		return ok("success",obj);
	}

	/**
	 * 400 业务异常状态码返回
	 * @param message
	 * @param <T>
	 * @return
	 */
	public static <T> R<T> fail(String message){
		return fail(message, null);
	}

	/**
	 * 200 成功状态码返回
	 * @param message
	 * @param obj
	 * @param <T>
	 * @return
	 */
	public static <T> R<T> ok(String message, T obj) {
		R resp = getInstance();
		resp.setCode(RespEnum.OK.getCode());
		resp.setMessage(message);
		resp.setData(obj);
		return resp;
	}

	/**
	 * 400 业务异常状态码返回
	 * @param message
	 * @param obj
	 * @param <T>
	 * @return
	 */
	public static <T> R<T> fail(String message, T obj){
		return fail(RespEnum.FAIL.getCode(),message, obj);
	}

	/**
	 * 异常状态码返回
	 * @param message
	 * @param obj
	 * @param <T>
	 * @return
	 */
	public static <T> R<T> fail(Integer code,String message, T obj){
		R resp = getInstance();
		resp.setCode(code);
		resp.setMessage(message);
		resp.setData(obj);
		return resp;
	}


	public static <T> R<T> tokenError(String message){
		return tokenError(message, null);
	}

	public static <T> R<T> tokenError(String message,T obj) {
		R resp = getInstance();
		resp.setCode(RespEnum.NOT_LOGIN.getCode());
		resp.setMessage(message);
		resp.setData(obj);
		return resp;
	}

	public static R error(String message) {
		return R.error(message,null);
	}

	public static <T> R<T> error(String message,T obj) {
		R resp = getInstance();
		resp.setCode(RespEnum.ERROR.getCode());
		resp.setMessage(message);
		resp.setData(obj);
		return resp;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
