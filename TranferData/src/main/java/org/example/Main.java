package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String filePath = "raw-data.txt"; // พาธไฟล์ .txt

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder jsonData = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                jsonData.append(line); // อ่านไฟล์ JSON ทั้งหมด
            }

            // แปลง String เป็น JSON Object เพื่อจะเรียกใช้แบบ key-value ได้
            JSONObject jsonObject = new JSONObject(jsonData.toString());

            // ดึงข้อมูล Nodes และ Edges
            JSONArray nodesArray = jsonObject.getJSONArray("nodes");
            JSONArray edgesArray = jsonObject.getJSONArray("edges");

            // สร้างโครงสร้างสำหรับเก็บข้อมูล Nodes
            List<String> Nodes = new ArrayList<>();
            List<String> addressIn = new ArrayList<>();
            List<String> addressOut = new ArrayList<>();

            for (int i = 0; i < nodesArray.length(); i++) {
                JSONObject node = nodesArray.getJSONObject(i);
                Nodes.add(node.getString("type")); // ดึงประเภทของ Node
                addressIn.add(""); // ค่าเริ่มต้น (จะอัปเดตทีหลัง)
                addressOut.add(""); // ค่าเริ่มต้น (จะอัปเดตทีหลัง)
            }

            // กำหนด addressIn และ addressOut จาก edgesArray
            for (int i = 0; i < edgesArray.length(); i++) {
                JSONObject edge = edgesArray.getJSONObject(i);
                String source = edge.getString("source");
                String target = edge.getString("target");

                // หา index ของ source และ target
                int sourceIndex = findNodeIndex(nodesArray, source);
                int targetIndex = findNodeIndex(nodesArray, target);

                if (targetIndex != -1) {
                    addressIn.set(targetIndex, source);
                }
                if (sourceIndex != -1) {
                    addressOut.set(sourceIndex, target);
                }
            }

            // แสดงผล
            System.out.println("Nodes: " + Nodes);
            System.out.println("addressIn: " + addressIn);
            System.out.println("addressOut: " + addressOut);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ฟังก์ชันค้นหา index ของ node จาก ID
    private static int findNodeIndex(JSONArray nodesArray, String nodeId) {
        for (int i = 0; i < nodesArray.length(); i++) {
            if (nodesArray.getJSONObject(i).getString("id").equals(nodeId)) {
                return i;
            }
        }
        return -1; // ไม่พบ
    }
}
