//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.03.29 at 03:57:34 PM CET 
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
import org.opentrafficsim.xml.bindings.LengthBeginEndAdapter;
import org.opentrafficsim.xml.bindings.types.LengthBeginEnd;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="LINK" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="LANE" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="DIRECTION" use="required" type="{http://www.opentrafficsim.org/ots}GTUDIRECTIONTYPE" /&gt;
 *       &lt;attribute name="POSITION" use="required" type="{http://www.opentrafficsim.org/ots}LENGTHBEGINENDTYPE" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "SINK")
@Generated(value = "com.sun.tools.xjc.Driver", date = "2019-03-29T03:57:34+01:00", comments = "JAXB RI v2.3.0")
public class SINK
    implements Serializable
{

    @Generated(value = "com.sun.tools.xjc.Driver", date = "2019-03-29T03:57:34+01:00", comments = "JAXB RI v2.3.0")
    private final static long serialVersionUID = 10102L;
    @XmlAttribute(name = "LINK", required = true)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2019-03-29T03:57:34+01:00", comments = "JAXB RI v2.3.0")
    protected String link;
    @XmlAttribute(name = "LANE", required = true)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2019-03-29T03:57:34+01:00", comments = "JAXB RI v2.3.0")
    protected String lane;
    @XmlAttribute(name = "DIRECTION", required = true)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2019-03-29T03:57:34+01:00", comments = "JAXB RI v2.3.0")
    protected String direction;
    @XmlAttribute(name = "POSITION", required = true)
    @XmlJavaTypeAdapter(LengthBeginEndAdapter.class)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2019-03-29T03:57:34+01:00", comments = "JAXB RI v2.3.0")
    protected LengthBeginEnd position;

    /**
     * Gets the value of the link property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2019-03-29T03:57:34+01:00", comments = "JAXB RI v2.3.0")
    public String getLINK() {
        return link;
    }

    /**
     * Sets the value of the link property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2019-03-29T03:57:34+01:00", comments = "JAXB RI v2.3.0")
    public void setLINK(String value) {
        this.link = value;
    }

    /**
     * Gets the value of the lane property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2019-03-29T03:57:34+01:00", comments = "JAXB RI v2.3.0")
    public String getLANE() {
        return lane;
    }

    /**
     * Sets the value of the lane property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2019-03-29T03:57:34+01:00", comments = "JAXB RI v2.3.0")
    public void setLANE(String value) {
        this.lane = value;
    }

    /**
     * Gets the value of the direction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2019-03-29T03:57:34+01:00", comments = "JAXB RI v2.3.0")
    public String getDIRECTION() {
        return direction;
    }

    /**
     * Sets the value of the direction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2019-03-29T03:57:34+01:00", comments = "JAXB RI v2.3.0")
    public void setDIRECTION(String value) {
        this.direction = value;
    }

    /**
     * Gets the value of the position property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2019-03-29T03:57:34+01:00", comments = "JAXB RI v2.3.0")
    public LengthBeginEnd getPOSITION() {
        return position;
    }

    /**
     * Sets the value of the position property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2019-03-29T03:57:34+01:00", comments = "JAXB RI v2.3.0")
    public void setPOSITION(LengthBeginEnd value) {
        this.position = value;
    }

}
