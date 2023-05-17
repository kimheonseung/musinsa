package com.devh.project.musinsa.backoffice.domain.item.aspect;

import com.devh.project.musinsa.backoffice.domain.common.exception.ErrorCode;
import com.devh.project.musinsa.backoffice.domain.common.exception.ItemException;
import com.devh.project.musinsa.backoffice.feign.AggregatorFeignClient;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class ItemAspect {

    private final AggregatorFeignClient aggregatorFeignClient;

    public ItemAspect(AggregatorFeignClient aggregatorFeignClient) {
        this.aggregatorFeignClient = aggregatorFeignClient;
    }

    @Pointcut("execution(public * com.devh.project.musinsa.backoffice.domain.item.service.*.*(..))")
    public void publicItemService() {}

    @Around("publicItemService()")
    public Object serviceAdvice(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed();
        } catch (ItemException itemException) {
            throw itemException;
        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            throw new ItemException(ErrorCode.ITEM_DUPLICATE_ERROR);
        } catch (Exception e) {
            throw new ItemException(ErrorCode.ITEM_ERROR, e.getMessage());
        }
    }

    @Pointcut("execution(public boolean com.devh.project.musinsa.backoffice.domain.item.service.ItemServiceImpl.add(..))")
    public void ItemServiceAddEvent() {}

    @Pointcut("execution(public boolean com.devh.project.musinsa.backoffice.domain.item.service.ItemServiceImpl.update(..))")
    public void ItemServiceUpdateEvent() {}

    @Pointcut("execution(public boolean com.devh.project.musinsa.backoffice.domain.item.service.ItemServiceImpl.delete(..))")
    public void ItemServiceDeleteEvent() {}

    @AfterReturning("ItemServiceAddEvent() || ItemServiceUpdateEvent() || ItemServiceDeleteEvent()")
    public void aggregationAdvice() throws RuntimeException {
        try {
            aggregatorFeignClient.postChange();
        } catch (Exception ignored) {}
    }
}
