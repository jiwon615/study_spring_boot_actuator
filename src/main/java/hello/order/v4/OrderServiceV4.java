package hello.order.v4;

import hello.order.OrderService;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * V4: 주문수, 취소수에 Timer 메트릭 적용
 * - 스프링 AOP를 사용하여 @Timed 활용
 */
@Timed("my.order")
@Slf4j
public class OrderServiceV4 implements OrderService {

    private AtomicInteger stock = new AtomicInteger(100);

    @Override
    public void order() {
        log.info("주문");
        stock.decrementAndGet();
        sleep(500); // 0.5초
    }

    @Override
    public void cancel() {
        log.info("취소");
        stock.incrementAndGet();
        sleep(200); // 0.2초
    }

    @Override
    public AtomicInteger getStock() {
        return stock;
    }

    private void sleep(int i) {
        try {
            Thread.sleep(i + new Random().nextInt(200)); // + 0 ~ 0.2초
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
