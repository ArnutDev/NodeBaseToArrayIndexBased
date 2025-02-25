package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String filePath = "raw-data.txt"; // พาธไฟล์ .txt

        try {
            // อ่านไฟล์ JSON
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(new File(filePath));

            // ดึงข้อมูล Nodes และ Edges
            JsonNode nodesArray = rootNode.get("nodes");
            JsonNode edgesArray = rootNode.get("edges");

            // สร้างโครงสร้างสำหรับเก็บข้อมูล Nodes
            List<String> Nodes = new ArrayList<>();
            List<String> addressIn = new ArrayList<>();
            List<String> addressOut = new ArrayList<>();

            for(JsonNode node : nodesArray){
                String value = node.get("type").asText();
                Nodes.add(value);
            }
            int index = 0;//for count not first input
            for(JsonNode edge : edgesArray){
                String source = edge.get("source").asText();
                if(index!=0){
                    addressIn.add(source);
                }
                else{
                    addressIn.add("\"\"");
                }
                String target = edge.get("target").asText();
                addressOut.add(target);
                index++;
            }


            // แสดงผล
            System.out.println("Nodes: " + Nodes);
            System.out.println("addressIn: " + addressIn);
            System.out.println("addressOut: " + addressOut);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
