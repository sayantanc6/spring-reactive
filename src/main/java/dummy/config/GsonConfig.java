package dummy.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.autoconfigure.gson.GsonBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import com.google.gson.GsonBuilder;

@EnableWebFlux
@Configuration
public class GsonConfig implements WebFluxConfigurer{
	
	@Bean
	public GsonBuilder gsonBuilder(List<GsonBuilderCustomizer> customizers) {
		
		GsonBuilder builder = new GsonBuilder();
		 customizers.forEach(c -> c.customize(builder));
				return builder;
	}

       // it'll help to correct JSON format response in REST APIs
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) { 
	    StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
	    stringConverter.setWriteAcceptCharset(false);
	    stringConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.TEXT_PLAIN));
	    converters.add(stringConverter);
	    converters.add(new ByteArrayHttpMessageConverter());
	    converters.add(new SourceHttpMessageConverter<>());
	    GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
	    gsonHttpMessageConverter.setGson(new GsonBuilder().create());
	    gsonHttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON));
	    converters.add(gsonHttpMessageConverter);
	}
}
