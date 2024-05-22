package com.platzi.pizza.persistence.repository;

import com.platzi.pizza.persistence.entity.OrderEntity;
import com.platzi.pizza.persistence.projection.OrderSummary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends ListCrudRepository<OrderEntity, Integer> {
    List<OrderEntity> findAllByDateAfter(LocalDateTime date);
    List<OrderEntity> findAllByMethodIn(List<String> methods);

    @Query(value = "SELECT * FROM pizza_order  WHERE id_customer = :id", nativeQuery = true)
    List<OrderEntity> findCustomerOrder(@Param("id") String id);

    @Query(value="Select po.id_order as idOrder, c.name as customerName, po.date as orderDate, po.total as orderTotal, GROUP_CONCAT(p.name) as pizzaNames from pizza_order po inner join customer c on c.id_customer = po.id_customer inner join order_item oi on oi.id_order = po.id_order inner join pizza p on p.id_pizza = oi.id_pizza where po.id_order = :orderId group by po.id_order, c.name, po.total;", nativeQuery = true)
    OrderSummary findSummary(@Param("orderId") int orderId);

}