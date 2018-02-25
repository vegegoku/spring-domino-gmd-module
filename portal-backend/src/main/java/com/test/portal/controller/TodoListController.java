package com.test.portal.controller;

import com.progressoft.brix.domino.api.server.context.DefaultExecutionContext;
import com.progressoft.brix.domino.api.server.context.ExecutionContext;
import com.progressoft.brix.domino.api.server.request.DefaultMultiMap;
import com.progressoft.brix.domino.api.server.request.DefaultRequestContext;
import com.progressoft.brix.domino.api.server.request.RequestContext;
import com.progressoft.brix.domino.api.shared.request.RequestBean;
import com.progressoft.brix.domino.api.shared.request.ResponseBean;
import com.progressoft.brix.domino.sample.items.server.handlers.AddItemHandler;
import com.progressoft.brix.domino.sample.items.server.handlers.ClearAllHandler;
import com.progressoft.brix.domino.sample.items.server.handlers.ClearDoneHandler;
import com.progressoft.brix.domino.sample.items.server.handlers.LoadItemsHandler;
import com.progressoft.brix.domino.sample.items.server.handlers.ToggleItemHandler;
import com.progressoft.brix.domino.sample.items.shared.request.AddItemRequest;
import com.progressoft.brix.domino.sample.items.shared.request.ToggleItemRequest;
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

  private SpringResponseContext responseContext;

  private <R extends RequestBean, S extends ResponseBean> ExecutionContext<R, S> createExecutionContext(R request,
      String requestUri) {
    RequestContext requestContext = DefaultRequestContext.forRequest(request)
        .requestPath(requestUri)
        .headers(new DefaultMultiMap<>())
        .parameters(new DefaultMultiMap<>())
        .build();
    responseContext = new SpringResponseContext();
    return new DefaultExecutionContext(requestContext, responseContext);
  }

  @RequestMapping(value = "items", method = RequestMethod.GET)
  public ResponseEntity findAll(HttpServletRequest request) {
    new LoadItemsHandler().handleRequest(createExecutionContext(RequestBean.VOID_REQUEST, request.getRequestURI()));
    return ResponseEntity.ok(responseContext.getResponseBean());
  }

  @RequestMapping(value = "add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity addItem(@RequestBody AddItemRequest request, HttpServletRequest httpServletRequest) {
    new AddItemHandler().handleRequest(createExecutionContext(request, httpServletRequest.getRequestURI()));
    return ResponseEntity.ok(responseContext.getResponseBean());
  }

  @RequestMapping(value = "clear", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity clear(HttpServletRequest httpServletRequest) {
    new ClearAllHandler().handleRequest(
        createExecutionContext(RequestBean.VOID_REQUEST, httpServletRequest.getRequestURI()));
    return ResponseEntity.ok(responseContext.getResponseBean());
  }

  @RequestMapping(value = "remove-done", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity removeDone(HttpServletRequest httpServletRequest) {
    new ClearDoneHandler().handleRequest(
        createExecutionContext(RequestBean.VOID_REQUEST, httpServletRequest.getRequestURI()));
    return ResponseEntity.ok(responseContext.getResponseBean());
  }

  @RequestMapping(value = "toggle", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity toggleItem(@RequestBody ToggleItemRequest request, HttpServletRequest httpServletRequest) {
    new ToggleItemHandler().handleRequest(createExecutionContext(request, httpServletRequest.getRequestURI()));
    return ResponseEntity.ok(responseContext.getResponseBean());
  }
}
