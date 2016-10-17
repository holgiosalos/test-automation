package com.bdd.appointments.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author Holmes Salazar
 */
public class SimpleRestClient
{
    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl;

    public SimpleRestClient(final String baseUrl)
    {
        this.baseUrl = baseUrl;
    }

    public String registerPatient(Map<String, String> patientMap)
    {
        return restTemplate.postForObject(baseUrl + "/addPatient", getFormattedMap(patientMap), String.class);
    }

    public String registerDoctor(Map<String, String> doctorMap)
    {
        return restTemplate.postForObject(baseUrl + "/addDoctor", getFormattedMap(doctorMap), String.class);
    }

    private MultiValueMap<String, String> getFormattedMap(Map<String, String> patientMap)
    {
        MultiValueMap<String, String> formattedMap = new LinkedMultiValueMap<>();

        try
        {
            for (Map.Entry<String, String> entry : patientMap.entrySet())
            {
                formattedMap.set(entry.getKey(), URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
        }
        catch (UnsupportedEncodingException exception)
        {
            throw new IllegalStateException("Error encoding input values", exception);
        }

        return formattedMap;
    }
}
