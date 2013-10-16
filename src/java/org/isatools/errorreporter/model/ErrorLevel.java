package org.isatools.errorreporter.model;

/**
 * Created by the ISA team
 *
 * @author Eamonn Maguire (eamonnmag@gmail.com)
 *         <p/>
 *         Date: 18/08/2011
 *         Time: 10:40
 */
public enum ErrorLevel {
    WARNING("warning"), ERROR("error");
    private String type;

    ErrorLevel(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
