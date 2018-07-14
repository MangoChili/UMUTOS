package com.example.ding.umutos.business;

import com.example.ding.umutos.objects.Order;
import com.example.ding.umutos.persistence.OrderPersistence;
import com.example.ding.umutos.persistence.OrderPersistenceStub;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AccessOrderTest {
  private AccessOrders accessOrders;
  private OrderPersistence orderPersistence;

  @Before
    public void setup() {
      orderPersistence = mock(OrderPersistence.class);
      accessOrders = new AccessOrders(orderPersistence);
  }


    @After
    public void tearDown(){
      accessOrders = null ; orderPersistence = null ;
  }

    @Test
    public void testOrderHistory() {
      System.out.println("\nStarting test testOrderHistory\n");
      String[] orderInfo1 = {"firstName1", "lastName1", "r3y0b6", "2046666666", "Mars"};
      String[] orderInfo2 = {"firstName2", "lastName2", "r3y0b7", "2046666667", "Heaven"};
      final List<Order> orders1 = new ArrayList<>();
      final List<Order> orders2 = new ArrayList<>();
      orders1.add(new Order("book1", 1, 2, 1));
      orders2.add(new Order("book2", 3, 4, 11));
      when(orderPersistence.getBuyerOrders(1)).thenReturn(orders1);
      when(orderPersistence.getSellerOrders(1)).thenReturn(orders2);

      accessOrders.buyerOrderHistory(1);
      accessOrders.sellerOrderHistory(1);
      verify(orderPersistence).getBuyerOrders(1);
      verify(orderPersistence).getSellerOrders(1);

      System.out.println("\nStarting test testInsertOrder\n");

    }


    @Test
    public void InsertOrder(){
      System.out.println("\nStarting test testInsertOrder\n");
      final List<Order> orders = new ArrayList<>();
      String[] orderInfo1 = {"firstName1", "lastName1", "r3y0b6", "2046666666", "Mars"};
      orders.add(new Order("book1", 1, 2, 1));
      final Order order=new Order("book2", 3, 4, 3);
      order.setAddress(orderInfo1[4]);
      order.setPhoneNumber(orderInfo1[3]);
      order.setPostCode(orderInfo1[2]);
      order.setLastName(orderInfo1[1]);
      order.setFirstName(orderInfo1[0]);
      Boolean result;

      when(orderPersistence.insertOrder(order)).thenReturn(order);

      result = accessOrders.insertOrder(order);
      assertTrue(result.equals(true));

      verify(orderPersistence).insertOrder(order);

      orderPersistence = new OrderPersistenceStub();
      accessOrders = new AccessOrders(orderPersistence);

      String[] orderInfo3 = {"firstName3", "lastName3", "r3y0b7", "2046666664", "Heaves"};
      Order newOrder = new Order("book3", 3, 3, 4);
      newOrder.setAddress(orderInfo3[4]);
      newOrder.setPhoneNumber(orderInfo3[3]);
      newOrder.setPostCode(orderInfo3[2]);
      newOrder.setLastName(orderInfo3[1]);
      newOrder.setFirstName(orderInfo3[0]);
      accessOrders.insertOrder(newOrder);

      assertTrue(accessOrders.getOrder().size() == 3);
      System.out.println("\nStarting test testInsertOrder\n");
    }


}
