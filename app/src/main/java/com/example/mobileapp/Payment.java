//package com.example.myapplication;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Toast;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.stripe.android.PaymentConfiguration;
//import com.stripe.android.paymentsheet.PaymentSheet;
//import com.stripe.android.paymentsheet.PaymentSheetResult;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class MainActivity extends AppCompatActivity {
//
//    Button payment;
//    String PublishableKey = "pk_test_51NM9GBGs2sMDKf990LEHvY4NppfibN0veajv6hcN2rYzBEW6QcBMhYEmQ6IaomEGpI29HgmNNAqrphoPcWtPBKPt00AfE4hP9z";
//    String SecretKey = "sk_test_51NM9GBGs2sMDKf99nw5bAr6tUgu9TwtoGNBFLkdNTvbpu4gbpi6HJtirXkOdKAwWuuJnfco49wqTcAxxX7doGeqc00lPhgpjNe";
//    String CustomerId;
//    String EphemeralKey;
//    String ClientSecret;
//
//    PaymentSheet paymentSheet;
//
//    private static final int MONTHLY_PASS_AMOUNT = 19000;
//
//    private static final int FOUR_DAY_PASS_AMOUNT = 5000;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        payment = findViewById(R.id.payment);
//
//        PaymentConfiguration.init(this, PublishableKey);
//
//        paymentSheet = new PaymentSheet(this, paymentSheetResult -> {
//            onPaymentResult(paymentSheetResult);
//        });
//
//        payment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                payment.setEnabled(false);
//                paymentFlow();
//            }
//        });
//
//        StringRequest request = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/customers",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject object = new JSONObject(response);
//                            CustomerId = object.getString("id");
//                            Toast.makeText(MainActivity.this, CustomerId, Toast.LENGTH_SHORT).show();
//                            getEphemeralKey();
//                        } catch (JSONException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                String errorMessage = error.getLocalizedMessage();
//                if (errorMessage == null || errorMessage.isEmpty()) {
//                    errorMessage = "An unknown error occurred.";
//                }
//                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
//            }
//        }) {
//            @Nullable
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> header = new HashMap<>();
//                header.put("Authorization", "Bearer " + SecretKey);
//                return header;
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(request);
//    }
//
//    private void paymentFlow() {
//        paymentSheet.presentWithPaymentIntent(ClientSecret, new PaymentSheet.Configuration("This is Stripe", new PaymentSheet.CustomerConfiguration(
//                CustomerId,
//                EphemeralKey
//        )));
//    }
//
//    private void onPaymentResult(PaymentSheetResult paymentSheetResult) {
//        payment.setEnabled(true); // Re-enable the button after the payment process is finished
//
//        if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
//            Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show();
//        } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
//            // Handle payment failure
//        } else if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
//            // Handle payment cancellation
//        }
//
//        // Generate a new ClientSecret for the next payment attempt
//        getClientSecret(CustomerId, EphemeralKey);
//    }
//
//    private void getEphemeralKey() {
//        StringRequest request = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/ephemeral_keys",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject object = new JSONObject(response);
//                            EphemeralKey = object.getString("id");
//                            Toast.makeText(MainActivity.this, EphemeralKey, Toast.LENGTH_SHORT).show();
//                            getClientSecret(CustomerId, EphemeralKey);
//                        } catch (JSONException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                String errorMessage = error.getLocalizedMessage();
//                if (errorMessage == null || errorMessage.isEmpty()) {
//                    errorMessage = "An unknown error occurred.";
//                }
//                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
//            }
//        }) {
//            @Nullable
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> header = new HashMap<>();
//                header.put("Authorization", "Bearer " + SecretKey);
//                header.put("Stripe-Version", "2020-08-27");
//                return header;
//            }
//
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("customer", CustomerId);
//                return params;
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(request);
//    }
//
//    private void getClientSecret(String CustomerId, String EphemeralKey) {
//        StringRequest request = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/payment_intents",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject object = new JSONObject(response);
//                            ClientSecret = object.getString("client_secret");
//                            Toast.makeText(MainActivity.this, ClientSecret, Toast.LENGTH_SHORT).show();
//                            payment.setEnabled(true);
//
//                        } catch (JSONException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                String errorMessage = error.getLocalizedMessage();
//                if (errorMessage == null || errorMessage.isEmpty()) {
//                    errorMessage = "An unknown error occurred.";
//                }
//                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
//            }
//        }) {
//            @Nullable
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> header = new HashMap<>();
//                header.put("Authorization", "Bearer " + SecretKey);
//                return header;
//            }
//
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("amount", "1000");
//                params.put("currency", "usd");
//                params.put("payment_method_types[]", "card");
//                return params;
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(request);
//    }
//}

