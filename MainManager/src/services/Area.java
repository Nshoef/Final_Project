/**
 * Area.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package services;

public class Area  implements java.io.Serializable {
    private java.lang.String name;

    private int numOfVotesPerVoter;

    private boolean ranked;

    private java.lang.String systemName;

    public Area() {
    }

    public Area(
           java.lang.String name,
           int numOfVotesPerVoter,
           boolean ranked,
           java.lang.String systemName) {
           this.name = name;
           this.numOfVotesPerVoter = numOfVotesPerVoter;
           this.ranked = ranked;
           this.systemName = systemName;
    }


    /**
     * Gets the name value for this Area.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this Area.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the numOfVotesPerVoter value for this Area.
     * 
     * @return numOfVotesPerVoter
     */
    public int getNumOfVotesPerVoter() {
        return numOfVotesPerVoter;
    }


    /**
     * Sets the numOfVotesPerVoter value for this Area.
     * 
     * @param numOfVotesPerVoter
     */
    public void setNumOfVotesPerVoter(int numOfVotesPerVoter) {
        this.numOfVotesPerVoter = numOfVotesPerVoter;
    }


    /**
     * Gets the ranked value for this Area.
     * 
     * @return ranked
     */
    public boolean isRanked() {
        return ranked;
    }


    /**
     * Sets the ranked value for this Area.
     * 
     * @param ranked
     */
    public void setRanked(boolean ranked) {
        this.ranked = ranked;
    }


    /**
     * Gets the systemName value for this Area.
     * 
     * @return systemName
     */
    public java.lang.String getSystemName() {
        return systemName;
    }


    /**
     * Sets the systemName value for this Area.
     * 
     * @param systemName
     */
    public void setSystemName(java.lang.String systemName) {
        this.systemName = systemName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Area)) return false;
        Area other = (Area) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            this.numOfVotesPerVoter == other.getNumOfVotesPerVoter() &&
            this.ranked == other.isRanked() &&
            ((this.systemName==null && other.getSystemName()==null) || 
             (this.systemName!=null &&
              this.systemName.equals(other.getSystemName())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        _hashCode += getNumOfVotesPerVoter();
        _hashCode += (isRanked() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getSystemName() != null) {
            _hashCode += getSystemName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Area.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://services", "Area"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://services", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numOfVotesPerVoter");
        elemField.setXmlName(new javax.xml.namespace.QName("http://services", "numOfVotesPerVoter"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ranked");
        elemField.setXmlName(new javax.xml.namespace.QName("http://services", "ranked"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://services", "systemName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
