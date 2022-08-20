//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.08.20 at 08:27:55 PM CEST 
//


package org.opentrafficsim.xml.generated;

import java.io.Serializable;
import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.opentrafficsim.xml.bindings.FractionAdapter;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="ID" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="NODESTART" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="NODEEND" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="DEMANDWEIGHT" type="{http://www.opentrafficsim.org/ots}FRACTIONTYPE" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "CONNECTOR")
@Generated(value = "com.sun.tools.xjc.Driver", date = "2022-08-20T08:27:55+02:00", comments = "JAXB RI v2.3.0")
public class CONNECTOR implements Serializable
{

    @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-08-20T08:27:55+02:00", comments = "JAXB RI v2.3.0")
    private final static long serialVersionUID = 10102L;
    @XmlAttribute(name = "ID", required = true)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-08-20T08:27:55+02:00", comments = "JAXB RI v2.3.0")
    protected String id;
    @XmlAttribute(name = "NODESTART", required = true)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-08-20T08:27:55+02:00", comments = "JAXB RI v2.3.0")
    protected String nodestart;
    @XmlAttribute(name = "NODEEND", required = true)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-08-20T08:27:55+02:00", comments = "JAXB RI v2.3.0")
    protected String nodeend;
    @XmlAttribute(name = "DEMANDWEIGHT")
    @XmlJavaTypeAdapter(FractionAdapter.class)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-08-20T08:27:55+02:00", comments = "JAXB RI v2.3.0")
    protected Double demandweight;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-08-20T08:27:55+02:00", comments = "JAXB RI v2.3.0")
    public String getID() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-08-20T08:27:55+02:00", comments = "JAXB RI v2.3.0")
    public void setID(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the nodestart property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-08-20T08:27:55+02:00", comments = "JAXB RI v2.3.0")
    public String getNODESTART() {
        return nodestart;
    }

    /**
     * Sets the value of the nodestart property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-08-20T08:27:55+02:00", comments = "JAXB RI v2.3.0")
    public void setNODESTART(String value) {
        this.nodestart = value;
    }

    /**
     * Gets the value of the nodeend property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-08-20T08:27:55+02:00", comments = "JAXB RI v2.3.0")
    public String getNODEEND() {
        return nodeend;
    }

    /**
     * Sets the value of the nodeend property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-08-20T08:27:55+02:00", comments = "JAXB RI v2.3.0")
    public void setNODEEND(String value) {
        this.nodeend = value;
    }

    /**
     * Gets the value of the demandweight property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-08-20T08:27:55+02:00", comments = "JAXB RI v2.3.0")
    public Double getDEMANDWEIGHT() {
        return demandweight;
    }

    /**
     * Sets the value of the demandweight property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2022-08-20T08:27:55+02:00", comments = "JAXB RI v2.3.0")
    public void setDEMANDWEIGHT(Double value) {
        this.demandweight = value;
    }

}
