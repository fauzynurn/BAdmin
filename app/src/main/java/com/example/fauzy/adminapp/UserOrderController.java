package com.example.fauzy.adminapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserOrderController implements Serializable {
    List<UserOrder> uOrderList = new ArrayList<>();
    List<DetailOrder> foodSummaryList = new ArrayList<>();
    List<DetailOrder> drinkSummaryList = new ArrayList<>();
    List<String> summaryList = new ArrayList<>();
    String summaryString = "";

    public UserOrderController(List<UserOrder> list) {
        uOrderList = list;
    }

    public void categorizeDetailOrder() {
        for (UserOrder uo : uOrderList) {
            for (DetailOrder detailOrder : uo.getDetailOrder()) {
                if (detailOrder.getJenis().equals("f")) {
                    foodSummaryList.add(detailOrder);
                }else{
                    drinkSummaryList.add(detailOrder);
                }
            }
//            String concatedString = uo.getDetailOrder().get(0);
//            for(int i = 1;i<uo.getDetailOrder().size();i++){
//                concatedString = concatedString + " " + "+" + " " + uo.getDetailOrder().get(i);
//            }
//            int index = findDetailOrder(concatedString);
//            if(index != -1){
//                summaryList.get(index).incrementQty();
//            }else {
//                DetailOrder detailOrder = new DetailOrder();
//                detailOrder.setNamaMenu(concatedString);
//                summaryList.add(detailOrder);
//            }
        }
    }

    public void simplifyOrders(List<DetailOrder> list) {
        for(int a = 0; a < list.size();a++){
            int copyIndex = a;
            copyIndex++;
            int index = copyIndex;
            for(int i = index; i<list.size();i++) {
                if(list.get(i).getNamaMenu().equals(list.get(a).getNamaMenu())){
                    list.get(a).incrementQty();
                    list.remove(i);
                    i--;
                }
            }
        }
    }

    public void writeDataToSummaryList(){
        for(DetailOrder detailOrder : foodSummaryList){
            String finalString = detailOrder.getNamaMenu() + "(" + detailOrder.getQty() + ")";
            summaryList.add(finalString);
            summaryString = summaryString + finalString + "\n";
        }
        for(DetailOrder detailOrder : drinkSummaryList){
            String finalString = detailOrder.getNamaMenu() + "(" + detailOrder.getQty() + ")";
            summaryList.add(finalString);
            summaryString = summaryString + finalString + "\n";
        }
    }

//    public int findDetailOrder(String string, List<DetailOrder> x){
//        for(int i=0; i<x.size();i++){
//            if(x.get(i).getNamaMenu().equals(string)){
//                return i;
//            }
//        }
//        return -1;
//    }

}
