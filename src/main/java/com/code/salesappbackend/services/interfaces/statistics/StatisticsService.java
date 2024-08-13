package com.code.salesappbackend.services.interfaces.statistics;

import com.code.salesappbackend.dtos.responses.statistics.TotalMoneyPerMonth;

import java.util.List;

public interface StatisticsService {
    Double getDailyRevenue();
    Double getMonthlyRevenue();
    Double getTotalRevenue();
    List<TotalMoneyPerMonth> getTotalMoneyPerMonth(int year);
}
