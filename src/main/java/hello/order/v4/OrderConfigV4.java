package hello.order.v4;

import hello.order.OrderService;
import hello.order.v2.OrderServiceV2;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfigV4 {

    @Bean
    public OrderService orderService() {
        return new OrderServiceV2();
    }

    /**
     * TimedAspect 를 등록하면 @Timed 를 인지해서 Timer 를 사용하는 AOP를 적용한다.
     * 주의! TimedAspect 빈으로 등록하지 않으면 @Timed 관련 AOP가 동작하지 않는다.
     */
    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }
}
