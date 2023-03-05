package hello.order.v2;

import hello.order.OrderService;
import io.micrometer.core.annotation.Counted;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * V2: 주문수, 취소수에 Counter 메트릭 적용
 * - 스프링 AOP를 사용하여 @Counted 활용
 */
@Slf4j
public class OrderServiceV2 implements OrderService {

    private AtomicInteger stock = new AtomicInteger(100); // 멀티쓰레드 상황에서 안전하게 값을 꺼내 사용 가능

    @Counted("my.order") // 메트릭 이름 지정
    @Override
    public void order() {
        log.info("주문");
        stock.decrementAndGet();
    }

    @Counted("my.order")
    @Override
    public void cancel() {
        log.info("취소");
        stock.incrementAndGet();
    }

    @Override
    public AtomicInteger getStock() {
        return stock;
    }
}
