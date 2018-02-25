package com.test.portal.controller;

import com.progressoft.brix.domino.api.server.request.DefaultMultiMap;
import com.progressoft.brix.domino.api.server.request.DefaultRequestContext;
import com.progressoft.brix.domino.api.server.request.RequestContext;
import com.progressoft.brix.domino.api.shared.request.RequestBean;
import com.progressoft.brix.domino.api.shared.request.ResponseBean;
import com.progressoft.brix.domino.api.shared.request.VoidRequest;
import com.progressoft.brix.domino.sample.items.server.handlers.AddItemHandler;
import com.progressoft.brix.domino.sample.items.server.handlers.ClearAllHandler;
import com.progressoft.brix.domino.sample.items.server.handlers.ClearDoneHandler;
import com.progressoft.brix.domino.sample.items.server.handlers.LoadItemsHandler;
import com.progressoft.brix.domino.sample.items.server.handlers.ToggleItemHandler;
import com.progressoft.brix.domino.sample.items.shared.request.AddItemRequest;
import com.progressoft.brix.domino.sample.items.shared.request.ToggleItemRequest;
import com.progressoft.brix.domino.sample.items.shared.response.AddItemResponse;
import com.progressoft.brix.domino.sample.items.shared.response.LoadItemsResponse;
import com.progressoft.brix.domino.sample.items.shared.response.RemoveResponse;
import com.progressoft.brix.domino.sample.items.shared.response.ToggleItemResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service/todo/")
@Log4j2
public class TodoListController {


  private <R extends RequestBean, S extends ResponseBean> SpringExecutionContext<R,S> createExecutionContext(R request,
      String requestUri) {
    RequestContext<R> requestContext = DefaultRequestContext.forRequest(request)
        .requestPath(requestUri)
        .headers(new DefaultMultiMap<>())
        .parameters(new DefaultMultiMap<>())
        .build();

    return new SpringExecutionContext<>(requestContext, new SpringResponseContext<S>());
  }

  @RequestMapping(value = "items", method = RequestMethod.GET)
  public ResponseEntity findAll(HttpServletRequest request) {
    SpringExecutionContext<VoidRequest, LoadItemsResponse> executionContext = createExecutionContext(
        RequestBean.VOID_REQUEST, request.getRequestURI());
    new LoadItemsHandler().handleRequest(executionContext);
    return ResponseEntity.ok(executionContext.getResponseContext().getResponseBean());
  }

  @RequestMapping(value = "add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity addItem(@RequestBody AddItemRequest request, HttpServletRequest httpServletRequest) {
    SpringExecutionContext<AddItemRequest, AddItemResponse> executionContext = createExecutionContext(request,
        httpServletRequest.getRequestURI());
    new AddItemHandler().handleRequest(executionContext);
    return ResponseEntity.ok(executionContext.getResponseContext().getResponseBean());
  }

  @RequestMapping(value = "clear", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity clear(HttpServletRequest httpServletRequest) {
    SpringExecutionContext<VoidRequest, RemoveResponse> executionContext = createExecutionContext(
        RequestBean.VOID_REQUEST, httpServletRequest.getRequestURI());
    new ClearAllHandler().handleRequest(
        executionContext);
    return ResponseEntity.ok(executionContext.getResponseContext().getResponseBean());
  }

  @RequestMapping(value = "remove-done", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity removeDone(HttpServletRequest httpServletRequest) {
    SpringExecutionContext<VoidRequest, RemoveResponse> executionContext = createExecutionContext
        (RequestBean
            .VOID_REQUEST,
        httpServletRequest.getRequestURI());
    new ClearDoneHandler().handleRequest(
        executionContext);
    return ResponseEntity.ok(executionContext.getResponseContext().getResponseBean());
  }

  @RequestMapping(value = "toggle", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity toggleItem(@RequestBody ToggleItemRequest request, HttpServletRequest httpServletRequest) {
    SpringExecutionContext<ToggleItemRequest, ToggleItemResponse> executionContext = createExecutionContext(request,
        httpServletRequest.getRequestURI());
    new ToggleItemHandler().handleRequest(executionContext);
    return ResponseEntity.ok(executionContext.getResponseContext().getResponseBean());
  }
}
