package org.isatools.isacreator.gui.reference;

import org.apache.commons.collections15.OrderedMap;
import org.apache.commons.collections15.map.ListOrderedMap;
import org.apache.commons.collections15.set.ListOrderedSet;
import org.isatools.isacreator.configuration.DataTypes;
import org.isatools.isacreator.configuration.FieldObject;
import org.isatools.isacreator.io.importisa.investigationproperties.InvestigationFileSection;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by the ISA team
 *
 * @author Eamonn Maguire (eamonnmag@gmail.com)
 *         <p/>
 *         Date: 10/03/2011
 *         Time: 10:44
 */
public class DataEntryReferenceObject {

    private OrderedMap<InvestigationFileSection, Set<String>> sectionDefinition;
    private OrderedMap<String, FieldObject> fieldDefinition;

    private Set<String> fieldsToIgnore;


    public DataEntryReferenceObject() {
        this(new ListOrderedMap<InvestigationFileSection, Set<String>>());
    }

    public DataEntryReferenceObject(OrderedMap<InvestigationFileSection, Set<String>> sectionDefinition) {
        this(sectionDefinition, new ListOrderedMap<String, FieldObject>());
    }

    public DataEntryReferenceObject(OrderedMap<InvestigationFileSection, Set<String>> sectionDefinition, OrderedMap<String, FieldObject> fieldDefinition) {
        this.sectionDefinition = sectionDefinition;
        this.fieldDefinition = fieldDefinition;
    }

    public void setSectionDefinition(OrderedMap<InvestigationFileSection, Set<String>> sectionDefinition) {
        this.sectionDefinition = sectionDefinition;
    }

    public void setFieldDefinition(OrderedMap<String, FieldObject> fieldDefinition) {
        this.fieldDefinition = fieldDefinition;
    }

    public void setFieldDefinition(List<FieldObject> fieldDefinition) {

        if (this.fieldDefinition == null) {
            this.fieldDefinition = new ListOrderedMap<String, FieldObject>();
        }

        for (FieldObject field : fieldDefinition) {
            this.fieldDefinition.put(field.getFieldName(), field);
        }

        createSectionDefinitionsFromField();

    }

    private void createSectionDefinitionsFromField() {
        sectionDefinition = new ListOrderedMap<InvestigationFileSection, Set<String>>();

        for (String fieldName : fieldDefinition.keySet()) {
            FieldObject fieldDef = fieldDefinition.get(fieldName);

            InvestigationFileSection section = InvestigationFileSection.convertToInstance(fieldDef.getSection());

            if (!sectionDefinition.containsKey(section)) {
                sectionDefinition.put(section, new ListOrderedSet<String>());
            }

            sectionDefinition.get(section).add(fieldName);
        }
    }

    public OrderedMap<InvestigationFileSection, Set<String>> getSectionDefinition() {
        if (sectionDefinition == null) {
            createSectionDefinitionsFromField();
        }
        return sectionDefinition;
    }

    public Map<String, FieldObject> getFieldDefinition() {
        return fieldDefinition;
    }

    public Set<String> getFieldsForSection(InvestigationFileSection section) {
        return getSectionDefinition().get(section);
    }

    public FieldObject getFieldDefinition(String fieldName) {
        if (fieldDefinition.containsKey(fieldName)) {
            return fieldDefinition.get(fieldName);
        } else {
            // return a default field definition object for Text
            return new FieldObject(fieldName, "No further information available", DataTypes.STRING, "", false, false, false);
        }
    }


    /**
     * Ontology terms are detected when there is a presence of 3 values in the field set with the same base name and
     * the words "Term Accession Number" & "Term Source Ref" are found.
     *
     * @return
     */
    public Set<String> getOntologyTerms(InvestigationFileSection section) {
        Set<String> ontologyFields = new HashSet<String>();


        if (sectionDefinition != null) {
            if (sectionDefinition.get(section) != null) {

                fieldsToIgnore = filterFields(sectionDefinition.get(section), "term accession", "term source");

                for (String ontologyTerm : fieldsToIgnore) {

                    String toAdd = ontologyTerm.substring(0, ontologyTerm.toLowerCase().indexOf("term")).trim();

                    // if the field edited was a comment, it will include an unclosed square bracket after running the
                    // previous function. So, we should close it.
                    if (toAdd.contains("[")) {
                        toAdd += "]";
                    }
                    ontologyFields.add(toAdd);
                }
            }
        }

        return ontologyFields;
    }

    public Set<String> getFieldsToIgnore() {
        return fieldsToIgnore == null ? new HashSet<String>() : fieldsToIgnore;
    }

    public Set<String> filterFields(Set<String> toFilter, String... filters) {
        Set<String> result = new HashSet<String>();
        for (String value : toFilter) {
            for (String filter : filters) {
                if (value.toLowerCase().contains(filter)) {
                    result.add(value);
                }
            }
        }

        return result;
    }
}
