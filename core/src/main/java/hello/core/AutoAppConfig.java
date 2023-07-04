package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan (
		// @ComponentScan을 사용하면 @Configuration이 붙은 설정 정보도 자동으로 등록되기 떄문에,
		// 앞서 만들어두었던 AppConfig, TestConfig 등 함께 등록되고 실행되어 버린다 -> 기존 작성한 예제 코드를 유지하기위해 필터를 적용
		excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

	
}
