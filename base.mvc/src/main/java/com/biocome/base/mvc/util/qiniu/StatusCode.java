package com.biocome.base.mvc.util.qiniu;

public class StatusCode {
	/**
	 * 指定资源不存在或已被删除
	 */
	public static int FILE_NOT_FOUND = 612;
	/**
	 * 操作执行成功
	 */
	public static int OK = 200;

	/**
	 * 请求报文格式错误
	 */
	public static int FORMAT_ERROR = 400;
	/**
	 * 认证授权失败
	 */
	public static int AUTH_ERROR = 401;
	/**
	 * 用户账号被冻结
	 */
	public static int ACCOUNT_LOCK = 419;
	/**
	 * 指定空间不存在
	 */
	public static int SPACE_NOT_FOUND = 631;
	/**
	 * 目标资源已存在
	 */
	public static int FILE_EXIST = 614;
	/**
	 * 服务端操作超时
	 */
	public static int TIME_OUT = 504;
	/**
	 * 服务端不可用
	 */
	public static int SERVER_NOT_AVAILABLE = 503;
}
