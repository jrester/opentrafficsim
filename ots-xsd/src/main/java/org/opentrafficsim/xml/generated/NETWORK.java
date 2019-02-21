//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.02.21 at 07:52:17 PM CET 
//


package org.opentrafficsim.xml.generated;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice maxOccurs="unbounded"&gt;
 *           &lt;element ref="{http://www.opentrafficsim.org/ots}DEFINITIONS" maxOccurs="unbounded" minOccurs="0"/&gt;
 *           &lt;element ref="{http://www.w3.org/2001/XInclude}include" maxOccurs="unbounded" minOccurs="0"/&gt;
 *           &lt;element ref="{http://www.opentrafficsim.org/ots}NODE" maxOccurs="unbounded" minOccurs="0"/&gt;
 *           &lt;element ref="{http://www.opentrafficsim.org/ots}LINK" maxOccurs="unbounded" minOccurs="0"/&gt;
 *           &lt;element ref="{http://www.opentrafficsim.org/ots}ROUTE" maxOccurs="unbounded" minOccurs="0"/&gt;
 *           &lt;element ref="{http://www.opentrafficsim.org/ots}ROUTEMIX" maxOccurs="unbounded" minOccurs="0"/&gt;
 *           &lt;element ref="{http://www.opentrafficsim.org/ots}SHORTESTROUTE" maxOccurs="unbounded" minOccurs="0"/&gt;
 *           &lt;element ref="{http://www.opentrafficsim.org/ots}SHORTESTROUTEMIX" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}base"/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "definitionsOrIncludeOrNODE"
})
@XmlRootElement(name = "NETWORK")
@Generated(value = "com.sun.tools.xjc.Driver", date = "2019-02-21T07:52:17+01:00", comments = "JAXB RI v2.3.0")
public class NETWORK {

    @XmlElements({
        @XmlElement(name = "DEFINITIONS", type = DEFINITIONS.class),
        @XmlElement(name = "include", namespace = "http://www.w3.org/2001/XInclude", type = IncludeType.class),
        @XmlElement(name = "NODE", type = NODE.class),
        @XmlElement(name = "LINK", type = LINK.class),
        @XmlElement(name = "ROUTE", type = ROUTE.class),
        @XmlElement(name = "ROUTEMIX", type = ROUTEMIX.class),
        @XmlElement(name = "SHORTESTROUTE", type = SHORTESTROUTE.class),
        @XmlElement(name = "SHORTESTROUTEMIX", type = SHORTESTROUTEMIX.class)
    })
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2019-02-21T07:52:17+01:00", comments = "JAXB RI v2.3.0")
    protected List<Object> definitionsOrIncludeOrNODE;
    @XmlAttribute(name = "base", namespace = "http://www.w3.org/XML/1998/namespace")
    @XmlSchemaType(name = "anyURI")
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2019-02-21T07:52:17+01:00", comments = "JAXB RI v2.3.0")
    protected String base;

    /**
     * Gets the value of the definitionsOrIncludeOrNODE property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the definitionsOrIncludeOrNODE property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDEFINITIONSOrIncludeOrNODE().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DEFINITIONS }
     * {@link IncludeType }
     * {@link NODE }
     * {@link LINK }
     * {@link ROUTE }
     * {@link ROUTEMIX }
     * {@link SHORTESTROUTE }
     * {@link SHORTESTROUTEMIX }
     * 
     * 
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2019-02-21T07:52:17+01:00", comments = "JAXB RI v2.3.0")
    public List<Object> getDEFINITIONSOrIncludeOrNODE() {
        if (definitionsOrIncludeOrNODE == null) {
            definitionsOrIncludeOrNODE = new ArrayList<Object>();
        }
        return this.definitionsOrIncludeOrNODE;
    }

    /**
     * Gets the value of the base property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2019-02-21T07:52:17+01:00", comments = "JAXB RI v2.3.0")
    public String getBase() {
        return base;
    }

    /**
     * Sets the value of the base property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2019-02-21T07:52:17+01:00", comments = "JAXB RI v2.3.0")
    public void setBase(String value) {
        this.base = value;
    }

}
