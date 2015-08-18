/**
 * GetAreaInfoResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package services;

public class GetAreaInfoResponse  implements java.io.Serializable {
    private services.AreaInfo getAreaInfoReturn;

    public GetAreaInfoResponse() {
    }

    public GetAreaInfoResponse(
           services.AreaInfo getAreaInfoReturn) {
           this.getAreaInfoReturn = getAreaInfoReturn;
    }


    /**
     * Gets the getAreaInfoReturn value for this GetAreaInfoResponse.
     * 
     * @return getAreaInfoReturn
     */
    public services.AreaInfo getGetAreaInfoReturn() {
        return getAreaInfoReturn;
    }


    /**
     * Sets the getAreaInfoReturn value for this GetAreaInfoResponse.
     * 
     * @param getAreaInfoReturn
     */
    public void setGetAreaInfoReturn(services.AreaInfo getAreaInfoReturn) {
        this.getAreaInfoReturn = getAreaInfoReturn;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetAreaInfoResponse)) return false;
        GetAreaInfoResponse other = (GetAreaInfoResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getAreaInfoReturn==null && other.getGetAreaInfoReturn()==null) || 
             (this.getAreaInfoReturn!=null &&
              this.getAreaInfoReturn.equals(other.getGetAreaInfoReturn())));
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
        if (getGetAreaInfoReturn() != null) {
            _hashCode += getGetAreaInfoReturn().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetAreaInfoResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://services", ">getAreaInfoResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getAreaInfoReturn");
        elemField.setXmlName(new javax.xml.namespace.QName("http://services", "getAreaInfoReturn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://services", "AreaInfo"));
        elemField.setNillable(false);
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
