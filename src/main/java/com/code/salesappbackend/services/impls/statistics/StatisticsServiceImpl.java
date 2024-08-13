package com.code.salesappbackend.services.impls.statistics;

import com.code.salesappbackend.dtos.responses.statistics.TotalMoneyPerMonth;
import com.code.salesappbackend.repositories.order.OrderRepository;
import com.code.salesappbackend.services.interfaces.statistics.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {
    private final OrderRepository orderRepository;

    @Override
    public Double getDailyRevenue() {
        return orderRepository.getDailyRevenue();
    }

    @Override
    public Double getMonthlyRevenue() {
        return orderRepository.getMonthlyRevenue();
    }

    @Override
    public Double getTotalRevenue() {
        return orderRepository.getTotalRevenue();
    }

    @Override
    public List<TotalMoneyPerMonth> getTotalMoneyPerMonth(int year) {
        return orderRepository.getTotalMoneyPerMonth(year);
    }
}
