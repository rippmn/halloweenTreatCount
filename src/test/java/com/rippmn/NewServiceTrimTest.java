package com.rippmn;

import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

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
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(SpringRunner.class)
public class NewServiceTrimTest {

    @MockBean
    private TrickOrTreatEventRepository mockRepo;

    @Autowired
    private NewService testService;

    @TestConfiguration
    static class TestContextConfig {

        @Bean
        public NewService newService() {
            TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
            return new NewService();
        }

        @Bean
        public Clock getClock() {
            return Clock.systemDefaultZone();
        }
    }

    @org.junit.Before
    public void init(){
        when(mockRepo.getTtsByYear(2020)).thenReturn(new ArrayList<TrickorTreatEvent>());
    }

    @Test
    public void testNoTrim() {
        
        ReflectionTestUtils.setField(testService, "clock", Clock.fixed(Instant.parse("2020-11-01T03:06:00Z"), ZoneOffset.UTC));

        List<Integer> result = testService.getTotalsByTime();
        System.out.println(result);
        Assert.assertEquals("array size", 150, result.size());
        for (Integer integer : result) {
            Assert.assertEquals("zero value", 0, integer.intValue());
        }
    }

    @Test
    public void testListTrim() {
        ReflectionTestUtils.setField(testService, "clock", Clock.fixed(Instant.parse("2020-10-31T22:06:00Z"), ZoneOffset.UTC));
        List<Integer> result = testService.getTotalsByTime();
        System.out.println(result);
        Assert.assertEquals("array size", 5, result.size());
        for (Integer integer : result) {
            Assert.assertEquals("zero value", 0, integer.intValue());
        }
    }


    public static void main(String[] args) {
        System.out.println(Locale.getDefault());
        System.out.println(TimeZone.getDefault());

        Clock testClock = Clock.fixed(Instant.parse("2020-10-31T22:06:00Z"), ZoneOffset.UTC);

        System.out.println(testClock.getZone());
        System.out.println(new Date(testClock.millis()));
        System.out.println(new Timestamp(testClock.millis()));

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        System.out.println(new Date(testClock.millis()));
        System.out.println(new Timestamp(testClock.millis()));
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
        System.out.println(new Date(testClock.millis()));
        System.out.println(new Timestamp(testClock.millis()));
        Calendar c = Calendar.getInstance();
        System.out.println(c.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        System.out.println(sdf.format(c.getTime()));
    }

}
