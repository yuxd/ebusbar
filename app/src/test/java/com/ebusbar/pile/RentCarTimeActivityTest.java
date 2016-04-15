package com.ebusbar.pile;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Jelly on 2016/4/14.
 */
public class RentCarTimeActivityTest {

    @Test
    public void testGetWeek() throws Exception {
        List<Date> list = RentCarTimeActivity.getWeek(7);
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
        for(int i=0;i<list.size();i++){
            List<Date> list1 = RentCarTimeActivity.getHour(list.get(i));
            System.out.println(sdf.format(list1.get(i)));
            SimpleDateFormat sdf1 = new SimpleDateFormat("HH:00");
            for(Date date:list1){
                if(sdf1.format(date).equals("00:00")){
                    System.out.println("23:59");
                }else {
                    System.out.println(sdf1.format(date));
                }
            }
        }
    }

    @Test
    public void testGetHour(){

    }

    @Test
    public void testGetDayOfWeek(){
        Calendar calendar = Calendar.getInstance();
        System.out.println(RentCarTimeActivity.getDayOfWeek(calendar));
    }
}