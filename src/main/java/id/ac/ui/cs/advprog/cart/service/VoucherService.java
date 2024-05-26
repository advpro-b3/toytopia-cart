package id.ac.ui.cs.advprog.cart.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class VoucherService {
    private final String VOUCHER_API_BASE_URL = "http://35.240.180.63/voucher/";

    private final RestTemplate restTemplate;

    public VoucherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, Object> getVoucherByCode(String voucherCode) {
        String url = VOUCHER_API_BASE_URL + voucherCode;
        try {
            return restTemplate.getForObject(url, Map.class);
        } catch (Exception e) {
            return null;
        }
    }
}