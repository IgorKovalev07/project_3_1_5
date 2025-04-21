package ru.kovalev.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.kovalev.model.UserDTO;

import java.util.List;


public class MainApp {
    public static void main(String[] args) {
        String cookie;
        RestTemplate restTemplate = new RestTemplate();
        UserClient userClient = new UserClient(restTemplate);
        ResponseEntity<List<UserDTO>> response = userClient.getAllUser();
        List<UserDTO> users = response.getBody();
        for (UserDTO user : users) {
            if (user != null) {
                System.out.println(user);
            }
        }
        String sessionId = response.getHeaders().getFirst("Set-Cookie");
        cookie = userClient.parseSessionId(sessionId);

        UserDTO user = new UserDTO();
        user.setId(3L);
        user.setName("James");
        user.setLastName("Brown");
        user.setAge((byte) 28);

        String one = userClient.save(user, cookie);

        UserDTO userUpdate = new UserDTO();
        userUpdate.setId(3L);
        userUpdate.setName("Thomas");
        userUpdate.setLastName("Shelby");
        userUpdate.setAge((byte) 28);

        String two = userClient.update(userUpdate, cookie);

        String three = userClient.delete(3L, cookie);

        System.out.println(one + two + three);

    }

}
