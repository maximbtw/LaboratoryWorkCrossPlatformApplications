package Lab8;

import Lab7.URLDepthPair;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Crawler {
    private URLPool pool;


    private String URL;
    private static int maxDepth;
    private static int countThreads;

    public static int currentWaitingThreads = 0;
    public static int CountURLs = 0;

    public static int getMaxDepth() { return maxDepth; }


    public Crawler(String URL, int maxDepth, int countThreads){
        this.URL = URL;
        Crawler.maxDepth = maxDepth;
        Crawler.countThreads = countThreads;
        pool = new URLPool();
        pool.addLink(new URLDepthPair(URL,0));
    }

    public void run() {
        while(pool.getPoolSize() > 0  || Thread.activeCount() > 0){
            if(pool.getPoolSize()==0) continue;
            URLDepthPair link = pool.getLink();
            System.out.println(link.toString());
            System.out.println(Thread.activeCount());
            if(link.getDepth() == maxDepth) continue;
            Crawler.CountURLs++;
            CrawlerTask task = new CrawlerTask(link);
            task.start();
        }

        System.out.println("Всего ссылок: " + CountURLs);
    }

    public class CrawlerTask extends Thread {
        URLDepthPair link;

        public CrawlerTask(URLDepthPair link) {
            this.link = link;
        }

        @Override
        public void run() {
            findLinks(link);
        }

        private void findLinks(URLDepthPair link)
        {
            try {
                java.net.URL url = new URL(link.getURL());

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                Scanner scanner = new Scanner(connection.getInputStream());

                while (scanner.findWithinHorizon("<a\\s+(?:[^>]*?\\s+)?href=([\"'])(.*?)\\1", 0) != null) {
                    String newURL = scanner.match().group(2);
                    URLDepthPair newLink =  createNewLink(newURL, link);
                    if (newLink == null) continue;
                    pool.addLink(newLink);
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
    }

    public static void main(String[] args){
        Crawler crawler = new Crawler("http://www.cs.caltech.edu/courses/cs11",2 ,10);
        crawler.run();

        if(currentWaitingThreads == countThreads) System.exit(1);
    }
}
