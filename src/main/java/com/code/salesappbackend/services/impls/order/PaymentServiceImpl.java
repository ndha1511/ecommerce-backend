package com.code.salesappbackend.services.impls.order;

import com.code.salesappbackend.configurations.VnpConfig;
import com.code.salesappbackend.exceptions.DataNotFoundException;
import com.code.salesappbackend.models.enums.OrderStatus;
import com.code.salesappbackend.models.order.Order;
import com.code.salesappbackend.models.order.PaymentSecurityHash;
import com.code.salesappbackend.repositories.order.OrderRepository;
import com.code.salesappbackend.repositories.order.PaymentSecurityHashRepository;
import com.code.salesappbackend.services.interfaces.order.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private final OrderRepository orderRepository;

    @Value("${front-end.url}")
    private String vnp_ReturnUrl;

    private final PasswordEncoder passwordEncoder;
    private final PaymentSecurityHashRepository paymentSecurityHashRepository;

    public PaymentServiceImpl(PasswordEncoder passwordEncoder, PaymentSecurityHashRepository paymentSecurityHashRepository, OrderRepository orderRepository) {
        this.passwordEncoder = passwordEncoder;
        this.paymentSecurityHashRepository = paymentSecurityHashRepository;
        this.orderRepository = orderRepository;
    }



    @Override
    public String payment(String orderId, HttpServletRequest req)  {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        long amount = Integer.parseInt(req.getParameter("amount"))* 100L;


        String vnp_TxnRef = VnpConfig.getRandomNumber(8);
        String vnp_IpAddr = VnpConfig.getIpAddress(req);
        String bankCode = req.getParameter("bankCode");

        String vnp_TmnCode = VnpConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);
        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }

        vnp_Params.put("vnp_Locale", "vn");
        String code = saveSecurityHash(orderId);
        String vnp_ReturnUrl_rs = vnp_ReturnUrl;
        vnp_ReturnUrl_rs += "/payment/success";
        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl_rs + "?orderId=" + orderId + "&code=" + code);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (!fieldValue.isEmpty())) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VnpConfig.hmacSHA512(VnpConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        return VnpConfig.vnp_PayUrl  + "?" + queryUrl;
    }

    @Override
    public String paymentSuccess(HttpServletRequest req) throws DataNotFoundException {
        String orderId = req.getParameter("orderId");
        String code = req.getParameter("code");
        PaymentSecurityHash paymentSecurityHash = paymentSecurityHashRepository.findByOrderId(orderId)
                .orElseThrow(() -> new DataNotFoundException("order not found"));
        if(passwordEncoder.matches(code, paymentSecurityHash.getHashCode())) {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new DataNotFoundException("order not found"));
            order.setOrderStatus(OrderStatus.PAID);
            orderRepository.save(order);
            paymentSecurityHashRepository.delete(paymentSecurityHash);
        } else {
            throw new DataNotFoundException("hash code not match");
        }
        return orderId;
    }

    private String saveSecurityHash(String orderId) {
        PaymentSecurityHash paymentSecurityHash = paymentSecurityHashRepository
                .findByOrderId(orderId).orElse(new PaymentSecurityHash());
        String originCode = generateRandomString(15);
        paymentSecurityHash.setOrderId(orderId);
        paymentSecurityHash.setHashCode(passwordEncoder.encode(originCode));
        paymentSecurityHashRepository.save(paymentSecurityHash);
        return originCode;
    }

    private String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }

        return sb.toString();
    }
}
