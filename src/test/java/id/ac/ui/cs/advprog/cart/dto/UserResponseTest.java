package id.ac.ui.cs.advprog.cart.dto;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class UserResponseTest {

    @Test
    void testNoArgsConstructor() {
        UserResponse userResponse = new UserResponse();
        assertNotNull(userResponse);
    }

    @Test
    void testAllArgsConstructor() {
        Long id = 1L;
        String username = "user1";
        String email = "user1@example.com";
        String firstName = "John";
        String lastName = "Doe";
        String address = "123 Main St";
        String role = "USER";

        UserResponse userResponse = new UserResponse(id, username, email, firstName, lastName, address, role);

        assertEquals(id, userResponse.getId());
        assertEquals(username, userResponse.getUsername());
        assertEquals(email, userResponse.getEmail());
        assertEquals(firstName, userResponse.getFirstName());
        assertEquals(lastName, userResponse.getLastName());
        assertEquals(address, userResponse.getAddress());
        assertEquals(role, userResponse.getRole());
    }

    @Test
    void testSettersAndGetters() {
        UserResponse userResponse = new UserResponse();

        Long id = 1L;
        String username = "user1";
        String email = "user1@example.com";
        String firstName = "John";
        String lastName = "Doe";
        String address = "123 Main St";
        String role = "USER";

        userResponse.setId(id);
        userResponse.setUsername(username);
        userResponse.setEmail(email);
        userResponse.setFirstName(firstName);
        userResponse.setLastName(lastName);
        userResponse.setAddress(address);
        userResponse.setRole(role);

        assertEquals(id, userResponse.getId());
        assertEquals(username, userResponse.getUsername());
        assertEquals(email, userResponse.getEmail());
        assertEquals(firstName, userResponse.getFirstName());
        assertEquals(lastName, userResponse.getLastName());
        assertEquals(address, userResponse.getAddress());
        assertEquals(role, userResponse.getRole());
    }

    @Test
    void testEqualsAndHashCode() {
        Long id = 1L;
        String username = "user1";
        String email = "user1@example.com";
        String firstName = "John";
        String lastName = "Doe";
        String address = "123 Main St";
        String role = "USER";

        UserResponse userResponse1 = UserResponse.builder()
                .id(id)
                .username(username)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .address(address)
                .role(role)
                .build();

        UserResponse userResponse2 = UserResponse.builder()
                .id(id)
                .username(username)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .address(address)
                .role(role)
                .build();

        assertEquals(userResponse1, userResponse2);
        assertEquals(userResponse1.hashCode(), userResponse2.hashCode());
    }

    @Test
    void testToString() {
        Long id = 1L;
        String username = "user1";
        String email = "user1@example.com";
        String firstName = "John";
        String lastName = "Doe";
        String address = "123 Main St";
        String role = "USER";

        UserResponse userResponse = UserResponse.builder()
                .id(id)
                .username(username)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .address(address)
                .role(role)
                .build();

        String expectedToString = "UserResponse(id=1, username=user1, email=user1@example.com, firstName=John, lastName=Doe, address=123 Main St, role=USER)";
        assertEquals(expectedToString, userResponse.toString());
    }

    @Test
    void testBuilder() {
        Long id = 1L;
        String username = "user1";
        String email = "user1@example.com";
        String firstName = "John";
        String lastName = "Doe";
        String address = "123 Main St";
        String role = "USER";

        UserResponse userResponse = UserResponse.builder()
                .id(id)
                .username(username)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .address(address)
                .role(role)
                .build();

        assertEquals(id, userResponse.getId());
        assertEquals(username, userResponse.getUsername());
        assertEquals(email, userResponse.getEmail());
        assertEquals(firstName, userResponse.getFirstName());
        assertEquals(lastName, userResponse.getLastName());
        assertEquals(address, userResponse.getAddress());
        assertEquals(role, userResponse.getRole());
    }
}
