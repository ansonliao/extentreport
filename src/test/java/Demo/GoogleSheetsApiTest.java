package Demo;

import com.google.gdata.client.spreadsheet.*;
import com.google.gdata.data.spreadsheet.*;
import com.google.gdata.util.*;

import java.io.IOException;
import java.net.*;
import java.util.*;

/**
 * Created by cpuser on 3/12/15.
 */
public class GoogleSheetsApiTest {
    public static void main(String[] agrs) throws IOException, ServiceException {

        String key = "1_8yFGKWkaEiY-6lLkaHeX-BUO-ds9O5zcoEupCo0RY4";
        String url_prefix = "https://spreadsheets.google.com/feeds/worksheets/";
        String url_tail = "/public/full";
        String url = url_prefix + key + url_tail;

        SpreadsheetService service = new SpreadsheetService("GoogleSpreadsheet");

        URL SPREADSHEET_FEED_URL = new URL(url);
        SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
        List<SpreadsheetEntry> spreadsheets = feed.getEntries();

        if (spreadsheets.size() == 0) {
            // TODO: There were no spreadsheets, act accordingly.
        }

        for (SpreadsheetEntry spreadSheet : spreadsheets) {
            System.out.println(spreadSheet.getTitle().getPlainText());
        }

    }
}
