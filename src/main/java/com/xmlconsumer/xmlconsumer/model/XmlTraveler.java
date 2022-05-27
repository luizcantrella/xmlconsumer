package com.xmlconsumer.xmlconsumer.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class XmlTraveler {

  @Id
  private Long id;
  private byte []xml;

  public XmlTraveler() {
  }

  public XmlTraveler(Long id, byte[] xml) {
    this.id = id;
    this.xml = xml;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public byte[] getXml() {
    return xml;
  }

  public void setXml(byte[] xml) {
    this.xml = xml;
  }
}
