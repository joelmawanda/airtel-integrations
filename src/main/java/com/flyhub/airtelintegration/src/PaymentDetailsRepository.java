package com.flyhub.airtelintegration.src;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 *
 * @author Mawanda Joel
 */

@Repository
public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Long> {
}
