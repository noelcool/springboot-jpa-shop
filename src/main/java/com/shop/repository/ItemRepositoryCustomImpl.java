package com.shop.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.domain.constant.ItemSellStatus;
import com.shop.domain.dto.MainItemDto;
import com.shop.domain.dto.QMainItemDto;
import com.shop.domain.dto.search.ItemSearchDto;
import com.shop.domain.entity.Item;
import com.shop.domain.entity.QItem;
import com.shop.domain.entity.QItemImg;
import ognl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public ItemRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private com.querydsl.core.types.dsl.BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus) {
        return searchSellStatus == null ? null : QItem.item.itemSellStatus.eq(searchSellStatus);
    }

    private com.querydsl.core.types.dsl.BooleanExpression regDtsAfter(String searchDateType) {
        LocalDateTime dateTime = LocalDateTime.now();
        if(StringUtils.equals("all", searchDateType) || searchDateType == null) {
            return null;
        } else if(StringUtils.equals("1d", searchDateType)) {
            dateTime = dateTime.minusDays(1);
        } else if(StringUtils.equals("1w", searchDateType)) {
            dateTime = dateTime.minusWeeks(1);
        } else if(StringUtils.equals("1m", searchDateType)) {
            dateTime = dateTime.minusMonths(1);
        } else if(StringUtils.equals("6m", searchDateType)) {
            dateTime = dateTime.minusMonths(6);
        }
        return QItem.item.regTime.after(dateTime);
    }

    private com.querydsl.core.types.dsl.BooleanExpression searchByLike(String searchBy, String searchQuery) {
        if(StringUtils.equals("itemNm", searchBy)) {
            return QItem.item.itemNm.like("%" + searchQuery + "%");
        } else if(StringUtils.equals("createdBy", searchBy)) {
            return QItem.item.createdBy.like("%" + searchQuery + "%");
        }
        return null;
    }

    @Override
    public Page<Item> getAdminItemPage(ItemSearchDto dto, Pageable pageable) {
        QueryResults<Item> results = queryFactory.
                selectFrom(QItem.item).
                where(regDtsAfter(dto.getSearchDateType()),
                        (Predicate) searchSellStatusEq(dto.getSearchSellStatus()),
                        searchByLike(dto.getSearchBy(), dto.getSearchQuery())).
                orderBy(QItem.item.id.desc()).
                offset(pageable.getOffset()).
                limit(pageable.getPageSize()).
                fetchResults();
        List<Item> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    private com.querydsl.core.types.dsl.BooleanExpression itemNmLike(String query) {
        return StringUtils.isEmpty(query) ? null : QItem.item.itemNm.like("%" + query + "%");
    }

    @Override
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        QItem item = QItem.item;
        QItemImg itemImg = QItemImg.itemImg;

        QueryResults<MainItemDto> results = queryFactory.
                select(
                        new QMainItemDto(
                                item.id,
                                item.itemNm,
                                item.itemDetail,
                                itemImg.imgUrl,
                                item.price)
                ).
                from(itemImg).
                join(itemImg.item, item). // itemImg, item join
                where(itemImg.repImgYn.eq("Y")). // 대표 상품 이미지만 가져온다
                where(itemNmLike(itemSearchDto.getSearchQuery())).
                orderBy(item.id.desc()).
                offset(pageable.getOffset()).
                limit(pageable.getPageSize()).
                fetchResults();
        List<MainItemDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }
}
