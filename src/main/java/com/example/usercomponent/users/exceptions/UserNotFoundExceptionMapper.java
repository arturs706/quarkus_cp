package com.example.usercomponent.users.exceptions;

import com.example.usercomponent.users.exceptions.ErrorResponse;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UserNotFoundExceptionMapper implements ExceptionMapper<UserNotFoundException> {
    @Override
    public Response toResponse(UserNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                Response.Status.NOT_FOUND.getStatusCode(),
                Response.Status.NOT_FOUND.getReasonPhrase(),
                exception.getMessage()
        );
        return Response.status(Response.Status.NOT_FOUND)
                .entity(errorResponse)
                .build();
    }
}
