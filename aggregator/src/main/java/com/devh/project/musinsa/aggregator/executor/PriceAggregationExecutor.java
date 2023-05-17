package com.devh.project.musinsa.aggregator.executor;

import com.devh.project.musinsa.aggregator.domain.item.repository.ItemRepository;
import com.devh.project.musinsa.aggregator.domain.price.comparison.entity.ComparisonPriceItemByCategory;
import com.devh.project.musinsa.aggregator.domain.price.comparison.repository.ComparisonPriceItemByCategoryRepository;
import com.devh.project.musinsa.aggregator.domain.price.lowest.entity.LowestPriceItemByBrand;
import com.devh.project.musinsa.aggregator.domain.price.lowest.entity.LowestPriceItemByCategory;
import com.devh.project.musinsa.aggregator.domain.price.lowest.repository.LowestPriceItemByBrandRepository;
import com.devh.project.musinsa.aggregator.domain.price.lowest.repository.LowestPriceItemByCategoryRepository;
import com.devh.project.musinsa.aggregator.domain.projection.ComparisonPriceItemProjection;
import com.devh.project.musinsa.aggregator.domain.projection.LowestPriceItemByBrandProjection;
import com.devh.project.musinsa.aggregator.exception.AggregationException;
import com.devh.project.musinsa.aggregator.exception.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class PriceAggregationExecutor implements AggregationExecutor {

    private final ItemRepository itemRepository;
    private final LowestPriceItemByCategoryRepository lowestPriceItemByCategoryRepository;
    private final LowestPriceItemByBrandRepository lowestPriceItemByBrandRepository;
    private final ComparisonPriceItemByCategoryRepository comparisonPriceItemByCategoryRepository;

    public PriceAggregationExecutor(ItemRepository itemRepository,
                                    LowestPriceItemByCategoryRepository lowestPriceItemByCategoryRepository,
                                    LowestPriceItemByBrandRepository lowestPriceItemByBrandRepository,
                                    ComparisonPriceItemByCategoryRepository comparisonPriceItemByCategoryRepository) {
        this.itemRepository = itemRepository;
        this.lowestPriceItemByCategoryRepository = lowestPriceItemByCategoryRepository;
        this.lowestPriceItemByBrandRepository = lowestPriceItemByBrandRepository;
        this.comparisonPriceItemByCategoryRepository = comparisonPriceItemByCategoryRepository;
    }

    @Override
    public void execute() {
        lowestPriceItemByCategoryAggregation();
        lowestPriceItemByBrandAggregation();
        comparisonPriceAggregation();
    }

    private void lowestPriceItemByCategoryAggregation() {
        List<LowestPriceItemByCategory> lowestPriceItemByCategories =
                itemRepository.findAllLowestPriceItemByCategory().stream()
                        .map(LowestPriceItemByCategory::createByProjection)
                        .collect(Collectors.toList());
        lowestPriceItemByCategoryRepository.saveAll(lowestPriceItemByCategories);
    }

    private void lowestPriceItemByBrandAggregation() {
        List<LowestPriceItemByBrandProjection> lowestPriceItemByBrandProjections = itemRepository.findAllLowestPriceItemByBrand();
        Map<String, List<LowestPriceItemByBrandProjection>> brandProjections = lowestPriceItemByBrandProjections.stream()
                .collect(Collectors.groupingBy(LowestPriceItemByBrandProjection::getBrandName));

        String lowestBrand = null;
        Long lowestPrice = null;
        for (Map.Entry<String, List<LowestPriceItemByBrandProjection>> entry : brandProjections.entrySet()) {
            if (Objects.isNull(lowestBrand) && Objects.isNull(lowestPrice)) {
                lowestBrand = entry.getKey();
                lowestPrice = entry.getValue().stream().mapToLong(LowestPriceItemByBrandProjection::getPrice).sum();
            } else {
                long price = entry.getValue().stream().mapToLong(LowestPriceItemByBrandProjection::getPrice).sum();
                if (price < lowestPrice) {
                    lowestBrand = entry.getKey();
                    lowestPrice = price;
                }
            }
        }

        if (Objects.isNull(lowestBrand)) {
            throw new AggregationException(ErrorCode.AGGREGATION_ERROR);
        }

        List<LowestPriceItemByBrandProjection> targets = brandProjections.get(lowestBrand);
        LowestPriceItemByBrand lowestPriceItemByBrand = LowestPriceItemByBrand.create(lowestBrand, targets, lowestPrice);
        this.lowestPriceItemByBrandRepository.save(lowestPriceItemByBrand);
    }

    private void comparisonPriceAggregation() {
        Map<String, List<ComparisonPriceItemProjection>> comparisonPriceItemByCategoryMap =
                this.itemRepository.findAllLowestHighestBrandItemByCategory().stream()
                        .collect(Collectors.groupingBy(ComparisonPriceItemProjection::getCategoryName));
        List<ComparisonPriceItemByCategory> comparisonPriceItemByCategories = new ArrayList<>();
        comparisonPriceItemByCategoryMap.forEach((key, value) -> comparisonPriceItemByCategories.add(ComparisonPriceItemByCategory.create(key, value)));
        if (comparisonPriceItemByCategories.size() > 0) {
            comparisonPriceItemByCategoryRepository.saveAll(comparisonPriceItemByCategories);
        }
    }
}
