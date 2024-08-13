package com.code.salesappbackend.repositories.order;

import com.code.salesappbackend.dtos.responses.statistics.TotalMoneyPerMonth;
import com.code.salesappbackend.models.order.Order;
import com.code.salesappbackend.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends BaseRepository<Order, String> {

    @Query(value = "select sum(o.discountedAmount) from Order o " +
            "where function('DATE', o.orderDate) = current_date")
    Double getDailyRevenue();

    @Query(value = "select sum(o.discountedAmount) from Order o " +
            "where extract(month from o.orderDate) = extract(month from current_date) " +
            "and extract(year from o.orderDate) = extract(year from current_date )")
    Double getMonthlyRevenue();

    @Query(value = "select sum(o.discountedAmount) from Order o")
    Double getTotalRevenue();

    @Query(value = "select " +
            "new com.code.salesappbackend.dtos.responses.statistics.TotalMoneyPerMonth(sum(o.discountedAmount), extract(month from o.orderDate), extract(year from o.orderDate)) " +
            "from Order o where extract(year from o.orderDate) = :year " +
            "group by extract(month from o.orderDate), extract(year from o.orderDate)")
    List<TotalMoneyPerMonth> getTotalMoneyPerMonth(int year);

}