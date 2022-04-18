package iob.logic.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class DateConverter implements Converter<String, Date> {
	@Override
	public Date convert(String source) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ").parse(source);
		} catch (Exception e) {
			return null;
		}
	}
}