package com.devh.project.musinsa.aggregator.domain.item.repository;

import com.devh.project.musinsa.aggregator.domain.item.entity.Item;
import com.devh.project.musinsa.aggregator.domain.projection.ComparisonPriceItemProjection;
import com.devh.project.musinsa.aggregator.domain.projection.LowestPriceItemByBrandProjection;
import com.devh.project.musinsa.aggregator.domain.projection.LowestPriceItemByCategoryProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
    @Query(
            value = "SELECT  " +
                    "    lo.lowestBrandName AS lowestBrandName,  " +
                    "    lo.lowestPrice AS lowestPrice,  " +
                    "    lo.categoryName AS categoryName,  " +
                    "    hi.highestBrandName AS highestBrandName,  " +
                    "    hi.highestPrice AS highestPrice " +
                    "FROM " +
                    "( " +
                    "    SELECT " +
                    "        jb.NAME AS highestBrandName, " +
                    "        p.HIGHEST_PRICE AS highestPrice, " +
                    "        p.CATEGORY_NAME AS categoryName " +
                    "    FROM " +
                    "        ( " +
                    "            SELECT " +
                    "                MAX(PRICE) AS HIGHEST_PRICE, " +
                    "                c.NAME AS CATEGORY_NAME, " +
                    "                c.ID AS CATEGORY_ID " +
                    "            FROM " +
                    "                ITEM i " +
                    "            JOIN " +
                    "                CATEGORY c ON i.CATEGORY_ID = c.ID " +
                    "            GROUP BY " +
                    "                c.NAME " +
                    "        ) AS p " +
                    "    LEFT OUTER JOIN ITEM li ON li.CATEGORY_ID = p.CATEGORY_ID AND li.PRICE = p.HIGHEST_PRICE " +
                    "    JOIN BRAND jb ON li.BRAND_ID = jb.ID " +
                    ") AS hi " +
                    "LEFT OUTER JOIN  " +
                    "( " +
                    "    SELECT " +
                    "        jb.NAME AS lowestBrandName, " +
                    "        p.LOWEST_PRICE AS lowestPrice, " +
                    "        p.CATEGORY_NAME AS categoryName " +
                    "    FROM " +
                    "        ( " +
                    "            SELECT " +
                    "                MIN(PRICE) AS LOWEST_PRICE, " +
                    "                c.NAME AS CATEGORY_NAME, " +
                    "                c.ID AS CATEGORY_ID " +
                    "            FROM " +
                    "                ITEM i " +
                    "            JOIN " +
                    "                CATEGORY c ON i.CATEGORY_ID = c.ID " +
                    "            GROUP BY " +
                    "                c.NAME " +
                    "        ) AS p " +
                    "    JOIN ITEM li ON li.CATEGORY_ID = p.CATEGORY_ID AND li.PRICE = p.LOWEST_PRICE " +
                    "    JOIN BRAND jb ON li.BRAND_ID = jb.ID " +
                    ") AS lo ON hi.categoryName = lo.categoryName ",
            nativeQuery = true
    )
    List<ComparisonPriceItemProjection> findAllLowestHighestBrandItemByCategory();

    @Query(
            value = "SELECT " +
                    "    p.LOWEST_PRICE AS lowestPrice, " +
                    "    p.CATEGORY_NAME AS categoryName, " +
                    "    (SELECT br.NAME FROM ITEM it JOIN BRAND br on it.BRAND_ID = br.ID WHERE it.CATEGORY_ID = p.CATEGORY_ID AND it.PRICE = p.LOWEST_PRICE ORDER BY RAND() LIMIT 1) AS brandName " +
                    "FROM " +
                    "( " +
                    "    SELECT " +
                    "        MIN(PRICE) AS LOWEST_PRICE, " +
                    "        c.NAME AS CATEGORY_NAME, " +
                    "        c.ID AS CATEGORY_ID " +
                    "    FROM " +
                    "        ITEM i " +
                    "    JOIN " +
                    "        CATEGORY c ON i.CATEGORY_ID = c.ID " +
                    "    JOIN " +
                    "        BRAND b ON i.BRAND_ID = b.ID" +
                    "    GROUP BY " +
                    "        CATEGORY_ID " +
                    ") AS p",
            nativeQuery = true
    )
    List<LowestPriceItemByCategoryProjection> findAllLowestPriceItemByCategory();

    @Query(
            value = "SELECT " +
                    "    MIN(i.PRICE) AS price, " +
                    "    b.NAME AS brandName, " +
                    "    c.NAME AS categoryName " +
                    "FROM " +
                    "    ITEM i " +
                    "JOIN " +
                    "    BRAND b ON i.BRAND_ID = b.ID " +
                    "JOIN " +
                    "    CATEGORY c ON i.CATEGORY_ID = c.ID " +
                    "GROUP BY " +
                    "    BRAND_ID, CATEGORY_ID",
            nativeQuery = true
    )
    List<LowestPriceItemByBrandProjection> findAllLowestPriceItemByBrand();
}
