package com.gaurav.myapps.villekart.serviceImpl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaurav.myapps.villekart.Exception.InvalidPinCodeException;
import com.gaurav.myapps.villekart.model.PincodeDetails;
import com.gaurav.myapps.villekart.service.UserService;
import com.gaurav.myapps.villekart.utils.StringConstants;

@Service
public class UserServiceImpl implements UserService{
	
	@Value("${pincode.validation.url}")
	String pinCodeApi;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@Autowired
	private RestTemplate restTemplate;

	
	private String getPincodeDetails(String pincode) {
        String url = pinCodeApi + pincode;
        // Make a GET request to the API
        return restTemplate.getForObject(url, String.class);
    }

	@Override
	public String validatePincode(String pincode) {
		String response= null;
        try {
        	String apiResponse = getPincodeDetails(pincode);
			if(null != apiResponse) {
				PincodeDetails postOffice = getFirstPostOffice(apiResponse);
				response = postOffice.toString();
			}
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidPinCodeException e) {
			response = StringConstants.INVALID_PINCODE.getValue();
		}
        
		return response;
	}
	
	

    private PincodeDetails getFirstPostOffice(String jsonResponse) throws IOException, InvalidPinCodeException {
        JsonNode rootNode = objectMapper.readTree(jsonResponse);
        if (rootNode.isArray() && rootNode.size() > 0) {
            // Get the first object in the array
            JsonNode firstElement = rootNode.get(0);
            String status = firstElement.path("Status").asText();
            if(status.equalsIgnoreCase(StringConstants.SUCCESS.getValue())) {
            	JsonNode postOfficeArray = firstElement.path("PostOffice");

                if (postOfficeArray.isArray() && postOfficeArray.size() > 0) {
                    // Get the first PostOffice object
                    JsonNode firstPostOfficeNode = postOfficeArray.get(0);
                    return objectMapper.treeToValue(firstPostOfficeNode, PincodeDetails.class);
                }
            }
            else {
            	throw new InvalidPinCodeException(StringConstants.INVALID_PINCODE.getValue());
            }
        }
        return null;
    }

}
