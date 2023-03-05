package hello.order.v1;

import hello.order.OrderService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * V1: 주문수, 취소수에 Counter 메트릭 적용
 * - 마이크로미터 기능 제공하는 핵심 컴포넌트 (MeterRegistry) 사용
 */
@Slf4j
public class OrderServiceV1 implements OrderService {

    private final MeterRegistry registry; // 마이크로미터 기능을 제공하는 핵심 컴포넌트
    private AtomicInteger stock = new AtomicInteger(100); // 멀티쓰레드 상황에서 안전하게 값을 꺼내 사용 가능

    public OrderServiceV1(MeterRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void order() {
        log.info("주문");
        stock.decrementAndGet();

        Counter.builder("my.order") // 메트릭 이름
                .tag("class", this.getClass().getName())
                .tag("method", "order") // 메소드 이름으로 구분하기 위해
                .description("order")
                .register(registry).increment();
    }

    @Override
    public void cancel() {
        log.info("취소");
        stock.incrementAndGet();

        Counter.builder("my.order")
                .tag("class", this.getClass().getName())
                .tag("method", "cancel")
                .description("cancel")
                .register(registry).increment();
    }

    @Override
    public AtomicInteger getStock() {
        return stock;
    }
}
