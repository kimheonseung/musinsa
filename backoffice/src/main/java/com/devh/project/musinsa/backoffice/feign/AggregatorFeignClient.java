package com.devh.project.musinsa.backoffice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "aggregatorFeignClient", url = "${aggregator.server}/aggregator/v1/api")
public interface AggregatorFeignClient {
    @PostMapping("/event/change")
    Boolean postChange();
    @GetMapping("/health")
    Boolean getHealth();
}
