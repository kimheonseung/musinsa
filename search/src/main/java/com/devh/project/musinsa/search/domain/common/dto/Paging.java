package com.devh.project.musinsa.search.domain.common.dto;

import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class Paging {
    /* 총 데이터 갯수 */
    private final long total;
    /* 한 페이지에 보여질 갯수 */
    private final int rows;
    /* 현재 페이지 번호 */
    private final int page;
    /* 목록 사이즈 */
    private final static int pageListSize = 5;
    /* 총 페이지 번호 */
    private final int totalPage;
    /* 시작 페이지 번호, 끝 페이지 번호 */
    private final int start, end;
    /* 이전, 다음 */
    private final boolean prev, next;
    /* 페이지 번호 목록 */
    private final List<Integer> pageList;

    private Paging(long total,
                   int rows,
                   int page,
                   int totalPage,
                   int start,
                   int end,
                   boolean prev,
                   boolean next,
                   List<Integer> pageList) {
        this.total = total;
        this.rows = rows;
        this.page = page;
        this.totalPage = totalPage;
        this.start = start;
        this.end = end;
        this.prev = prev;
        this.next = next;
        this.pageList = pageList;
    }


    public static Paging create(int page, int rows, long total) {

        final int tempEnd = (int) (Math.ceil(page / (double) pageListSize)) * pageListSize;
        final int start = tempEnd - (pageListSize - 1);
        final int totalPage = (int) Math.ceil(total / (double) rows);
        final int end = Math.min(totalPage, tempEnd);

        return new Paging(total,
                rows,
                page,
                totalPage,
                start,
                end,
                start > 1,
                totalPage > tempEnd,
                IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList()));
    }
}
