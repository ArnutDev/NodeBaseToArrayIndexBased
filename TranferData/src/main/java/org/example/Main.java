package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String filePath = "raw-data.txt";

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(new File(filePath));

            JsonNode nodesArray = rootNode.get("nodes");
            JsonNode edgesArray = rootNode.get("edges");

            List<String> Nodes = new ArrayList<>();
            List<String> addressIn = new ArrayList<>();
            List<String> addressOut = new ArrayList<>();

            for(JsonNode node : nodesArray){
                String value = node.get("type").asText();
                Nodes.add(value);
            }
            int index = 0;//นับindex ของ addressIn แรกใส่ ''
            for(JsonNode edge : edgesArray){
                String source = edge.get("source").asText();
                if(index!=0){
                    addressIn.add(source);
                }
                else{
                    addressIn.add("\'\'");
                }
                boolean found = false;
                String target = edge.get("target").asText();
                for(JsonNode test : edgesArray){
                    String findSource = test.get("source").asText();
                    if(findSource.equals(target)){
                        addressOut.add(target);
//                        System.out.println("source: "+nextSource+" target: "+target);
                        found = true;
                        break;
                    }
                }
                if(found==false && index!=0){
                    addressOut.add("\'\'");
                }
                index++;
            }

            System.out.println("Nodes: " + Nodes);
            System.out.println("addressIn: " + addressIn);
            System.out.println("addressOut: " + addressOut);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}