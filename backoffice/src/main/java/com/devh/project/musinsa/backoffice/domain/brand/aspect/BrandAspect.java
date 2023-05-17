package com.devh.project.musinsa.backoffice.domain.brand.aspect;

import com.devh.project.musinsa.backoffice.domain.common.exception.BrandException;
import com.devh.project.musinsa.backoffice.domain.common.exception.ErrorCode;
import com.devh.project.musinsa.backoffice.feign.AggregatorFeignClient;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class BrandAspect {

    private final AggregatorFeignClient aggregatorFeignClient;

    public BrandAspect(AggregatorFeignClient aggregatorFeignClient) {
        this.aggregatorFeignClient = aggregatorFeignClient;
    }

    @Pointcut("execution(public * com.devh.project.musinsa.backoffice.domain.brand.service.*.*(..))")
    public void publicBrandService() {}

    @Around("publicBrandService()")
    public Object serviceAdvice(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed();
        } catch (BrandException brandException) {
            throw brandException;
        } catch (Exception e) {
            throw new BrandException(ErrorCode.BRAND_ERROR, e.getMessage());
        }
    }

    @Pointcut("execution(public boolean com.devh.project.musinsa.backoffice.domain.brand.service.BrandServiceImpl.update(..))")
    public void brandServiceUpdateEvent() {}

    @Pointcut("execution(public boolean com.devh.project.musinsa.backoffice.domain.brand.service.BrandServiceImpl.delete(..))")
    public void brandServiceDeleteEvent() {}

    @AfterReturning("brandServiceUpdateEvent() || brandServiceDeleteEvent()")
    public void aggregationAdvice() throws RuntimeException {
        try {
            aggregatorFeignClient.postChange();
        } catch (Exception ignored) {}
    }
}
