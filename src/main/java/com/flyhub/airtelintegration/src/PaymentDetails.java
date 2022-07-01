package com.flyhub.airtelintegration.src;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "payments")
@Table(name = "payments")
@XmlRootElement(name = "COMMAND")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(value = {"id", "type", "customerMsisdn", "merchantMsisdn", "customerName", "pin", "username", "password", "reference1"}, allowSetters = true)
public class PaymentDetails {

    @Id
    @GeneratedValue
    private long id;

    @Transient
    @XmlElement(name = "TYPE")
	private String type;

    @Transient
    @XmlElement(name = "CUSTOMERMSISDN")
    private String customerMsisdn;

    @Transient
    @XmlElement(name = "MERCHANTMSISDN")
    private String merchantMsisdn;

    @Transient
    @XmlElement(name = "CUSTOMERNAME")
    private String customerName;

    @XmlElement(name = "AMOUNT")
    private String amount;

    @Transient
    @XmlElement(name = "PIN")
    private String pin;

    @XmlElement(name = "REFERENCE")
    private String reference;

    @Transient
    @XmlElement(name = "USERNAME")
    private String username;

    @Transient
    @XmlElement(name = "PASSWORD")
    private String password;

    @Transient
    @XmlElement(name = "REFERENCE1")
    private String reference1;

}
