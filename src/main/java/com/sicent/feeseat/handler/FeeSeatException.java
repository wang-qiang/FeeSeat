/**
 * 
 */
package com.sicent.feeseat.handler;

/**
 * FeeSeat运行时异常
 * 
 * @author wangqiang
 * 
 */
public class FeeSeatException extends RuntimeException {

    private static final long serialVersionUID = 1225313360824382505L;

    public FeeSeatException() {
        super();
    }

    public FeeSeatException(String message) {
        super(message);
    }
}
