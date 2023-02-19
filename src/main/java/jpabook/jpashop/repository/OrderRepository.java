package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order){
        em.persist(order);
    }

    public Order findOne(Long id){
        return em.find(Order.class, id);
    }

    public List<Order> findAll(OrderSearch orderSearch){

        return em.createQuery("select o from Order o join o.member m" + // 동적 쿼리가 되어야 함
                        " where o.status = :status " +
                        " and m.name like :name", Order.class)
                .setParameter("status",orderSearch.getOrderstatus())
                .setParameter("name",orderSearch.getMemberName())
                .setMaxResults(1000) // 최대 1000건의 주문정보 조회
                .getResultList();
    }
}
