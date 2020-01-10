/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import DTO.QueryDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author jonab
 */
public class ValidateUrls {

    public ValidateUrls() {
    }

    public List filterUrls(List<QueryDTO> urls) {
        List<String> filteredUrls = new ArrayList();
        for (int i = 0; i < urls.size(); i++) {

            if (!urls.get(i).getUrl().contains("soeg") && !urls.get(i).getUrl().contains("/id")
                    && !urls.get(i).getUrl().contains("annonce") && !urls.get(i).getUrl().contains("reg-")) {
                filteredUrls.add(urls.get(i).getUrl().replace("\"", ""));
                System.out.println(urls.get(i).getUrl());
            }
            if (urls.get(i).getUrl().split("/").length > 5 && !urls.get(i).getUrl().contains("?")
                    && !urls.get(i).getUrl().contains("%") && !urls.get(i).getUrl().contains("annonce") && !urls.get(i).getUrl().contains("id")) {
                filteredUrls.add(urls.get(i).getUrl().replace("\"", ""));
                System.out.println(urls.get(i).getUrl());

            }

        }
        if (filteredUrls.isEmpty()) {
            for (int i = 0; i < urls.size(); i++) {
                if (urls.get(i).getUrl().contains("soeg")) {
                    filteredUrls.add(urls.get(i).getUrl().replace("\"", ""));
                }
            }
        }
        return filteredUrls;
    }

    public String fixQuery(String query) {
        query = query.replace("å", "aa");
        query = query.replace("æ", "ae");
        query = query.replace("ø", "oe");
        return query;
    }

    public List createLinks(String query, List<QueryDTO> urls) {
        query = fixQuery(query);
        List<String> filteredUrls = filterUrls(urls);
        System.out.println("list size: " + filteredUrls.size());
        List<String> finalUrls = new ArrayList();
        for (int i = 0; i < filteredUrls.size(); i++) {
            if (filteredUrls.get(i).contains(query) && !filteredUrls.get(i).contains("soeg")) {
                String[] urlSplit = filteredUrls.get(i).split(query);
                String url = urlSplit[0] + query;
                if (url.split("/").length > 4) {
                    finalUrls.add(url);
                    System.out.println(url);
                }
                //System.out.println(urls.get(i).getUrl());
            } else {
                finalUrls.add(filteredUrls.get(i));
                System.out.println(filteredUrls.get(i));
            }
        }
        List<String> removedDuplicates = finalUrls.stream().distinct().collect(Collectors.toList());
        System.out.println("list without dups:" + removedDuplicates.size());
        System.out.println("list with dups:" + finalUrls.size());
        return removedDuplicates;
    }
}
