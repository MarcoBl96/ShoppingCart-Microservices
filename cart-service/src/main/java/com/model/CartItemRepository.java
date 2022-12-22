package com.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Modifying
    @Transactional
    @Query(value="DELETE FROM cartitem where cart_id= :cartId", nativeQuery=true)
    void deleteCartItems(@Param("cartId") Long cartId);
}
