package com.biocome.base.security.aspect;

import java.lang.reflect.Field;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import com.biocome.base.constants.Chars;
import com.biocome.base.exception.BusinessException;
import com.biocome.base.model.AutoIncrementEntity;
import com.biocome.base.model.UuidEntity;
import com.biocome.base.security.annotations.Log;
import com.biocome.base.security.annotations.LogField;
import com.biocome.base.security.entity.BnLogEntity;
import com.biocome.base.security.service.AbstractBnLogger;
import com.biocome.base.util.BeanUtils;
import com.biocome.base.util.SpringUtils;
import com.biocome.base.util.StringUtils;

/**
 * 日志切面。
 */
@Aspect
public class LogAspect {

	/**
	 * 环绕填充业务日志。
	 * 
	 * @param joinPoint
	 *            切入点
	 * @throws Throwable
	 *             切入方法执行失败时抛出异常。
	 */
	@Around("@annotation(com.biocome.base.security.annotations.Log)")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		return processEntity(joinPoint);
	}

	/**
	 * 处理业务实体类型的日志。
	 * 
	 * @param joinPoint
	 *            切入点
	 * @throws Throwable
	 *             切入方法执行失败时抛出异常。
	 */
	private Object processEntity(ProceedingJoinPoint joinPoint)
			throws Throwable {
		Log log = getLogs(joinPoint);
		AbstractBnLogger<? extends BnLogEntity> bnLogger = SpringUtils
				.getBean("bnLogger");
		BnLogEntity bnLog = bnLogger.newBnLog();
		bnLog.setLogType(log.type());
		bnLog.setMethod(joinPoint.getSignature().getName());
		Object[] os = joinPoint.getArgs();
		bnLog.setMessage(log.code() + Chars.COLON);
		bnLog.setMessage(Chars.OPEN_BRACE);
		if (log.target() > 0) {
			Object o = joinPoint.getArgs()[log.target()];
			bnLog.setMessage(getMessage(o));
		} else {
			for (Object o : os) {
				bnLog.setMessage(getMessage(o));
			}
		}
		bnLog.setMessage(Chars.CLOSE_BRACE);
		bnLogger.fill(bnLog);
		try {
			Object result = joinPoint.proceed();
			bnLog.setSuccess(true);
			bnLogger.fill(bnLog);
			return result;
		} catch (BusinessException e) {
			bnLog.setExceptionMsg(e.getMessage());
			throw e;
		} catch (Exception e) {
			bnLog.setExceptionMsg(e.getClass().getName());
			throw e;
		} finally {
			bnLogger.log(bnLog);
		}
	}

	/**
	 * 获取切入方法上的注解。
	 * 
	 * @param joinPoint
	 *            切入点
	 * @return 返回切入方法上的注解。
	 */
	private Log getLogs(JoinPoint joinPoint) {
		MethodSignature methodSignature = (MethodSignature) joinPoint
				.getSignature();
		return methodSignature.getMethod().getAnnotation(Log.class);
	}

	/**
	 * 获取日志信息。
	 * 
	 * @param target
	 *            日志目标对象
	 * @param log
	 *            日志注解
	 * @return 返回日志信息。
	 */
	private String getMessage(Object target) {
		try {
			if (target != null) {
				StringBuilder sb = new StringBuilder();
				sb.append(Chars.OPEN_BRACKET);
				if (target instanceof UuidEntity
						|| target instanceof AutoIncrementEntity) {
					List<Field> fields = BeanUtils.findField(target.getClass(),
							LogField.class);
					if (fields != null && fields.size() > 0) {
						for (Field f : fields) {
							boolean isac = f.isAccessible();
							f.setAccessible(true);
							LogField fa = f.getAnnotation(LogField.class);
							String fname = fa.text();
							if (!StringUtils.hasText(fname)) {
								fname = f.getName();
							}
							sb.append(fname).append(Chars.COLON)
									.append(f.get(target))
									.append(Chars.SEMICOLON);
							f.setAccessible(isac);
						}
					} else {
						Field idfield = BeanUtils.findField(target.getClass(),
								"id");
						if (idfield != null) {
							Object id = BeanUtils.getField(target, idfield);
							sb.append("id:").append(id).append(Chars.SEMICOLON);
						}
					}
				} else {
					sb.append(target.toString());
				}
				sb.append(Chars.CLOSE_BRACKET);
				return sb.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
