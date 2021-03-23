package Lab8;

import Lab7.URLDepthPair;

import java.net.*;
import java.util.Scanner;
/*
public class CrawlerTask implements Runnable {
    private URLPool pool;

    public CrawlerTask(URLDepthPair link) {
        pool = new URLPool();
        pool.AddLink(link);
    }

    @Override
    public void run() {
        URLDepthPair link = pool.GetLink();
        if (link == null) return;
        System.out.println(link.toString());
        System.out.println(Thread.activeCount());
        Crawler.CountURLs++;
        if(link.getDepth() == Crawler.getMaxDepth()) return;

        findLinks(link);
    }

    private void findLinks(URLDepthPair link)
    {
        try {
            URL url = new URL(link.getURL());

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            Scanner scanner = new Scanner(connection.getInputStream());


            while (scanner.findWithinHorizon("<a\\s+(?:[^>]*?\\s+)?href=([\"'])(.*?)\\1", 0) != null) {
                String newURL = scanner.match().group(2);
                URLDepthPair newLink =  createNewLink(newURL, link);
                if (newLink == null) continue;
                CreateNewThread(newLink);
            }
        }
        catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private URLDepthPair createNewLink(String newURL, URLDepthPair link){
        if (newURL.startsWith("/")) {
            newURL = link.getURL() + newURL;
        }
        else if (!newURL.startsWith("http")) return null;

        return new URLDepthPair(newURL, link.getDepth() + 1);
    }

    private void CreateNewThread(URLDepthPair link) {
        CrawlerTask task = new CrawlerTask(link);
        task.run();
    }
}
*/
