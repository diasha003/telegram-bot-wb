package org.example.telegrambotwb.service.subscription;


import org.example.telegrambotwb.entity.subscription.Subscription;
import org.example.telegrambotwb.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public boolean hasSubscriptions(Long chatId){

        List<Subscription> subscriptions= subscriptionRepository.findByChatId(chatId);

        System.out.println(subscriptions);

        return false;

    }
}
