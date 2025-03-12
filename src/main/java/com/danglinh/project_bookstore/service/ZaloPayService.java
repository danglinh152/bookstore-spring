package com.danglinh.project_bookstore.service;

import com.danglinh.project_bookstore.config.ZaloPayConfig;
import com.danglinh.project_bookstore.domain.DTO.request.ZaloPayOrder;
import com.danglinh.project_bookstore.domain.DTO.response.PaymentUrl;
import com.danglinh.project_bookstore.util.vn.zalopay.crypto.HMACUtil;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.springframework.stereotype.Service;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ZaloPayService {

    private static String getCurrentTimeString(String format) {
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT+7"));
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        fmt.setCalendar(cal);
        return fmt.format(cal.getTimeInMillis());
    }

    public PaymentUrl createOrder(ZaloPayOrder zaloPayOrder) {
        Random rand = new Random();
        int randomId = rand.nextInt(1000000);

        // Check if the order amount is null
        if (zaloPayOrder.getAmount() == null) {
            System.err.println("Order amount is null.");
            return null;
        }

        Map<String, Object> order = new HashMap<>();
        order.put("app_id", ZaloPayConfig.config.get("app_id"));
        order.put("app_trans_id", getCurrentTimeString("yyMMdd") + "_" + randomId);
        order.put("app_time", System.currentTimeMillis());
        order.put("app_user", "user123");
        order.put("amount", zaloPayOrder.getAmount());
        order.put("description", "S10.07 Bookstore - Payment for the order #" + randomId);
        order.put("bank_code", "");
        order.put("item", "[{}]");
        order.put("embed_data", "{}");
        order.put("callback_url", ZaloPayConfig.config.get("callback_url"));

        // Prepare data for HMAC
        String data = String.join("|",
                order.get("app_id").toString(),
                order.get("app_trans_id").toString(),
                order.get("app_user").toString(),
                order.get("amount").toString(),
                order.get("app_time").toString(),
                order.get("embed_data").toString(),
                order.get("item").toString()
        );

        System.out.println("Data for HMAC: " + data);

        // Generate HMAC
        String mac = HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, ZaloPayConfig.config.get("key1"), data);
        order.put("mac", mac);

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(ZaloPayConfig.config.get("endpoint"));

            List<NameValuePair> params = new ArrayList<>();
            for (Map.Entry<String, Object> entry : order.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }

            post.setEntity(new UrlEncodedFormEntity(params));

            try (CloseableHttpResponse response = client.execute(post)) {
                // Check if the response is successful
                if (response.getStatusLine().getStatusCode() == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                    StringBuilder resultJsonStr = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        resultJsonStr.append(line);
                    }

                    // Parse JSON response
                    JSONObject jsonResponse = new JSONObject(resultJsonStr.toString());

                    // Check for the "order_url" key
                    if (jsonResponse.has("order_url")) {
                        return new PaymentUrl(jsonResponse.getString("order_url"));
                    } else {
                        System.err.println("Response does not contain 'order_url': " + jsonResponse.toString());
                        return null;
                    }
                } else {
                    System.err.println("Failed to create order. HTTP error code: " + response.getStatusLine().getStatusCode());
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public String getOrderStatus(String appTransId) {
        String data = ZaloPayConfig.config.get("app_id") + "|" + appTransId + "|" + ZaloPayConfig.config.get("key1");
        String mac = HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, ZaloPayConfig.config.get("key1"), data);

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(ZaloPayConfig.config.get("orderstatus"));

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("app_id", ZaloPayConfig.config.get("app_id")));
            params.add(new BasicNameValuePair("app_trans_id", appTransId));
            params.add(new BasicNameValuePair("mac", mac));

            post.setEntity(new UrlEncodedFormEntity(params));

            try (CloseableHttpResponse response = client.execute(post)) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuilder resultJsonStr = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    resultJsonStr.append(line);
                }

                return resultJsonStr.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\": \"Failed to get order status: " + e.getMessage() + "\"}";
        }
    }

}