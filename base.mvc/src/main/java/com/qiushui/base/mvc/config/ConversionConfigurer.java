package com.qiushui.base.mvc.config;

import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.stereotype.Component;

import com.qiushui.base.mvc.converter.IEnumToString;
import com.qiushui.base.mvc.converter.ParamsToString;
import com.qiushui.base.mvc.converter.StringToIEnum;
import com.qiushui.base.mvc.converter.StringToParams;
import com.qiushui.base.mvc.converter.StringToUuidEntity;
import com.qiushui.base.mvc.converter.UuidEntityToString;
import com.qiushui.base.util.DateUtils;

/**
 * 转换器配置组件。
 */
@Component("com.qiushui.base.mvc.config.ConversionConfigurer")
public class ConversionConfigurer extends AbstractConversionConfigurer {
	@Override
	public void config(FormattingConversionService conversionService) {
		conversionService.removeConvertible(String.class, Enum.class);
		conversionService.addConverterFactory(new StringToIEnum());
		conversionService.addConverter(new IEnumToString());
		conversionService.addConverter(new StringToParams());
		conversionService.addConverter(new ParamsToString());
		conversionService.addConverterFactory(new StringToUuidEntity());
		conversionService.addConverter(new UuidEntityToString());

		conversionService
				.addFormatter(new DateFormatter(DateUtils.MILLISECOND));
		conversionService.addFormatter(new DateFormatter(DateUtils.SECOND));
		conversionService.addFormatter(new DateFormatter(DateUtils.MINUTE));
		conversionService.addFormatter(new DateFormatter(DateUtils.DAY));
	}
}
