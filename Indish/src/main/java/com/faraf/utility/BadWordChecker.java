package com.faraf.utility;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class BadWordChecker {

    public boolean isBadContent(String content) throws IOException {
        File file = ResourceUtils.getFile("classpath:badwords.json");
        ObjectMapper mapper = new ObjectMapper();

        boolean isBadWord = mapper.readValue(file, new TypeReference<Map<String, List<Map<String, String>>>>() {})
                .values()
                .stream()
                .flatMap(List::stream)
                .map(m -> m.get("word"))
                .anyMatch(content::contains);
        return isBadWord;
    }

}


