/**
 * AreaInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package services;

public class AreaInfo  implements java.io.Serializable {
    private java.lang.String area;

    private java.lang.String[] canNames;

    private java.lang.String electionName;

    private int numOfVotePerVoter;

    private boolean ranked;

    public AreaInfo() {
    }

    public AreaInfo(
           java.lang.String area,
           java.lang.String[] canNames,
           java.lang.String electionName,
           int numOfVotePerVoter,
           boolean ranked) {
           this.area = area;
           this.canNames = canNames;
           this.electionName = electionName;
           this.numOfVotePerVoter = numOfVotePerVoter;
           this.ranked = ranked;
    }


    /**
     * Gets the area value for this AreaInfo.
     * 
     * @return area
     */
    public java.lang.String getArea() {
        return area;
    }


    /**
     * Sets the area value for this AreaInfo.
     * 
     * @param area
     */
    public void setArea(java.lang.String area) {
        this.area = area;
    }


    /**
     * Gets the canNames value for this AreaInfo.
     * 
     * @return canNames
     */
    public java.lang.String[] getCanNames() {
        return canNames;
    }


    /**
     * Sets the canNames value for this AreaInfo.
     * 
     * @param canNames
     */
    public void setCanNames(java.lang.String[] canNames) {
        this.canNames = canNames;
    }


    /**
     * Gets the electionName value for this AreaInfo.
     * 
     * @return electionName
     */
    public java.lang.String getElectionName() {
        return electionName;
    }


    /**
     * Sets the electionName value for this AreaInfo.
     * 
     * @param electionName
     */
    public void setElectionName(java.lang.String electionName) {
        this.electionName = electionName;
    }


    /**
     * Gets the numOfVotePerVoter value for this AreaInfo.
     * 
     * @return numOfVotePerVoter
     */
    public int getNumOfVotePerVoter() {
        return numOfVotePerVoter;
    }


    /**
     * Sets the numOfVotePerVoter value for this AreaInfo.
     * 
     * @param numOfVotePerVoter
     */
    public void setNumOfVotePerVoter(int numOfVotePerVoter) {
        this.numOfVotePerVoter = numOfVotePerVoter;
    }


    /**
     * Gets the ranked value for this AreaInfo.
     * 
     * @return ranked
     */
    public boolean isRanked() {
        return ranked;
    }


    /**
     * Sets the ranked value for this AreaInfo.
     * 
     * @param ranked
     */
    public void setRanked(boolean ranked) {
        this.ranked = ranked;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AreaInfo)) return false;
        AreaInfo other = (AreaInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.area==null && other.getArea()==null) || 
             (this.area!=null &&
              this.area.equals(other.getArea()))) &&
            ((this.canNames==null && other.getCanNames()==null) || 
             (this.canNames!=null &&
              java.util.Arrays.equals(this.canNames, other.getCanNames()))) &&
            ((this.electionName==null && other.getElectionName()==null) || 
             (this.electionName!=null &&
              this.electionName.equals(other.getElectionName()))) &&
            this.numOfVotePerVoter == other.getNumOfVotePerVoter() &&
            this.ranked == other.isRanked();
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
        if (getArea() != null) {
            _hashCode += getArea().hashCode();
        }
        if (getCanNames() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCanNames());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCanNames(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getElectionName() != null) {
            _hashCode += getElectionName().hashCode();
        }
        _hashCode += getNumOfVotePerVoter();
        _hashCode += (isRanked() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AreaInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://services", "AreaInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("area");
        elemField.setXmlName(new javax.xml.namespace.QName("http://services", "area"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("canNames");
        elemField.setXmlName(new javax.xml.namespace.QName("http://services", "canNames"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://services", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("electionName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://services", "electionName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numOfVotePerVoter");
        elemField.setXmlName(new javax.xml.namespace.QName("http://services", "numOfVotePerVoter"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ranked");
        elemField.setXmlName(new javax.xml.namespace.QName("http://services", "ranked"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
