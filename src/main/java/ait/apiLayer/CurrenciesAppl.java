package ait.apiLayer;

import ait.apiLayer.dto.ResponseDto;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Scanner;

public class CurrenciesAppl {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Enter the currency you want to convert (for example USD):");
            String from = scanner.next().toUpperCase();

            System.out.println("Enter the conversion amount (for example 100):");
            double amount = scanner.nextDouble();

            System.out.println("Enter the currency to convert to (for example EUR):");
            String to = scanner.next().toUpperCase();

            RestTemplate restTemplate = new RestTemplate();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://api.apilayer.com/fixer/latest")
                    .queryParam("base", from)
                    .queryParam("symbol", to)
                    .queryParam("apikey", "P6mJ2a8tjq1KF4avsor3bWPT9m5jTP6o");

            URI url = builder.build().toUri();

            RequestEntity<String> request = new RequestEntity<>(HttpMethod.GET, url);

            ResponseEntity<ResponseDto> response = restTemplate.exchange(request, ResponseDto.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                Double rate = response.getBody().getRates().get(to);
                if (rate != null) {
                    double result = rate * amount;
                    System.out.println("Your amount is " + result);
                } else {
                    System.out.println("Failed to get course");
                }
            } else {
                System.out.println("Error in request: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.out.println("Error, most likely you entered a non-existed currency or a string instead of an amount");
        }
        scanner.close();
    }
}
