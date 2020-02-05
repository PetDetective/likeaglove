package com.likeaglove.core.quarkus.resource;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.likeaglove.core.quarkus.controller.ParkingSlotException;

/**
 * Utility class to convert our specific exception to an Http Response
 * 
 * @author AceVentura
 *
 */
@Provider
public class ParkingExceptionMapper implements ExceptionMapper<ParkingSlotException> {

	@Override
	public Response toResponse(ParkingSlotException exception) {
		return Response.status(exception.getStatus()).entity(exception.getMessage()).type("text/plain").build();
	}

}
