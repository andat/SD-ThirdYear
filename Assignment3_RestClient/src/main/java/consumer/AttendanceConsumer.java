package consumer;

import client.HttpClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Attendance;
import consumerContracts.IAttendanceConsumer;
import model.request.AttendanceRequestModel;
import model.request.LoginModel;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AttendanceConsumer implements IAttendanceConsumer {
    private ObjectMapper mapper = new ObjectMapper();


    @Override
    public List<Attendance> getAllAttendance(LoginModel credentials) {
        String url = "/attendances";
        List<Attendance> attendance = new ArrayList<>();
        try{
            String response = HttpClient.getRequest(url, credentials);
            attendance = Arrays.asList(mapper.readValue(response, Attendance[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return attendance;
    }

    public boolean addAttendance(AttendanceRequestModel att, LoginModel credentials){
        boolean added = false;
        String url = "/attendances";
        try {
            String body = mapper.writeValueAsString(att);
            added = HttpClient.postRequest(url, new StringEntity(body), credentials);
        } catch (IOException e){
            e.printStackTrace();
        }
        return added;
    }

    @Override
    public boolean editAttendance(AttendanceRequestModel att, int id, LoginModel credentials) {
        boolean added = false;
        String url = "/attendances/" + id;
        try{
            String body = mapper.writeValueAsString(att);
            added = HttpClient.putRequest(url, new StringEntity(body), credentials);
        } catch(IOException e){
            e.printStackTrace();
        }
        return added;
    }

    @Override
    public boolean deleteAttendance(int id, LoginModel credentials) {
       boolean deleted = false;
       String url = "/attendances/" + id;
       try {
           deleted = HttpClient.deleteRequest(url, credentials);
       } catch (IOException e) {
           e.printStackTrace();
       }
        return deleted;
    }

    @Override
    public List<Attendance> getAttendanceByLabId(int labId, LoginModel credentials) {
        String url = "/attendances/labs/" + labId;
        List<Attendance> attendance = new ArrayList<>();
        try{
            String response = HttpClient.getRequest(url, credentials);
            attendance = Arrays.asList(mapper.readValue(response, Attendance[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return attendance;
    }
}