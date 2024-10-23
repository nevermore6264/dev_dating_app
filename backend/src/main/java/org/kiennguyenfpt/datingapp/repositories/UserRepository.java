package org.kiennguyenfpt.datingapp.repositories;

import jakarta.transaction.Transactional;
import org.kiennguyenfpt.datingapp.dtos.responses.NearlyUserResponse;
import org.kiennguyenfpt.datingapp.entities.User;
import org.kiennguyenfpt.datingapp.enums.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    // Tìm kiếm người dùng theo email hoặc name chứa từ khóa
    @Query("SELECT u FROM User u " +
            "JOIN u.userRoles ur " +
            "JOIN ur.role r " +
            "WHERE r.roleName = 'User' " +
            "AND (:keyword IS NULL OR :keyword = '' OR u.email LIKE %:keyword% OR u.phone LIKE %:keyword%)")
    List<User> searchUsersByKeyword(@Param("keyword") String keyword);

    @Modifying
    @Query("UPDATE User u SET u.status = CASE WHEN u.status = 'ACTIVE' THEN 'INACTIVE' ELSE 'ACTIVE' END WHERE u.userId = :id")
    int lockOrUnLockUser(@Param("id") Long id);

    @Query(value = "SELECT u.user_id as userId, u.email, u.phone, " +
            "ul.latitude, ul.longitude, ul.address, " +
            "p.name, p.age, p.gender " +
            "FROM users u " +
            "JOIN user_location ul ON ul.user_id = u.user_id " +
            "LEFT JOIN profiles p ON p.user_id = u.user_id " +
            "WHERE (6371000 * acos(cos(radians(:latitude)) * cos(radians(ul.latitude)) * " +
            "cos(radians(ul.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(ul.latitude)))) " +
            "<= :rangeInMeters",
            nativeQuery = true)
    List<Object[]> findNearbyUsersRaw(@Param("latitude") double latitude,
                                      @Param("longitude") double longitude,
                                      @Param("rangeInMeters") double rangeInMeters);

    default List<NearlyUserResponse> findNearbyUsers(@Param("latitude") double latitude,
                                                     @Param("longitude") double longitude,
                                                     @Param("rangeInMeters") double rangeInMeters) {
        List<Object[]> results = findNearbyUsersRaw(latitude, longitude, rangeInMeters);
        return results.stream()
                .map(result -> new NearlyUserResponse(
                        (Long) result[0],    // userId
                        (String) result[1],  // email
                        (String) result[2],  // phone
                        (Double) result[3],  // latitude
                        (Double) result[4],  // longitude
                        (String) result[5],  // address
                        result[6] != null ? (String) result[6] : "-",  // Default name if null
                        (Integer) result[7], // age (Profile)
                        result[8] != null ? Gender.valueOf((String) result[8]) : Gender.OTHER // gender (Profile)
                ))
                .collect(Collectors.toList());
    }

}
