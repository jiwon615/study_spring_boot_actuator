package hello.order.v3;

import hello.order.OrderService;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * V3: 주문수, 취소수에 Timer 메트릭 직접 적용
 * - 마이크로미터 기능 제공하는 핵심 컴포넌트 (MeterRegistry) 사용
 */
@Slf4j
public class OrderServiceV3 implements OrderService {

    private final MeterRegistry registry;
    private AtomicInteger stock = new AtomicInteger(100);

    public OrderServiceV3(MeterRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void order() {
        Timer timer = Timer.builder("my.order")
                .tag("class", this.getClass().getName())
                .tag("method", "order")
                .description("order")
                .register(registry);

        // timer.record() 안에 시간을 측정할 내용을 포함
        timer.record(() -> {
            log.info("주문");
            stock.decrementAndGet();
            sleep(500); // 0.5초
        });
    }

    @Override
    public void cancel() {
        Timer timer = Timer.builder("my.order")
                .tag("class", this.getClass().getName())
                .tag("method", "cancel")
                .description("cancel")
                .register(registry);

        timer.record(() -> {
            log.info("취소");
            stock.incrementAndGet();
            sleep(200); // 0.2초
        });
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
