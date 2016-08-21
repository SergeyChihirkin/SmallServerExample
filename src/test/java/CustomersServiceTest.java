import junit.framework.TestCase;
import org.apache.commons.codec.binary.Base64;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import service.customers.model.Customer;
import service.customers.model.GetCustomersResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashSet;

public class CustomersServiceTest {
    private static final String GET_CUSTOMERS_URL = "http://localhost:8080/customers/getCustomers";

    private HttpHeaders getHeaders(String username, String password){
        String plainCredentials= username + ":" + password;
        String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        return headers;
    }

    @Test
    public void sendCorrectRequest() throws IOException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<>(getHeaders("admin_username", "admin_password"));

        ResponseEntity<GetCustomersResponse> response = restTemplate.exchange(GET_CUSTOMERS_URL, HttpMethod.GET, request,
                GetCustomersResponse.class);

        assert response.getStatusCode() == HttpStatus.OK;

        HashSet<Customer> customers = new HashSet<>(response.getBody().getCustomers());

        HashSet<Customer> expectedCustomers = new HashSet<>(Arrays.asList(new Customer("John", "Doe"),
                new Customer("Anna", "Smith"), new Customer("Peter", "Jones")));

        assert customers.equals(expectedCustomers);
    }

    @Test
    public void wrongAutentificationTest() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> request = new HttpEntity<>(getHeaders("unknown_user", "unknown_password"));

            restTemplate.exchange(GET_CUSTOMERS_URL, HttpMethod.GET, request, GetCustomersResponse.class);

            TestCase.fail("Expected a RestClientException to be thrown");
        } catch (RestClientException restClientException) {
            Assert.assertThat(restClientException.getMessage(), Is.is("401 Unauthorized"));
        }
    }

    @Test
    public void wrongAuthorizationTest() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> request = new HttpEntity<>(getHeaders("user_username", "user_password"));

            restTemplate.exchange(GET_CUSTOMERS_URL, HttpMethod.GET, request, GetCustomersResponse.class);

            TestCase.fail("Expected a RestClientException to be thrown");
        } catch (RestClientException restClientException) {
            Assert.assertThat(restClientException.getMessage(), Is.is("403 Forbidden"));
        }
    }
}
