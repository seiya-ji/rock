package com.onezhier.rock.framework.component;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.onezhier.rock.client.Response;
import com.onezhier.rock.exception.BizException;
import com.onezhier.rock.exception.ErrorCode;
import com.onezhier.rock.exception.SysException;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局处理controller中的异常 -
 * <p>
 * 所有的ValidationException的标识为200, 表示客户端已经联通，但是服务端有异常。
 * <p>
 * 所有未捕获的RuntimeException的标识为500, 表示服务端有异常。
 *
 * @author shawn_tao
 */
@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

  
//  @ExceptionHandler(value = {NotFoundException.class})
//  @ResponseBody
//  @ResponseStatus(HttpStatus.NOT_FOUND)
//  public Object notFoundExceptionHandler(HttpServletRequest req, Exception e)
//      throws Exception {
//    return evaluateExceptionObject(req, e,
//        "---controllerExceptionHandler ---Host {} invokes url {} ERROR: {}");
//  }

  @ExceptionHandler(value = {ServiceException.class})
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Object controllerExceptionHandler(HttpServletRequest req, Exception e)
      throws Exception {
	  log.error(e.getMessage(),e);
    return evaluateExceptionObject(req, e,
        "---controllerExceptionHandler ---Host {} invokes url {} ERROR: {}");
  }

  

  @ExceptionHandler(value = {org.springframework.orm.ObjectOptimisticLockingFailureException.class})
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Object controllerConcurrentModificationExceptionHandler(HttpServletRequest req,
      Exception e)
      throws Exception {
	  log.error(e.getMessage(),e);
	  return   Response.buildFailure("-1", "数据已过期，请刷新后重新提交");

  }


  private Response evaluateExceptionObject(HttpServletRequest req, Exception e, String format) {
    log.error(
    		format,
        req.getRemoteHost(), req.getRequestURL(), e.getMessage());
    return   Response.buildFailure("-1", e.getMessage());

  }

  @ExceptionHandler(value = {BizException.class})
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public Object bizException(HttpServletRequest request, Exception e) {
	  log.error(e.getMessage(),e);
	  return Response.buildFailure(ErrorCode.DEFAULT_BIZ_ERR_CODE, e.getMessage());

  }
  
  @ExceptionHandler(value = {SysException.class})
  @ResponseBody
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Object sysException(HttpServletRequest request, Exception e) {
	  log.error(e.getMessage(),e);
    return evaluateExceptionObject(request, e,
        "---Serivice Handler ---Host {} invokes url {} ERROR: {}");
  }



  @ExceptionHandler(value = RuntimeException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Object runtimeExceptionHandler(HttpServletRequest req, Exception e)
      throws Exception {
	  log.error(e.getMessage(),e);
    return evaluateExceptionObject(req, e,
        "---Serivice Handler ---Host {} invokes url {} ERROR: {}");
  }

  @ResponseBody
  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public Response errorHandler2(MethodArgumentNotValidException e) {
	  log.error(e.getMessage(),e);
    BindingResult bindingResult = e.getBindingResult();
    StringBuffer emsg = new StringBuffer();
    for (int i = 0; i < bindingResult.getFieldErrors().size(); i++) {
      FieldError fieldError = bindingResult.getFieldErrors().get(i);
      emsg.append(fieldError.getField());
      emsg.append(":");
      emsg.append(fieldError.getDefaultMessage());
      emsg.append(",");
    }
    emsg.deleteCharAt(emsg.length()-1);
  return   Response.buildFailure("-1", emsg.toString());

  }
}
