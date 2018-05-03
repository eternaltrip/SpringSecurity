package com.websystique.springmvc;
 
import java.net.URI;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.me.SSH4_secureRESTApi_basic.model.User;

 
public class SpringRestClient {
 
    public static final String REST_SERVICE_URI = "http://localhost:8080/SSH4-secureRESTApi_basic";
 
    /*
     * Add HTTP Authorization header, using Basic-Authentication to send user-credentials.
     */
    private static HttpHeaders getHeaders(){
    	String plainCredentials="admin:admin";
    	String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.add("Authorization", "Basic " + base64Credentials);
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	return headers;
    }
    
    /*
     * Send a GET request to get list of all users.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static void listAllUsers(){
        System.out.println("\nTesting listAllUsers API-----------");
        RestTemplate restTemplate = new RestTemplate(); 
        
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        Object obj = restTemplate.exchange(REST_SERVICE_URI+"/user/", HttpMethod.GET, request, List.class);
        ResponseEntity<List> response = (ResponseEntity<List>) obj;
        List<LinkedHashMap<String, Object>> usersMap = (List<LinkedHashMap<String, Object>>)response.getBody();
        
        if(usersMap!=null){
            for(LinkedHashMap<String, Object> map : usersMap){
                System.out.println("User : id="+map.get("id")+", Name="+map.get("name")+", Age="+map.get("age")+", Salary="+map.get("salary"));;
            }
        }else{
            System.out.println("No user exist----------");
        }
    }
     
    /*
     * Send a GET request to get a specific user.
     */
    private static void getUser(){
        System.out.println("\nTesting getUser API----------");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        ResponseEntity<User> response = restTemplate.exchange(REST_SERVICE_URI+"/user/1", HttpMethod.GET, request, User.class);
        User user = response.getBody();
        System.out.println(user);
    }
     
    /*
     * Send a POST request to create a new user.
     */
    private static void createUser() {
        System.out.println("\nTesting create User API----------");
        RestTemplate restTemplate = new RestTemplate();
        User user = new User(0,"Sarah",51,134);
        HttpEntity<Object> request = new HttpEntity<Object>(user, getHeaders());
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI+"/user/", request, User.class);
        System.out.println("Location : "+uri.toASCIIString());
    }
 
    /*
     * Send a PUT request to update an existing user.
     */
    private static void updateUser() {
        System.out.println("\nTesting update User API----------");
        RestTemplate restTemplate = new RestTemplate();
        User user  = new User(1,"Tomy",33, 70000);
        HttpEntity<Object> request = new HttpEntity<Object>(user, getHeaders());
        ResponseEntity<User> response = restTemplate.exchange(REST_SERVICE_URI+"/user/1", HttpMethod.PUT, request, User.class);
        System.out.println(response.getBody());
    }
 
    /*
     * Send a DELETE request to delete a specific user.
     */
    private static void deleteUser() {
        System.out.println("\nTesting delete User API----------");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        restTemplate.exchange(REST_SERVICE_URI+"/user/3", HttpMethod.DELETE, request, User.class);
    }
 
 
    /*
     * Send a DELETE request to delete all users.
     */
    private static void deleteAllUsers() {
        System.out.println("\nTesting all delete Users API----------");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        restTemplate.exchange(REST_SERVICE_URI+"/user/", HttpMethod.DELETE, request, User.class);
    }
 

    public static void main(String args[]){
//        tomcat 为了安全性，只允许get和post方式的请求，其他的put、delete等请求都是forbidden的状态。所以，建议restfull也不要使用get和post之外的请求
    	
    	
    	listAllUsers();

        getUser();

        createUser();
        listAllUsers();

        updateUser();
        listAllUsers();

        deleteUser();
        listAllUsers();

        deleteAllUsers();
        listAllUsers();
    }
}