package com.hyunseo.covidreserve.repository;

import com.hyunseo.covidreserve.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ihyeonseo
 */
public interface AdminRepository extends JpaRepository<Admin, Long> {
}
