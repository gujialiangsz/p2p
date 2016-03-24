package com.biocome.base.mvc.config;

import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.stereotype.Component;

import com.biocome.base.mvc.converter.IEnumToString;
import com.biocome.base.mvc.converter.ParamsToString;
import com.biocome.base.mvc.converter.StringToIEnum;
import com.biocome.base.mvc.converter.StringToParams;
import com.biocome.base.mvc.converter.StringToUuidEntity;
import com.biocome.base.mvc.converter.UuidEntityToString;
import com.biocome.base.util.DateUtils;

/**
 * 转换器配置组件。
 */
@Component("com.biocome.base.mvc.config.ConversionConfigurer")
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
