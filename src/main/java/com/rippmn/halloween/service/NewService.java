package com.rippmn.halloween.service;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import com.rippmn.halloween.domain.TrickorTreatEvent;
import com.rippmn.halloween.persistence.TrickOrTreatEventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NewService {
    @Autowired
    private Clock clock;
    
    @Autowired
    private TrickOrTreatEventRepository repo;

    private TreeSet<String> labels = new TreeSet<String>();

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm");

    private static final long DATE_OFFSET = Long
            .parseLong(System.getenv("DATE_OFFSET") != null ? System.getenv("DATE_OFFSET") : "5") * 60 * 60 * 1000;
    private static final long REPORT_INTERVAL = Long
            .parseLong(System.getenv("REPORT_INTERVAL") != null ? System.getenv("REPORT_INTERVAL") : "2") * 60 * 1000;

    public NewService() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,
                (System.getenv("START_HOUR") != null ? Integer.parseInt(System.getenv("START_HOUR")) : 17));
        c.set(Calendar.MINUTE, 0);

        int interval = Integer
                .parseInt(System.getenv("REPORT_INTERVAL") != null ? System.getenv("REPORT_INTERVAL") : "2");

        while (c.get(Calendar.HOUR_OF_DAY) <= (System.getenv("END_HOUR") != null
                ? Integer.parseInt(System.getenv("END_HOUR"))
                : 21)) {
            labels.add(DATE_FORMAT.format(c.getTime()));
            c.set(Calendar.MINUTE, (c.get(Calendar.MINUTE) + interval));
        }
    }

    public List<Integer> getTotalsByTime() {
        HashMap<String, Integer> data = new HashMap<String, Integer>();

        List<TrickorTreatEvent> tts = repo.getTtsByYear(2020);
        String theBucket;
        Integer count;
        for (TrickorTreatEvent tte : tts) {
            theBucket = getBucket(tte.getEventDateTime().getTime());
            count = data.get(theBucket);
            if (count == null) {
                count = 0;
            }
            data.put(theBucket, count + tte.getCount());
        }

        ArrayList<Integer> totals = new ArrayList<Integer>();
        int total = 0;
        //lets try the clock here
        String lastKey = getBucket(clock.millis());
        boolean done = false;
        for (String label : this.labels) {
            if (done) {
                break;
            }

            done = label.equals(lastKey);
            count = data.get(label);
            if (count == null) {
                count = 0;
            }
            total += count;
            totals.add(total);
        }

        return totals;
    }

    private String getBucket(long time) {
        return DATE_FORMAT.format(new Date(time - (time % REPORT_INTERVAL) + REPORT_INTERVAL - DATE_OFFSET));
    }

    public static void main(String[] args) {
        Date d = new Date();

        System.out.println(DATE_FORMAT.format(d));

    }
}
