package org.kiennguyenfpt.datingapp.services;

import org.kiennguyenfpt.datingapp.dtos.responses.SubscriptionPlanResponse;
import org.kiennguyenfpt.datingapp.entities.UserSubscription;

public interface UserSubscriptionService {
    SubscriptionPlanResponse getActiveSubscriptionByUserId(Long userId);
}
