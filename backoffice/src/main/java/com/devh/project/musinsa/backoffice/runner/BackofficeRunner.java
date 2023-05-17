package com.devh.project.musinsa.backoffice.runner;

import com.devh.project.musinsa.backoffice.feign.AggregatorFeignClient;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class BackofficeRunner implements ApplicationRunner {

    private final AggregatorFeignClient aggregatorFeignClient;

    public BackofficeRunner(AggregatorFeignClient aggregatorFeignClient) {
        this.aggregatorFeignClient = aggregatorFeignClient;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        new Thread(() -> {
            boolean aggregatorAlive = false;
            do {
                try {
                    System.out.println("try to check aggregator...");
                    aggregatorAlive = aggregatorFeignClient.getHealth();
                } catch (Exception e) {
                    try {Thread.sleep(1000L);} catch (InterruptedException ignored) {}
                }
            } while(!aggregatorAlive);
            System.out.println("aggregator alive.");
            aggregatorFeignClient.postChange();
        }).start();
    }
}
