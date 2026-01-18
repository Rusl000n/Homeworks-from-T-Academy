package academy.io;

import academy.dto.analytics.AnalyticsDate;

public interface OutputInterface {
    void writeFile(String output, AnalyticsDate analyticsDate) throws Exception;
}
