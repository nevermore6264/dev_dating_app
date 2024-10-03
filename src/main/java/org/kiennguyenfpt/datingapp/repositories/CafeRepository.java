package org.kiennguyenfpt.datingapp.repositories;

import org.kiennguyenfpt.datingapp.entities.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CafeRepository extends JpaRepository<Cafe, Long> {
    List<Cafe> findByNameContaining(String name); // Ví dụ: tìm kiếm quán cà phê theo tên
}
