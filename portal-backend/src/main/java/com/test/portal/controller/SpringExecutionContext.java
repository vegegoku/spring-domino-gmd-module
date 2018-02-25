package com.test.portal.controller;

import com.progressoft.brix.domino.api.server.context.DefaultExecutionContext;
import com.progressoft.brix.domino.api.server.request.RequestContext;
import com.progressoft.brix.domino.api.shared.request.RequestBean;
import com.progressoft.brix.domino.api.shared.request.ResponseBean;

public class SpringExecutionContext<R extends RequestBean, S extends ResponseBean> extends DefaultExecutionContext<R,S>{

  private final SpringResponseContext<S> responseContext;

  public SpringExecutionContext(RequestContext<R> requestContext,
      SpringResponseContext<S> responseContext) {
    super(requestContext, responseContext);
    this.responseContext=responseContext;
  }

  public SpringResponseContext<S> getResponseContext() {
    return responseContext;
  }
}
