package org.kiennguyenfpt.datingapp.repositories;

import org.kiennguyenfpt.datingapp.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findByUser_UserId(Long userId);
    Profile findByUser_Email(String email);

    // Tìm tất cả user_id mà current user đã swipe (like/dislike)
    @Query("SELECT s.targetUser.userId FROM Swipe s WHERE s.user.userId = :currentUserId")
    List<Long> findSwipedUserIdsByUserId(@Param("currentUserId") Long currentUserId);

    // Lấy tất cả profile không nằm trong danh sách đã swipe và khác current user
    List<Profile> findAllByUser_UserIdNotInAndUser_UserIdNot(List<Long> excludedUserIds, Long currentUserId);
}
