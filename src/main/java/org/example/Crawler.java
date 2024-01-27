package org.example;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Crawler {

    HashSet<String> urlset;
    int max_depth = 2;
    Crawler(){
        urlset = new HashSet<>();
    }

    public void getpageTextAndLinks(String url, int depth) {
        if (urlset.contains(url) || depth >= max_depth) {
            return;
        }
        if(urlset.add(url)){
            System.out.println(url);
        }
        depth++;
        try{
        Document document = Jsoup.connect(url).timeout(5000).get();
        // INDEXER WORK START HERE
            Indexer indexer = new Indexer(document,url);
        System.out.println(document.title());
        Elements availableLinksOnPage = document.select("a[href]");

        for (Element currentLink : availableLinksOnPage) {
            getpageTextAndLinks(currentLink.attr("abs:href"), depth);
        }
      }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Crawler crawler = new Crawler();
        crawler.getpageTextAndLinks("https://www.javatpoint.com",0);
    }
}