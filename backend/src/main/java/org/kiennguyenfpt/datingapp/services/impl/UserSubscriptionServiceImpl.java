package org.kiennguyenfpt.datingapp.services.impl;

import org.kiennguyenfpt.datingapp.dtos.responses.SubscriptionPlanResponse;
import org.kiennguyenfpt.datingapp.entities.UserSubscription;
import org.kiennguyenfpt.datingapp.enums.SubscriptionStatus;
import org.kiennguyenfpt.datingapp.repositories.UserSubscriptionRepository;
import org.kiennguyenfpt.datingapp.services.UserSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSubscriptionServiceImpl implements UserSubscriptionService {

    private final UserSubscriptionRepository userSubscriptionRepository;

    @Autowired
    public UserSubscriptionServiceImpl(UserSubscriptionRepository userSubscriptionRepository) {
        this.userSubscriptionRepository = userSubscriptionRepository;
    }

    @Override
    public SubscriptionPlanResponse getActiveSubscriptionByUserId(Long userId) {
        UserSubscription userSubscription = userSubscriptionRepository.findByUser_UserIdAndStatus(userId, SubscriptionStatus.ACTIVE);
        if (userSubscription == null) {
            return null;
        }

        // Map UserSubscription to SubscriptionPlanResponse
        return new SubscriptionPlanResponse(
                userSubscription.getSubscriptionPlan().getPlanId(),
                userSubscription.getSubscriptionPlan().getName(),
                userSubscription.getSubscriptionPlan().getDescription(),
                userSubscription.getSubscriptionPlan().getPrice()
        );
    }
}
