package org.hits.backend.hackathon_tusur.core.collecting_money;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CollectingMoneyScheduler {
    private final CollectingMoneyService collectingMoneyService;

    //TODO: сильно мелкие тайминги, сделано для тестирования
    @Scheduled(cron = "0 * * * * *")
    public void activateCollectingMoney() {
        collectingMoneyService.activateCollectingMoney();
    }

    @Scheduled(fixedRate = 1000)
    public void test() {
        collectingMoneyService.test();
    }

    @Scheduled(cron = "0 * * * * *")
    public void deactivateCollectingMoney() {
        collectingMoneyService.deactivateCollectingMoney();
    }
}
