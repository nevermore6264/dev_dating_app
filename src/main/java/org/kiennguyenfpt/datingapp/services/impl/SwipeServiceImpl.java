package org.kiennguyenfpt.datingapp.services.impl;

import jakarta.transaction.Transactional;
import org.kiennguyenfpt.datingapp.dtos.responses.SwipeResponse;
import org.kiennguyenfpt.datingapp.entities.Like;
import org.kiennguyenfpt.datingapp.entities.Swipe;
import org.kiennguyenfpt.datingapp.entities.User;
import org.kiennguyenfpt.datingapp.exceptions.AlreadyMatchedException;
import org.kiennguyenfpt.datingapp.repositories.LikeRepository;
import org.kiennguyenfpt.datingapp.repositories.SwipeRepository;
import org.kiennguyenfpt.datingapp.repositories.UserRepository;
import org.kiennguyenfpt.datingapp.services.MatchService;
import org.kiennguyenfpt.datingapp.services.SwipeService;
import org.springframework.stereotype.Service;

@Service
public class SwipeServiceImpl implements SwipeService {
    private final SwipeRepository swipeRepository;
    private final UserRepository userRepository;
    private final MatchService matchService;

    public SwipeServiceImpl(SwipeRepository swipeRepository, UserRepository userRepository, MatchService matchService) {
        this.swipeRepository = swipeRepository;
        this.userRepository = userRepository;
        this.matchService = matchService;
    }

    @Override
    @Transactional
    public SwipeResponse swipe(Long userId, Long targetUserId, boolean isLike) {
        User user = userRepository.findById(userId).orElse(null);
        User targetUser = userRepository.findById(targetUserId).orElse(null);

        if (user == null || targetUser == null) {
            throw new IllegalArgumentException("User or Target User not found");
        }

        // Kiểm tra xem targetUser có profile hay không
        if (targetUser.getProfile() == null) {
            throw new IllegalArgumentException("Target User does not have a profile.");
        }

        // Kiểm tra nếu hai người đã match trước đó
        boolean hasMatched = matchService.hasMatched(userId, targetUserId);
        if (hasMatched) {
            throw new AlreadyMatchedException("You have already matched with this user.");
        }

        // Logic to handle swipe action
        boolean isMatch = false;

            Swipe swipe = new Swipe();
            swipe.setUser(user);
            swipe.setTargetUser(targetUser);
            swipe.setLike(isLike);
            swipeRepository.save(swipe);    // Lưu lại swipe, dù là like hay dislike

        if (isLike) {
            // Kiểm tra nếu target user đã like user trước đó (reciprocal like)
            Swipe reciprocalSwipe = swipeRepository.findByUser_UserIdAndTargetUser_UserId(targetUserId, userId);
            if (reciprocalSwipe != null && reciprocalSwipe.isLike()) {
                // Nếu reciprocal swipe là like, tạo match
                matchService.createMatch(user, targetUser);
                isMatch = true;
            }
        }else {

        }
        return new SwipeResponse(isMatch);
    }
}
