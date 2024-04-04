package com.example.usercomponent.users.controller;

import com.example.jwtservice.JWTService;
import com.example.usercomponent.users.exceptions.UserConflictException;
import com.example.usercomponent.users.exceptions.UserNotFoundException;
import com.example.usercomponent.users.models.Artist;
import com.example.usercomponent.users.models.LoginCredentials;
import com.example.usercomponent.users.models.User;
import com.example.usercomponent.users.service.UserService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Path("/api/v1/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    @Inject
    UserService userService;

    @GET
    @Path("/")
    @RolesAllowed("Superuser")
    public Response getAllUsers() {
        try {
            List<User> users = userService.findAll();
            return Response.ok(users).build();
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/artists")
    @RolesAllowed("Superuser")
    public Response getAllArtists() {
        try {
            List<Artist> artists = userService.findAllArtists();
            return Response.ok(artists).build();
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @POST
    @Path("/")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveUser(User user) {
        try {
            userService.registerUser(user);
            String message = "User successfully registered";
            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"" + message + "\"}")
                    .build();
        } catch (UserConflictException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(e.getErrorMessage())
                    .build();
        }
    }

    @POST
    @Path("/login")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginUser(LoginCredentials credentials, @Context UriInfo uriInfo) {
        String email = credentials.getEmail();
        String passwd = credentials.getPassword();
        Optional<User> user = userService.loginUser(email, passwd);
        if (user.isPresent()) {
            HashSet<String> groups = new HashSet<>();
            switch (user.get().getUserType()) {
                case "Superuser":
                    groups.add("Superuser");
                    groups.add("Admin");
                    groups.add("Listener");
                    groups.add("Artist");
                    break;
                case "Admin":
                    groups.add("Admin");
                    groups.add("Listener");
                    groups.add("Artist");
                    break;
                case "Artist":
                    groups.add("Artist");
                    groups.add("Listener");
                    break;
                case "Listener":
                    groups.add("Listener");
                    break;
            }
            String accessToken = JWTService.generateAccessToken(user.get().getUserId(), groups);
            String refreshToken = JWTService.generateRefreshToken(user.get().getUserId(), groups);
            NewCookie refreshTokenCookie = new NewCookie("refreshToken", refreshToken, "/", uriInfo.getBaseUri().getHost(), "", NewCookie.DEFAULT_MAX_AGE, false);
            return Response.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken).cookie(refreshTokenCookie).entity(user.get()).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Invalid email or password")
                    .build();
        }

    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response getUser(@PathParam("id") UUID id) {
        try {
            User user = userService.findById(id);
            return Response.ok(user).build();
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }


//    @POST
//    @Path("/refresh")
//    @PermitAll
//    public Response refreshToken(@CookieParam("refreshToken") String refreshToken, @Context UriInfo uriInfo) {
//        if (refreshToken == null) {
//            return Response.status(Response.Status.UNAUTHORIZED)
//                    .entity("No refresh token found")
//                    .build();
//        }
//        UUID userId = JWTService.verifyRefreshToken(refreshToken);
//        if (userId != null) {
//            Optional<User> user = Optional.ofNullable(userService.findById(userId));
//            if (user.isPresent()) {
//                HashSet<String> groups = new HashSet<>();
//                switch (user.get().getUserType()) {
//                    case "Superuser":
//                        groups.add("Superuser");
//                        groups.add("Admin");
//                        groups.add("Listener");
//                        groups.add("Artist");
//                        break;
//                    case "Admin":
//                        groups.add("Admin");
//                        groups.add("Listener");
//                        groups.add("Artist");
//                        break;
//                    case "Artist":
//                        groups.add("Artist");
//                        groups.add("Listener");
//                        break;
//                    case "Listener":
//                        groups.add("Listener");
//                        break;
//                }
//                String accessToken = JWTService.generateAccessToken(user.get().getUserId(), groups);
//                String newRefreshToken = JWTService.generateRefreshToken(user.get().getUserId(), groups);
//                NewCookie newRefreshTokenCookie = new NewCookie("refreshToken", newRefreshToken, "/", uriInfo.getBaseUri().getHost(), "", NewCookie.DEFAULT_MAX_AGE, false);
//                return Response.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken).cookie(newRefreshTokenCookie).entity(user.get()).build();
//            }
//        }
//        return Response.status(Response.Status.UNAUTHORIZED)
//                .entity("Invalid refresh token")
//                .build();
//    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response deleteUser(@PathParam("id") UUID id) {
        try {
            userService.deleteUser(id);
            String message = "User successfully deleted";
            return Response.status(Response.Status.OK)
                    .entity("{\"message\": \"" + message + "\"}")
                    .build();
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PUT
    @PermitAll
    @Path("/{userId}/updateuserandartist")
    public void updateUserAndArtist(@PathParam("userId") UUID userId) {
        userService.updateUserAndArtist(userId, true);
    }

    @GET
    @PermitAll
    @Path("/artists/{userId}")
    public Response getArtist(@PathParam("userId") UUID userId) {
        try {
            UUID artist = userService.getArtistId(userId);
            return Response.ok(artist).build();
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }
}
