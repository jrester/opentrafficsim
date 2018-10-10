//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2016.11.03 at 01:02:34 PM CET
//

package org.opentrafficsim.road.network.factory.vissim.xsd;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SHORTESTROUTE" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="NAME" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="WEIGHT" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="NAME" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}base"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "shortestroute" })
@XmlRootElement(name = "SHORTESTROUTEMIX")
public class SHORTESTROUTEMIX
{

    @XmlElement(name = "SHORTESTROUTE", required = true)
    protected List<SHORTESTROUTEMIX.SHORTESTROUTE> shortestroute;

    @XmlAttribute(name = "NAME", required = true)
    protected String name;

    @XmlAttribute(name = "base", namespace = "http://www.w3.org/XML/1998/namespace")
    @XmlSchemaType(name = "anyURI")
    protected String base;

    /**
     * Gets the value of the shortestroute property.
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for the
     * shortestroute property.
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getSHORTESTROUTE().add(newItem);
     * </pre>
     * <p>
     * Objects of the following type(s) are allowed in the list {@link SHORTESTROUTEMIX.SHORTESTROUTE }
     */
    public List<SHORTESTROUTEMIX.SHORTESTROUTE> getSHORTESTROUTE()
    {
        if (shortestroute == null)
        {
            shortestroute = new ArrayList<SHORTESTROUTEMIX.SHORTESTROUTE>();
        }
        return this.shortestroute;
    }

    /**
     * Gets the value of the name property.
     * @return possible object is {@link String }
     */
    public String getNAME()
    {
        return name;
    }

    /**
     * Sets the value of the name property.
     * @param value String; allowed object is {@link String }
     */
    public void setNAME(String value)
    {
        this.name = value;
    }

    /**
     * Gets the value of the base property.
     * @return possible object is {@link String }
     */
    public String getBase()
    {
        return base;
    }

    /**
     * Sets the value of the base property.
     * @param value String; allowed object is {@link String }
     */
    public void setBase(String value)
    {
        this.base = value;
    }

    /**
     * <p>
     * Java class for anonymous complex type.
     * <p>
     * The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="NAME" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="WEIGHT" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class SHORTESTROUTE
    {

        @XmlAttribute(name = "NAME", required = true)
        protected String name;

        @XmlAttribute(name = "WEIGHT", required = true)
        protected double weight;

        /**
         * Gets the value of the name property.
         * @return possible object is {@link String }
         */
        public String getNAME()
        {
            return name;
        }

        /**
         * Sets the value of the name property.
         * @param value String; allowed object is {@link String }
         */
        public void setNAME(String value)
        {
            this.name = value;
        }

        /**
         * Gets the value of the weight property.
         */
        public double getWEIGHT()
        {
            return weight;
        }

        /**
         * Sets the value of the weight property.
         */
        public void setWEIGHT(double value)
        {
            this.weight = value;
        }

    }

}
