package org.kiennguyenfpt.datingapp.repositories;

import jakarta.transaction.Transactional;
import org.kiennguyenfpt.datingapp.dtos.responses.AdminUserResponse;
import org.kiennguyenfpt.datingapp.dtos.responses.NearlyUserResponse;
import org.kiennguyenfpt.datingapp.entities.User;
import org.kiennguyenfpt.datingapp.enums.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
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

    @Query(value = "SELECT u.user_id, p.name, u.email, l.address\n" +
            "FROM users u\n" +
            "JOIN user_roles ur ON u.user_id = ur.user_id\n" +
            "JOIN roles r ON ur.role_id = r.role_id\n" +
            "LEFT JOIN profiles p ON u.user_id = p.user_id\n" +
            "LEFT JOIN user_location l ON u.user_id = l.user_id\n" +
            "WHERE r.role_name = 'User'\n" +
            "AND (:keyword IS NULL OR :keyword = '' " +
            "OR u.email LIKE CONCAT('%', :keyword, '%') " +
            "OR p.name LIKE CONCAT('%', :keyword, '%'))", nativeQuery = true)
    List<Object[]> searchAdminUsersByKeywordRaw(@Param("keyword") String keyword);

    default List<AdminUserResponse> searchAdminUsersByKeyword(@Param("keyword") String keyword) {
        // Execute the raw query method
        List<Object[]> results = searchAdminUsersByKeywordRaw(keyword);

        // Convert raw results to UserSearchResponse DTOs
        return results.stream()
                .map(result -> new AdminUserResponse(
                        (Long) result[0],      // userId
                        result[1] != null ? (String) result[1] : "-",  // name, default "-" if null
                        (String) result[2],    // email
                        result[3] != null ? (String) result[3] : "-"   // address, default "-" if null
                ))
                .collect(Collectors.toList());
    }

    @Modifying
    @Query("UPDATE User u SET u.status = CASE WHEN u.status = 'ACTIVE' THEN 'INACTIVE' ELSE 'ACTIVE' END WHERE u.userId = :id")
    int lockOrUnLockUser(@Param("id") Long id);

    @Query(value = "SELECT u.user_id as userId, u.email, u.phone, " +
            "ul.latitude, ul.longitude, ul.address, " +
            "p.name, p.age, p.gender, p.bio, p.avatar, " +
            "GROUP_CONCAT(ph.url) as photoUrls " +  // Sử dụng GROUP_CONCAT để gộp các URL lại thành một chuỗi
            "FROM users u " +
            "JOIN user_location ul ON ul.user_id = u.user_id " +
            "LEFT JOIN profiles p ON p.user_id = u.user_id " +
            "LEFT JOIN photos ph ON ph.profile_id = p.profile_id " +  // Liên kết với bảng photos
            "WHERE (6371000 * acos(cos(radians(:latitude)) * cos(radians(ul.latitude)) * " +
            "cos(radians(ul.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(ul.latitude)))) " +
            "<= :rangeInMeters " +
            "GROUP BY u.user_id, ul.latitude, ul.longitude, ul.address, p.name, p.age, p.gender, p.bio, p.avatar",
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
                        result[8] != null ? Gender.valueOf((String) result[8]) : Gender.OTHER, // gender (Profile)
                        result[9] != null ? (String) result[9] : "-",  // bio (Profile)
                        result[10] != null ? (String) result[10] : "-", // avatar (Profile)
                        result[11] != null ? List.of(((String) result[11]).split(",")) : Collections.emptyList() // photoUrls (Profile)
                ))
                .collect(Collectors.toList());
    }

}
