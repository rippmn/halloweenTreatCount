package com.rippmn.halloween;

import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.rippmn.halloween.domain.TrickorTreatEvent;
import com.rippmn.halloween.persistence.TrickOrTreatEventRepository;
import com.rippmn.halloween.service.NewService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class NewServiceTest {
    @MockBean
    private TrickOrTreatEventRepository mockRepo;

    @Autowired
    private NewService testService;

    @TestConfiguration
    static class TestContextConfig {

        @Bean
        public NewService newService() {
            return new NewService();
        }

        @Bean
        public Clock getClock(){
            return Clock.systemDefaultZone();
        }
    }

    @Test
    public void testNodata() {
        when(mockRepo.getTtsByYear(2020)).thenReturn(new ArrayList<TrickorTreatEvent>());
        List<Integer> result = testService.getTotalsByTime();
        System.out.println(result);
        Assert.assertEquals("array size", 150, result.size());
        for (Integer integer : result) {
            Assert.assertEquals("zero value", 0, integer.intValue());
        }
    }

    @Test
    public void testListTrim() {
        when(mockRepo.getTtsByYear(2020)).thenReturn(new ArrayList<TrickorTreatEvent>());
        //instead lets rip the bandaid off and just reset env vars to current system time values that make this work
        
        Instant.now(Clock.fixed( 
            Instant.parse("2020-10-31T22:06:00Z"),
            ZoneOffset.UTC));
        List<Integer> result = testService.getTotalsByTime();
        System.out.println(result);
        Assert.assertEquals("array size", 3, result.size());
        for (Integer integer : result) {
            Assert.assertEquals("zero value", 0, integer.intValue());
        }
    }


    @Test
    public void testData() {
        ArrayList<TrickorTreatEvent> ttes = new ArrayList<TrickorTreatEvent>();
        ttes.add(new TrickorTreatEvent());
        ttes.get(0).setCount(10);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 22);
        c.set(Calendar.MINUTE, 1);
        ttes.get(0).setEventDateTime(new Timestamp(c.getTimeInMillis()));
        when(mockRepo.getTtsByYear(2020)).thenReturn(ttes);

        List<Integer> result = testService.getTotalsByTime();
        System.out.println(result);
        Assert.assertEquals("array size", 150, result.size());
        Assert.assertEquals("zero value", 0, result.get(0).intValue());
        Assert.assertEquals("first value", 10, result.get(1).intValue());
        for (int i = 2; i < result.size(); i++) {
            Assert.assertEquals("rest of array", 10, result.get(i).intValue());
        }
    }

    @Test
    public void testData2() {
        ArrayList<TrickorTreatEvent> ttes = new ArrayList<TrickorTreatEvent>();
        ttes.add(new TrickorTreatEvent());
        ttes.add(new TrickorTreatEvent());
        ttes.add(new TrickorTreatEvent());
        ttes.get(0).setCount(10);
        ttes.get(1).setCount(5);
        ttes.get(2).setCount(2);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 22);
        c.set(Calendar.MINUTE, 1);
        ttes.get(0).setEventDateTime(new Timestamp(c.getTimeInMillis()));
        c.set(Calendar.MINUTE, 2);
        ttes.get(1).setEventDateTime(new Timestamp(c.getTimeInMillis()));
        c.set(Calendar.MINUTE, 3);
        ttes.get(2).setEventDateTime(new Timestamp(c.getTimeInMillis()));
        when(mockRepo.getTtsByYear(2020)).thenReturn(ttes);

        List<Integer> result = testService.getTotalsByTime();
        System.out.println(result);
        Assert.assertEquals("array size", 150, result.size());
        Assert.assertEquals("zero value", 0, result.get(0).intValue());
        Assert.assertEquals("first value", 10, result.get(1).intValue());
        Assert.assertEquals("second value", 17, result.get(2).intValue());
        for (int i = 3; i < result.size(); i++) {
            Assert.assertEquals("rest of array", 17, result.get(i).intValue());
        }
    }

    

}
