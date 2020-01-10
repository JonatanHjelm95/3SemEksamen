/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jonab
 */
@Entity
@Table(name = "tag_names")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TagNames.findAll", query = "SELECT t FROM TagNames t"),
    @NamedQuery(name = "TagNames.findByTagId", query = "SELECT t FROM TagNames t WHERE t.tagId = :tagId"),
    @NamedQuery(name = "TagNames.findByTagName", query = "SELECT t FROM TagNames t WHERE t.tagName = :tagName")})
public class TagNames implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tag_id")
    private Integer tagId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "tag_name")
    private String tagName;
//    @ManyToMany(mappedBy = "tagNamesCollection")
//    private Collection<Products> productsCollection;

    public TagNames() {
    }

    public TagNames(Integer tagId) {
        this.tagId = tagId;
    }

    public TagNames(Integer tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

//    @XmlTransient
//    public Collection<Products> getProductsCollection() {
//        return productsCollection;
//    }
//
//    public void setProductsCollection(Collection<Products> productsCollection) {
//        this.productsCollection = productsCollection;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tagId != null ? tagId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TagNames)) {
            return false;
        }
        TagNames other = (TagNames) object;
        if ((this.tagId == null && other.tagId != null) || (this.tagId != null && !this.tagId.equals(other.tagId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.TagNames[ tagId=" + tagId + " ]";
    }
    
}
