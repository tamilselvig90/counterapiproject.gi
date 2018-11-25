package com.counterapi.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CounterApiService {

    private static Logger logger = LoggerFactory.getLogger( CounterApiService.class );
    private static Path path = Paths.get( "src/main/resources/paragraph.txt" );
    private static List<String> stringList;

    static {
        try {
            logger.info( "Reading from the file" );
            stringList = Files.lines( path )
                    .flatMap( (String line) -> Stream.of( line.split( "[\\p{Punct}\\s]+" ) ) )
                    .map( String::toLowerCase )
                    .collect( Collectors.toList() );
        } catch (IOException e) {
            logger.error( "Error occured while reading the file:", e );
        }
    }

    public List getCount(List<String> wordList) {
        List<Map> resultList = new ArrayList<Map>();
        for (String str : wordList) {
            HashMap<String, Long> wordMap = new HashMap<String, Long>();
            wordMap.put( str, (stringList.stream()
                    .filter( s -> s.equals( str.toLowerCase() ) ).count()) );
            resultList.add( wordMap );
        }
        return resultList;
    }

    public String getTopCount(int count) {
        StringBuffer buffer = new StringBuffer();
        stringList.stream()
                .collect( Collectors.toMap( word -> word, word -> 1, Integer::sum ) )
                .entrySet()
                .stream()
                .sorted( (x, y) -> x.getValue() == y.getValue() ? x.getKey().compareTo( y.getKey() ) : y.getValue() - x.getValue() )
                .limit( count )
                .forEach( z -> buffer.append( z.getKey() + "|" + z.getValue() + "\n" ) );
        return buffer.toString();

    }
}
