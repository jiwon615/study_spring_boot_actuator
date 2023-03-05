package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ActuatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActuatorApplication.class, args);
    }

    // httpexchanges 앤드포인트 사용위해 추가
    @Bean
    public InMemoryHttpExchangeRepository httpExchangeRepository() {
        // 이 구현체는 최대 100갸의 HTTP 요청을 제공함. 최대 요청이 넘어가면 과거 요청 삭제함 ( setCapacity()로 최대 요청수 변경 가능)
        return new InMemoryHttpExchangeRepository();
    }

}
