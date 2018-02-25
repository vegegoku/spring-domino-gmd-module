package com.test.portal.controller;

import com.progressoft.brix.domino.api.server.response.ResponseContext;
import com.progressoft.brix.domino.api.shared.request.ResponseBean;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class SpringResponseContext implements ResponseContext<ResponseBean> {

  private Map<String, String> headers = new HashMap<>();
  private int statusCode;
  private ResponseBean responseBean;

  @Override
  public ResponseContext<ResponseBean> putHeader(String name, String value) {
    headers.put(name, value);
    return this;
  }

  @Override
  public ResponseContext<ResponseBean> putHeader(String name, Iterable<String> values) {
    values.forEach(value -> putHeader(name, value));
    return this;
  }

  @Override
  public ResponseContext<ResponseBean> statusCode(int statusCode) {
    this.statusCode = statusCode;
    return this;
  }

  @Override
  public void end() {
  }

  @Override
  public void end(ResponseBean body) {
    this.responseBean = body;
  }

  @Override
  public void end(String body) {
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public ResponseBean getResponseBean() {
    return responseBean;
  }
}