package com.example.mobileapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Payment extends AppCompatActivity {

    Button payment;
    RadioGroup radioGroup;
    RadioButton monthlyPassRadioButton;
    RadioButton fourDayPassRadioButton;

    String PublishableKey = "pk_test_51NM9GBGs2sMDKf990LEHvY4NppfibN0veajv6hcN2rYzBEW6QcBMhYEmQ6IaomEGpI29HgmNNAqrphoPcWtPBKPt00AfE4hP9z";
    String SecretKey = "sk_test_51NM9GBGs2sMDKf99nw5bAr6tUgu9TwtoGNBFLkdNTvbpu4gbpi6HJtirXkOdKAwWuuJnfco49wqTcAxxX7doGeqc00lPhgpjNe";
    String CustomerId;
    String EphemeralKey;
    String ClientSecret;

    PaymentSheet paymentSheet;

    private static final int MONTHLY_PASS_AMOUNT = 19000;
    private static final int FOUR_DAY_PASS_AMOUNT = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        payment = findViewById(R.id.payment);
        radioGroup = findViewById(R.id.radioGroup);
        monthlyPassRadioButton = findViewById(R.id.monthlyPassRadioButton);
        fourDayPassRadioButton = findViewById(R.id.fourDayPassRadioButton);


        PaymentConfiguration.init(this, PublishableKey);

        paymentSheet = new PaymentSheet(this, paymentSheetResult -> {
            onPaymentResult(paymentSheetResult);
        });

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payment.setEnabled(false);
                paymentFlow();
            }
        });

        Button paymentHistoryButton = findViewById(R.id.payment_history_button);
        paymentHistoryButton.setOnClickListener(v -> {
            Intent intent = new Intent(Payment.this, PaymentHistoryActivity.class);
            startActivity(intent);
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                getClientSecret(CustomerId, EphemeralKey);
            }
        });

        StringRequest request = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/customers",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            CustomerId = object.getString("id");
                            Toast.makeText(Payment.this, CustomerId, Toast.LENGTH_SHORT).show();
                            getEphemeralKey();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMessage = error.getLocalizedMessage();
                if (errorMessage == null || errorMessage.isEmpty()) {
                    errorMessage = "An unknown error occurred.";
                }
                Toast.makeText(Payment.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", "Bearer " + SecretKey);
                return header;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void paymentFlow() {
        paymentSheet.presentWithPaymentIntent(ClientSecret, new PaymentSheet.Configuration("This is Stripe", new PaymentSheet.CustomerConfiguration(
                CustomerId,
                EphemeralKey
        )));
    }

    private void onPaymentResult(PaymentSheetResult paymentSheetResult) {
        payment.setEnabled(true);

        if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            String referenceCode = generateReferenceCode();
            String itemName = radioGroup.getCheckedRadioButtonId() == R.id.monthlyPassRadioButton ? "Monthly Pass" : "Four Day Pass";
            saveReferenceCode(referenceCode, itemName);
        } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
        } else if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
        }

        getClientSecret(CustomerId, EphemeralKey);
    }


    private String generateReferenceCode() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    private void saveReferenceCode(String referenceCode, String itemName) {
        SharedPreferences sharedPreferences = getSharedPreferences("payment_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> referenceCodeSet = sharedPreferences.getStringSet("payment_reference_codes", new HashSet<>());

        Set<String> newReferenceCodeSet = new HashSet<>(referenceCodeSet);
        String paymentInfo = referenceCode + "|" + itemName;
        newReferenceCodeSet.add(paymentInfo);
        editor.putStringSet("payment_reference_codes", newReferenceCodeSet);
        editor.apply();
    }

    private void getEphemeralKey() {
        StringRequest request = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/ephemeral_keys",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            EphemeralKey = object.getString("id");
                            Toast.makeText(Payment.this, EphemeralKey, Toast.LENGTH_SHORT).show();
                            getClientSecret(CustomerId, EphemeralKey);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMessage = error.getLocalizedMessage();
                if (errorMessage == null || errorMessage.isEmpty()) {
                    errorMessage = "An unknown error occurred.";
                }
                Toast.makeText(Payment.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", "Bearer " + SecretKey);
                header.put("Stripe-Version", "2020-08-27");
                return header;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer", CustomerId);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void getClientSecret(String CustomerId, String EphemeralKey) {
        int selectedAmount = radioGroup.getCheckedRadioButtonId() == R.id.monthlyPassRadioButton ?  MONTHLY_PASS_AMOUNT:  FOUR_DAY_PASS_AMOUNT;

        StringRequest request = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/payment_intents",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            ClientSecret = object.getString("client_secret");
                            Toast.makeText(Payment.this, ClientSecret, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMessage = error.getLocalizedMessage();
                if (errorMessage == null || errorMessage.isEmpty()) {
                    errorMessage = "An unknown error occurred.";
                }
                Toast.makeText(Payment.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", "Bearer " + SecretKey);
                return header;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("amount", String.valueOf(selectedAmount));
                params.put("currency", "usd");
                params.put("payment_method_types[]", "card");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}