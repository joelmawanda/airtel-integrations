package com.flyhub.airtelintegration.src.exceptions;

/**
 *
 * @author Mawanda Joel
 */
public class RecordNotFoundException extends GenericServiceException {

    public RecordNotFoundException(int responseCode, String responseMessage) {
        super(responseCode, responseMessage);
    }

}
