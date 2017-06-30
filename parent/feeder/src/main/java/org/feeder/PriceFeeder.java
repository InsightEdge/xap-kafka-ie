package org.feeder;

import org.common.PriceFeed;
import org.openspaces.core.GigaSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
public class PriceFeeder {
    static int counter = 1;

    public PriceFeeder() {
        System.out.println("PriceFeeder constructor called.");
    }

    @Autowired
    protected GigaSpace gigaPriceSpace;

    @Scheduled(fixedDelay = 1000)
    public void simulateFeed() {
        try {
            for (int i = 0; i < 2; i++) {
                PriceFeed priceFeed = new PriceFeed();
                priceFeed.setSymbol("A" + i);
                priceFeed.setPrice(counter++ * 1.0f);
                gigaPriceSpace.write(priceFeed, 30000000);
                System.out.println("PriceFeeder writtern to space for i=" + i + ", counter=" + counter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}