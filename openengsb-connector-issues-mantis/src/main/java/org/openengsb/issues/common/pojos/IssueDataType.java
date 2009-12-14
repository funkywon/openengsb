/**

   Copyright 2009 OpenEngSB Division, Vienna University of Technology

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE\-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
   
*/
package org.openengsb.issues.common.pojos;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import biz.futureware.mantisconnect.AccountData;
import biz.futureware.mantisconnect.ObjectRef;


/**
 * <p>Java class for IssueDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IssueDataType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="last_updated" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="project" type="{http://futureware.biz/mantisconnect}ObjectRef"/>
 *         <xsd:element name="category" type="xsd:string"/>
 *         &lt;element name="priority" type="{http://futureware.biz/mantisconnect}ObjectRef" minOccurs="0"/>
 *         &lt;element name="severity" type="{http://futureware.biz/mantisconnect}ObjectRef" minOccurs="0"/>
 *         &lt;element name="status" type="{http://futureware.biz/mantisconnect}ObjectRef" minOccurs="0"/>
 *         &lt;element name="reporter" type="{http://futureware.biz/mantisconnect}AccountData" minOccurs="0"/>
 *         &lt;element name="summary" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="os" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="date_submitted" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="handler" type="{http://futureware.biz/mantisconnect}AccountData" minOccurs="0"/>
 *         &lt;element name="eta" type="{http://futureware.biz/mantisconnect}ObjectRef" minOccurs="0"/>
 *         &lt;element name="resolution" type="{http://futureware.biz/mantisconnect}ObjectRef" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attachments" type="{http://common.issues.openengsb.org/pojos}AttachmentDataArray" minOccurs="0"/>
 *         &lt;element name="notes" type="{http://common.issues.openengsb.org/pojos}IssueNoteDataArray" minOccurs="0"/>
 *         &lt;element name="custom_fields" type="{http://common.issues.openengsb.org/pojos}CustomFieldValueForIssueDataArray" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IssueDataType", propOrder = {
    "id",
    "lastUpdated",
    "project",
    "category",
    "priority",
    "severity",
    "status",
    "reporter",
    "summary",
    "version",
    "os",
    "dateSubmitted",
    "handler",
    "eta",
    "resolution",
    "description",
    "attachments",
    "notes",
    "customFields"
})
public class IssueDataType {

    protected BigInteger id;
    @XmlElement(name = "last_updated")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastUpdated;
    protected ObjectRef project;
    protected String category;
	protected ObjectRef priority;
    protected ObjectRef severity;
    protected ObjectRef status;
    protected AccountData reporter;
    protected String summary;
    protected String version;
    protected String os;
    @XmlElement(name = "date_submitted")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateSubmitted;
    protected AccountData handler;
    protected ObjectRef eta;
    protected ObjectRef resolution;
    protected String description;
    protected AttachmentDataArray attachments;
    protected IssueNoteDataArray notes;
    @XmlElement(name = "custom_fields")
    protected CustomFieldValueForIssueDataArray customFields;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setId(BigInteger value) {
        this.id = value;
    }

    /**
     * Gets the value of the lastUpdated property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastUpdated() {
        return lastUpdated;
    }

    /**
     * Sets the value of the lastUpdated property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastUpdated(XMLGregorianCalendar value) {
        this.lastUpdated = value;
    }

    /**
     * Gets the value of the project property.
     * 
     * @return
     *     possible object is
     *     {@link ObjectRef }
     *     
     */
    public ObjectRef getProject() {
        return project;
    }

    /**
     * Sets the value of the project property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectRef }
     *     
     */
    public void setProject(ObjectRef value) {
        this.project = value;
    }
    
    public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

    /**
     * Gets the value of the priority property.
     * 
     * @return
     *     possible object is
     *     {@link ObjectRef }
     *     
     */
    public ObjectRef getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectRef }
     *     
     */
    public void setPriority(ObjectRef value) {
        this.priority = value;
    }

    /**
     * Gets the value of the severity property.
     * 
     * @return
     *     possible object is
     *     {@link ObjectRef }
     *     
     */
    public ObjectRef getSeverity() {
        return severity;
    }

    /**
     * Sets the value of the severity property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectRef }
     *     
     */
    public void setSeverity(ObjectRef value) {
        this.severity = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link ObjectRef }
     *     
     */
    public ObjectRef getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectRef }
     *     
     */
    public void setStatus(ObjectRef value) {
        this.status = value;
    }

    /**
     * Gets the value of the reporter property.
     * 
     * @return
     *     possible object is
     *     {@link AccountData }
     *     
     */
    public AccountData getReporter() {
        return reporter;
    }

    /**
     * Sets the value of the reporter property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountData }
     *     
     */
    public void setReporter(AccountData value) {
        this.reporter = value;
    }

    /**
     * Gets the value of the summary property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSummary() {
        return summary;
    }

    /**
     * Sets the value of the summary property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSummary(String value) {
        this.summary = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the os property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOs() {
        return os;
    }

    /**
     * Sets the value of the os property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOs(String value) {
        this.os = value;
    }

    /**
     * Gets the value of the dateSubmitted property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateSubmitted() {
        return dateSubmitted;
    }

    /**
     * Sets the value of the dateSubmitted property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateSubmitted(XMLGregorianCalendar value) {
        this.dateSubmitted = value;
    }

    /**
     * Gets the value of the handler property.
     * 
     * @return
     *     possible object is
     *     {@link AccountData }
     *     
     */
    public AccountData getHandler() {
        return handler;
    }

    /**
     * Sets the value of the handler property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountData }
     *     
     */
    public void setHandler(AccountData value) {
        this.handler = value;
    }

    /**
     * Gets the value of the eta property.
     * 
     * @return
     *     possible object is
     *     {@link ObjectRef }
     *     
     */
    public ObjectRef getEta() {
        return eta;
    }

    /**
     * Sets the value of the eta property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectRef }
     *     
     */
    public void setEta(ObjectRef value) {
        this.eta = value;
    }

    /**
     * Gets the value of the resolution property.
     * 
     * @return
     *     possible object is
     *     {@link ObjectRef }
     *     
     */
    public ObjectRef getResolution() {
        return resolution;
    }

    /**
     * Sets the value of the resolution property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectRef }
     *     
     */
    public void setResolution(ObjectRef value) {
        this.resolution = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the attachments property.
     * 
     * @return
     *     possible object is
     *     {@link AttachmentDataArray }
     *     
     */
    public AttachmentDataArray getAttachments() {
        return attachments;
    }

    /**
     * Sets the value of the attachments property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttachmentDataArray }
     *     
     */
    public void setAttachments(AttachmentDataArray value) {
        this.attachments = value;
    }

    /**
     * Gets the value of the notes property.
     * 
     * @return
     *     possible object is
     *     {@link IssueNoteDataArray }
     *     
     */
    public IssueNoteDataArray getNotes() {
        return notes;
    }

    /**
     * Sets the value of the notes property.
     * 
     * @param value
     *     allowed object is
     *     {@link IssueNoteDataArray }
     *     
     */
    public void setNotes(IssueNoteDataArray value) {
        this.notes = value;
    }

    /**
     * Gets the value of the customFields property.
     * 
     * @return
     *     possible object is
     *     {@link CustomFieldValueForIssueDataArray }
     *     
     */
    public CustomFieldValueForIssueDataArray getCustomFields() {
        return customFields;
    }

    /**
     * Sets the value of the customFields property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomFieldValueForIssueDataArray }
     *     
     */
    public void setCustomFields(CustomFieldValueForIssueDataArray value) {
        this.customFields = value;
    }

}
