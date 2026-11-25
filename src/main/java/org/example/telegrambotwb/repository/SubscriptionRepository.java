package org.example.telegrambotwb.repository;


import org.example.telegrambotwb.entity.subscription.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findByChatId(Long chatId);


}
