package com.intters.util.idworker.strategy;

import com.intters.util.idworker.InvalidSystemClock;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @author Ruison
 * @date 2018/7/24.
 */
public class DayPrefixRandomCodeStrategy extends DefaultRandomCodeStrategy {
    private final String dayFormat;
    private String lastDay;

    public DayPrefixRandomCodeStrategy(String dayFormat) {
        this.dayFormat = dayFormat;
    }

    @Override
    public void init() {
        String day = createDate();
        if (day.equals(lastDay)) {
            throw new InvalidSystemClock("init failed for day unrolled");
        }

        lastDay = day;

        availableCodes.clear();
        release();

        prefixIndex = Integer.parseInt(lastDay);
        if (tryUsePrefix()) {
            return;
        }

        throw new InvalidSystemClock("prefix is not available " + prefixIndex);
    }

    private String createDate() {
        return new SimpleDateFormat(dayFormat).format(new Date());
    }

    @Override
    public int next() {
        if (!lastDay.equals(createDate())) {
            init();
        }

        return super.next();
    }
}
