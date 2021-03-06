package com.qiushui.base.security.component;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.qiushui.base.captcha.CaptchaImageConfig;
import com.qiushui.base.captcha.CaptchaImageGenerator;

/**
 * 验证码组件。
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Captcha implements Serializable {
	private final Logger log = LoggerFactory.getLogger(getClass());
	@Resource
	private CaptchaImageGenerator captchaImageGenerator;
	@Resource
	private CaptchaImageConfig captchaImageConfig;
	/** 正确的验证码 */
	private String correctCode;

	/**
	 * 生成验证码图片。
	 * 
	 * @return 返回验证码图片。
	 */
	public BufferedImage generateImage() {
		correctCode = generateCode();
		log.debug("生成验证码[{}]。", correctCode);
		return captchaImageGenerator.generateImage(correctCode);
	}

	/**
	 * 验证验证码，忽略大小写。
	 * 
	 * @param code
	 *            验证码
	 * 
	 * @return 正确返回true，错误返回false。
	 */
	public Boolean validate(String code) {
		if (code != null) {
			return code.equalsIgnoreCase(correctCode);
		}
		return false;
	}

	/**
	 * 验证验证码，区分大小写。
	 * 
	 * @param code
	 *            验证码
	 * 
	 * @return 正确返回true，错误返回false。
	 */
	public Boolean validateWithCase(String code) {
		return code.equals(correctCode);
	}

	/**
	 * 生成随机验证码字符串。
	 * 
	 * @return 返回随机验证码字符串。
	 */
	private String generateCode() {
		char[] chars = captchaImageConfig.getChars().toCharArray();
		StringBuffer challengeString = new StringBuffer();
		for (int i = 0; i < captchaImageConfig.getLength(); i++) {
			double randomValue = Math.random();
			int randomIndex = (int) Math
					.round(randomValue * (chars.length - 1));
			char characterToShow = chars[randomIndex];
			challengeString.append(characterToShow);
		}
		return challengeString.toString();
	}
}
