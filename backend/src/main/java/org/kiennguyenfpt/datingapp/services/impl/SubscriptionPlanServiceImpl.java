package org.kiennguyenfpt.datingapp.services.impl;

import org.kiennguyenfpt.datingapp.entities.SubscriptionPlan;
import org.kiennguyenfpt.datingapp.repositories.SubscriptionPlanRepository;
import org.kiennguyenfpt.datingapp.services.SubscriptionPlanService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionPlanServiceImpl implements SubscriptionPlanService {

    private final SubscriptionPlanRepository subscriptionPlanRepository;

    public SubscriptionPlanServiceImpl(SubscriptionPlanRepository subscriptionPlanRepository) {
        this.subscriptionPlanRepository = subscriptionPlanRepository;
    }

    public List<SubscriptionPlan> getAllSubscriptionPlans() {
        return subscriptionPlanRepository.findAll();
    }

    @Override
    public SubscriptionPlan updateSubscriptionPlan(Long planId, SubscriptionPlan updatedPlan) {
        // Retrieve the existing plan, apply updates, and save it back to the database
        Optional<SubscriptionPlan> existingPlan;
        existingPlan = subscriptionPlanRepository.findById(planId);
        if (existingPlan.isPresent()) {
            // Update the properties
            SubscriptionPlan subscriptionPlan = existingPlan.get();

            subscriptionPlan.setName(updatedPlan.getName());
            subscriptionPlan.setDescription(updatedPlan.getDescription());
            subscriptionPlan.setPrice(updatedPlan.getPrice());
            subscriptionPlan.setDuration(updatedPlan.getDuration());
            subscriptionPlan.setImageUrl(updatedPlan.getImageUrl());
            subscriptionPlan.setHasLikeLimit(updatedPlan.isHasLikeLimit());
            subscriptionPlan.setHasWatchLike(updatedPlan.isHasWatchLike());
            subscriptionPlan.setHasShowPriority(updatedPlan.isHasShowPriority());
            subscriptionPlan.setHasViewProfile(updatedPlan.isHasViewProfile());
            SubscriptionPlan savedSubscriptionPlan = subscriptionPlanRepository.save(subscriptionPlan);
            return savedSubscriptionPlan;
        } else {
            throw new RuntimeException("Subscription plan not found");
        }
    }
}
