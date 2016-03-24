package com.biocome.base.security.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import com.biocome.base.security.entity.ResourceEntity;

/**
 * ResourceEntity切面。
 */
@Aspect
public class ResourceEntityAspect {
	/**
	 * 在方法执行之前对ResourceEntity进行自动填充。
	 * 
	 * @param joinPoint
	 *            切入点
	 */
	@Before("@annotation(com.biocome.base.security.annotations.AutoFillResourceEntity)")
	public void before(JoinPoint joinPoint) {
		Object[] params = joinPoint.getArgs();
		for (Object param : params) {
			if (param instanceof ResourceEntity<?>) {
				((ResourceEntity<?>) param).autoFillIn();
			}
		}
	}
}
