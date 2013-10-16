package org.isatools.errorreporter.model;

/**
 * Created by the ISA team
 *
 * @author Eamonn Maguire (eamonnmag@gmail.com)
 *         <p/>
 *         Date: 18/03/2011
 *         Time: 15:15
 */
public enum FileType {

    MICROARRAY("microarray"), NMR("nmr"), MASS_SPECTROMETRY("spectrometry"), SEQUENCING("sequencing"),
    GEL_ELECTROPHORESIS("electrophoresis"), FLOW_CYTOMETRY("flow"), HISTOLOGY("histology"),
    HEMATOLOGY("hematology"), CLINICAL_CHEMISTRY("chemistry"), INVESTIGATION("investigation"), STUDY_SAMPLE("sample");

    private String type;

    FileType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static FileType extractRelevantType(String type) {
        for (FileType assayType : values()) {
            if (type.toLowerCase().contains(assayType.getType())) {
                return assayType;
            }
        }

        return null;
    }
}
