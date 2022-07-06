package com.flyhub.airtelintegration.src;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author Mawanda Joel
 */

@Repository
public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Long> {
}
