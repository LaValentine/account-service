package lav.valentine.accountserver.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.Scheduled;

import java.math.BigInteger;

@Slf4j
@Configuration
@EnableAspectJAutoProxy
@Aspect
public class StatisticConfiguration {
    private BigInteger getAmount = BigInteger.ZERO;
    private BigInteger addAmount = BigInteger.ZERO;
    private BigInteger getAmountTotal = BigInteger.ZERO;
    private BigInteger addAmountTotal = BigInteger.ZERO;

    @Before("execution(public lav.valentine.accountserver.dto.ResponseDto resetStatistic(..))")
    public void resetStatistic() {
        log.info("Statistic reset");
        getAmount = BigInteger.ZERO;
        addAmount = BigInteger.ZERO;
        getAmountTotal = BigInteger.ZERO;
        addAmountTotal = BigInteger.ZERO;
    }

    @Before("execution(Long getAmount(..))")
    public void beforeGetAmount() {
        getAmount = getAmount.add(BigInteger.ONE);
        getAmountTotal = getAmountTotal.add(BigInteger.ONE);
    }

    @Before("execution(void addAmount(..))")
    public void beforeAddAmount() {
        addAmount = addAmount.add(BigInteger.ONE);
        addAmountTotal = addAmountTotal.add(BigInteger.ONE);
    }

    @Scheduled(fixedDelayString = "${statistic.time-interval}")
    public void monitorGetAmount() {
        log.info("Total requests getAmount per 30 seconds = " + getAmount.toString());
        log.info("Total requests addAmount per 30 seconds = " + addAmount.toString());
        log.info("Total requests getAmount = " + getAmountTotal.toString());
        log.info("Total requests addAmount = " + addAmountTotal.toString());

        getAmount = BigInteger.ZERO;
        addAmount = BigInteger.ZERO;
    }
}