import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TranferData {
    public static void main(String[] args) {
        // ระบุพาธไฟล์ .txt ที่ต้องการอ่าน (อยู่ในโฟลเดอร์เดียวกับโปรเจกต์)
        String filePath = "raw-data.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line); // แสดงผลแต่ละบรรทัด
            }
        } catch (IOException e) {
            e.printStackTrace(); // แสดงข้อผิดพลาดหากอ่านไฟล์ไม่ได้
        }
    }
}
