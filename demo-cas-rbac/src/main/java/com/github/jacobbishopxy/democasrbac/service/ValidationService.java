package com.github.jacobbishopxy.democasrbac.service;

import java.util.HashMap;
import java.util.Map;

import com.github.jacobbishopxy.democasrbac.security.AuthUtils;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

  @Autowired
  AuthUtils util;

  public String validate(String ticketValue) throws Exception {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("ticketValue", ticketValue);
    params.put("ticketName", "SIAMTGT");
    params.put("timingVerifyTime", "0");
    params.put("autoTimeout", "0");
    String validateResult = util.getResponseFromServer(util.getValidateUrl(), params);

    return validateResult;
  }

  public String getUser(String validateResult) throws Exception {
    JSONObject obj = new JSONObject(validateResult);
    JSONObject user = obj.getJSONObject("user");

    if (user == null) {
      return null;
    }

    return user.getString("uid");
  }

}
