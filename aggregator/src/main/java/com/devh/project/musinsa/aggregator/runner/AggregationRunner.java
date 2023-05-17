package com.devh.project.musinsa.aggregator.runner;

import com.devh.project.musinsa.aggregator.executor.AggregationExecutor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 최초 실행 시 집계 데이터 적재 로직 1회 수행
 */
@Component
public class AggregationRunner implements ApplicationRunner {

    private final AggregationExecutor aggregationExecutor;

    public AggregationRunner(AggregationExecutor aggregationExecutor) {
        this.aggregationExecutor = aggregationExecutor;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        aggregationExecutor.execute();
    }
}
