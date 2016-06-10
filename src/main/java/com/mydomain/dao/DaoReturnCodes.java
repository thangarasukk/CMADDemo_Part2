package com.mydomain.dao;

import javax.ws.rs.core.Response;

public class DaoReturnCodes {

	/**
     * A {@code String} constant representing {@value #RETURN_STATUS_OK} return type ok.
     */
    public final static int RETURN_STATUS_OK = 0;

	/**
     * A {@code String} constant representing {@value #RETURN_STATUS_INVALID_ARGUMENT} return type invalid argument.
     */
    public final static int RETURN_STATUS_INVALID_ARGUMENT = 1;
    
	/**
     * A {@code String} constant representing {@value #RETURN_STATUS_CONTENT_NOT_FOUND} return type content not found.
     */
    public final static int RETURN_STATUS_CONTENT_NOT_FOUND = 2;
    
	/**
     * A {@code String} constant representing {@value #RETURN_STATUS_UNKNOWN_ERROR} return type unknown.
     */
    public final static int RETURN_STATUS_UNKNOWN_ERROR = 255;
    
    
	public Response getHttpResponseFromCode(int responseCode){
		Response http_response = null;
		
		switch(responseCode){
			case DaoReturnCodes.RETURN_STATUS_OK:
				http_response = Response.status(Response.Status.OK).entity("OK").build();
				break;
			case DaoReturnCodes.RETURN_STATUS_INVALID_ARGUMENT:
				http_response = Response.status(Response.Status.EXPECTATION_FAILED).entity("Invalid Argument").build();
				break;
			case DaoReturnCodes.RETURN_STATUS_CONTENT_NOT_FOUND:
				http_response = Response.status(Response.Status.NOT_FOUND).entity("Content not found").build();
				break;
			case DaoReturnCodes.RETURN_STATUS_UNKNOWN_ERROR:
			default:
				http_response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Unknown error").build();
				break;
		}
		return http_response;
	}
	
}
