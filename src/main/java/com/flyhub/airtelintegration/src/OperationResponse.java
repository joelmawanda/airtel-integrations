package com.flyhub.airtelintegration.src;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 *
 * @author Mawanda Joel
 */
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@ToString
@Data
@EqualsAndHashCode(callSuper = false)
@JsonPropertyOrder(value = {"timestamp", "operation_result", "operation_description", "data"})
public class OperationResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.APP_TIME_PATTERN)
    private LocalDateTime timestamp = LocalDateTime.now();

    @JsonProperty("operation_result")
    private int operationResult;

    @JsonProperty("operation_description")
    private String operationDescription;

    @JsonProperty("data")
    private Object data;


    public OperationResponse(int operationResult, String operationDescription) {
        this.operationResult = operationResult;
        this.operationDescription = operationDescription;
    }

    public OperationResponse(int operationResult, String operationDescription, Object data) {
        this.operationResult = operationResult;
        this.operationDescription = operationDescription;
        this.data = data;
    }
}
