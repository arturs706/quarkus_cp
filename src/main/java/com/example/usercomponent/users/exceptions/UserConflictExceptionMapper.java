package com.example.usercomponent.users.exceptions;

import com.example.usercomponent.users.exceptions.ErrorResponse;
import com.example.usercomponent.users.exceptions.UserConflictException;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UserConflictExceptionMapper implements ExceptionMapper<UserConflictException> {
    @Override
    public Response toResponse(UserConflictException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                Response.Status.CONFLICT.getStatusCode(),
                Response.Status.CONFLICT.getReasonPhrase(),
                exception.getMessage()
        );
        return Response.status(Response.Status.CONFLICT)
                .entity(errorResponse)
                .build();
    }
}
