package org.kiennguyenfpt.datingapp.services.impl;

import org.kiennguyenfpt.datingapp.dtos.responses.SwipeResponse;
import org.kiennguyenfpt.datingapp.entities.User;
import org.kiennguyenfpt.datingapp.repositories.UserRepository;
import org.kiennguyenfpt.datingapp.services.SwipeService;
import org.springframework.stereotype.Service;

@Service
public class SwipeServiceImpl implements SwipeService {
    private UserRepository userRepository;

    public SwipeServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public SwipeResponse swipe(Long userId, Long targetUserId, boolean isLike) {
        User user = userRepository.findById(userId).orElse(null);
        User targetUser = userRepository.findById(targetUserId).orElse(null);

        if (user == null || targetUser == null) {
            throw new IllegalArgumentException("User or Target User not found");
        }

        // Logic to handle swipe action
        boolean isMatch = false;
        if (isLike) {
            // Check if target user has already liked the current user
            if (targetUser.getLikedUsers().contains(user)) {
                isMatch = true;
                // Add each other to matches
                user.getMatches().add(targetUser);
                targetUser.getMatches().add(user);
            }
            user.getLikedUsers().add(targetUser);
        } else {
            user.getDislikedUsers().add(targetUser);
        }

        userRepository.save(user);
        userRepository.save(targetUser);

        return new SwipeResponse(isMatch);
    }

}
