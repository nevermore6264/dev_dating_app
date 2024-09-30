package org.kiennguyenfpt.datingapp.services.impl;

import jakarta.transaction.Transactional;
import org.kiennguyenfpt.datingapp.dtos.responses.SwipeResponse;
import org.kiennguyenfpt.datingapp.entities.Like;
import org.kiennguyenfpt.datingapp.entities.User;
import org.kiennguyenfpt.datingapp.repositories.LikeRepository;
import org.kiennguyenfpt.datingapp.repositories.UserRepository;
import org.kiennguyenfpt.datingapp.services.MatchService;
import org.kiennguyenfpt.datingapp.services.SwipeService;
import org.springframework.stereotype.Service;

@Service
public class SwipeServiceImpl implements SwipeService {
    private final UserRepository userRepository;
    private final MatchService matchService;
    private final LikeRepository likeRepository;


    public SwipeServiceImpl(UserRepository userRepository, MatchService matchService, LikeRepository likeRepository) {
        this.userRepository = userRepository;
        this.matchService = matchService;
        this.likeRepository = likeRepository;
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

        // Logic to handle swipe action
        boolean isMatch = false;
        if (isLike) {
            // Lưu lượt like vào bảng Like
            Like like = new Like();
            like.setUser(user);
            like.setProfile(targetUser.getProfile());
            likeRepository.save(like);

            // Kiểm tra nếu target user đã like user trước đó (reciprocal like)
            Like reciprocalLike = likeRepository.findByUserAndProfile(targetUser, user.getProfile());
            if (reciprocalLike != null) {
                // Có match
                isMatch = true;
                // Sử dụng MatchService để lưu match
                matchService.createMatch(user, targetUser);
            }
        } else {
            // Xử lý khi user dislike targetUser (có thể lưu trong bảng nếu muốn, hoặc chỉ bỏ qua)
            // Không lưu gì cả, nếu muốn lưu lại thì có thể thêm bảng Dislike tương tự bảng Like
        }

        return new SwipeResponse(isMatch);
    }

}
