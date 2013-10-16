package org.isatools.errorreporter.model;

import java.util.List;

public class ISAFileErrorReport {

    private String fileName;

    private String technologyType;
    private String measurementType;
    private FileType fileType;
    private List<ErrorMessage> messages;

    public ISAFileErrorReport(String fileName, FileType fileType, List<ErrorMessage> messages) {
        this(fileName, "", "", fileType, messages);
    }

    public ISAFileErrorReport(String fileName, String technologyType, String measurementType,
                              FileType fileType, List<ErrorMessage> messages) {
        this.fileName = fileName;
        this.technologyType = technologyType;
        this.measurementType = measurementType;
        this.fileType = fileType;
        this.messages = messages;
    }

    public String getFileName() {
        return fileName;
    }

    public FileType getFileType() {
        return fileType;
    }

    public List<ErrorMessage> getMessages() {
        return messages;
    }

    public int getMessageCountAtLevel(ErrorLevel level) {
        int count = 0;
        for (ErrorMessage message : messages) {
            if (message.getErrorLevel() == level) {
                count++;
            }
        }
        return count;
    }

    public String getAssayDescription() {
        if (measurementType.equals("")) {
            return "";
        } else {
            StringBuilder assayDescription = new StringBuilder(measurementType);
            if (!technologyType.equals("")) {
                assayDescription.append(" using ").append(technologyType);
            }

            return assayDescription.toString();
        }
    }


    public String getProblemSummary() {
        if (messages == null || messages.size() == 0) {
            return "0 problems found";
        } else {
            return messages.size() + " problem" + (messages.size() == 1 ? "" : "s") + " found";
        }
    }
}
