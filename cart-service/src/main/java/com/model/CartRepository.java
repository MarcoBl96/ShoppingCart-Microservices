package com.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query(value="select c from Cart c where c.customerId=?1")
    Cart findCartbyCustomerId(Long customer);

    @Modifying
    @Transactional
    @Query(value="UPDATE Cart c set c.totalPrice = :totalPrice where c.cartId = :cartId")
    void updateCartTotalPrice(@Param("totalPrice") double totalPrice, @Param("cartId") long cartId);
}
