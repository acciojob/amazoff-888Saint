package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    private HashMap<String, Order> orderMap;
    private HashMap<String, DeliveryPartner> partnerMap;
    private HashMap<String, HashSet<String>> partnerToOrderMap;
    private HashMap<String, String> orderToPartnerMap;

    public OrderRepository(){
        this.orderMap = new HashMap<String, Order>();
        this.partnerMap = new HashMap<String, DeliveryPartner>();
        this.partnerToOrderMap = new HashMap<String, HashSet<String>>();
        this.orderToPartnerMap = new HashMap<String, String>();
    }

    public void saveOrder(Order order){
        // your code here
        orderMap.put(order.getId(), order);
    }

    public void savePartner(String partnerId){
        // your code here
        // create a new partner with given partnerId and save it
        DeliveryPartner deliveryPartner = new DeliveryPartner(partnerId);
        partnerMap.put(partnerId, deliveryPartner);
        partnerToOrderMap.put(partnerId, new HashSet<>());
    }

    public void saveOrderPartnerMap(String orderId, String partnerId){
        if(orderMap.containsKey(orderId) && partnerMap.containsKey(partnerId)){
            // your code here
            //add order to given partner's order list
            //increase order count of partner
            //assign partner to this order
            partnerToOrderMap.get(partnerId).add(orderId);
            orderToPartnerMap.put(orderId, partnerId);
            DeliveryPartner partner = partnerMap.get(partnerId);
            partner.setNumberOfOrders(partner.getNumberOfOrders() + 1);
        }
    }

    public Order findOrderById(String orderId){
        // your code here
        return orderMap.get(orderId);
    }

    public DeliveryPartner findPartnerById(String partnerId){
        // your code here
        return partnerMap.get(partnerId);
    }

    public Integer findOrderCountByPartnerId(String partnerId){
        // your code here
        return partnerToOrderMap.getOrDefault(partnerId, new HashSet<>()).size();
    }

    public List<String> findOrdersByPartnerId(String partnerId){
        // your code here
        return new ArrayList<>(partnerToOrderMap.getOrDefault(partnerId, new HashSet<>()));
    }

    public List<String> findAllOrders(){
        // your code here
        // return list of all orders
        return new ArrayList<>(orderMap.keySet());
    }

    public void deletePartner(String partnerId){
        // your code here
        // delete partner by ID
        if (partnerMap.containsKey(partnerId))
        {
            HashSet<String> orders = partnerToOrderMap.getOrDefault(partnerId, new HashSet<>());
            for (String oid : orders)
            {
                orderToPartnerMap.remove(oid);
            }
            partnerToOrderMap.remove(partnerId);
            partnerMap.remove(partnerId);
        }
    }

    public void deleteOrder(String orderId){
        // your code here
        // delete order by ID
        if (orderMap.containsKey(orderId))
        {
            String pid = orderToPartnerMap.get(orderId);
            if (pid != null && partnerToOrderMap.containsKey(pid))
            {
                partnerToOrderMap.get(pid).remove(orderId);
                DeliveryPartner dp = partnerMap.get(pid);
                dp.setNumberOfOrders(dp.getNumberOfOrders()-1);
                orderToPartnerMap.remove(orderId);
            }
            orderMap.remove(orderId);
        }
    }

    public Integer findCountOfUnassignedOrders(){
        // your code here
        //return (int) orderMap.keySet().stream().filter(orderId -> !orderToPartnerMap.containsKey(orderId)).count();

        int count = 0;
        for (String oid : orderMap.keySet())
        {
            if (!orderToPartnerMap.containsKey(oid))
            {
                count++;
            }
        }
        return count;
    }

    public Integer findOrdersLeftAfterGivenTimeByPartnerId(String timeString, String partnerId){
        // your code here
        if (!partnerToOrderMap.containsKey(partnerId)) return null;

        int givenTime = convertTimeToMinutes(timeString);
        HashSet<String> orders = partnerToOrderMap.get(partnerId);
        int count = 0;

        for (String oid : orders)
        {
            Order order = orderMap.get(oid);
            if (order != null && order.getDeliveryTime() > givenTime)
            {
                count++;
            }
        }
        return count;
    }

    public String findLastDeliveryTimeByPartnerId(String partnerId){
        // your code here
        // code should return string in format HH:MM
        if (!partnerToOrderMap.containsKey(partnerId)) return null;

        HashSet<String> orders = partnerToOrderMap.get(partnerId);
        int latestTime = 0;

        for (String oid : orders)
        {
            Order order = orderMap.get(oid);
            if (order != null)
            {
                latestTime = Math.max(latestTime, order.getDeliveryTime());
            }
        }
        return convertMintutesToTimeString(latestTime);
    }

    private int convertTimeToMinutes(String timeString)
    {
        String timeParts[] = timeString.split(":");
        int hours = Integer.parseInt(timeParts[0]);
        int minutes = Integer.parseInt(timeParts[1]);
        return hours * 60 + minutes;
    }

    private String convertMintutesToTimeString(int latestTime) {
        int hours = latestTime / 60;
        int minutes = latestTime % 60;
        return String.format("%02d:%02d", hours, minutes);
    }
}