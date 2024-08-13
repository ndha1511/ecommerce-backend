package com.code.salesappbackend.controllers.statistics;

import com.code.salesappbackend.dtos.responses.Response;
import com.code.salesappbackend.dtos.responses.ResponseSuccess;
import com.code.salesappbackend.services.interfaces.statistics.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/daily-revenue")
    public Response dailyRevenue() {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "success",
                statisticsService.getDailyRevenue()
        );
    }

    @GetMapping("/monthly-revenue")
    public Response monthlyRevenue() {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "success",
                statisticsService.getMonthlyRevenue()
        );
    }

    @GetMapping("/total-revenue")
    public Response totalRevenue() {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "success",
                statisticsService.getTotalRevenue()
        );
    }

    @GetMapping("/total-money-per-month/{year}")
    public Response totalMoneyPerMonth(@PathVariable int year) {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "success",
                statisticsService.getTotalMoneyPerMonth(year)
        );
    }
}
