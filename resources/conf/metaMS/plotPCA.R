library("getopt")
library("PCA")

#get options, using the spec as defined by the enclosed list.
#we read the options from the default: commandArgs(TRUE).
spec = matrix(c(
	'help' , 'h', 0, "logical",
	'workdir', 'w', 1, "character"
), byrow=TRUE, ncol=4);

opt = getopt(spec);

# if help was asked for print a friendly message
# and exit with a non-zero error code
if ( !is.null(opt$help) | is.null(opt$workdir)) {
	writeLines("Usage: test.R [options]");                                                    
	writeLines("\t--help,h\tPrint usage options");
	writeLines("\t--workdir,w\tpath to result.RData");                                                               
  q(status=1);
}

# load results
load(paste0(opt$workdir, "/result.RData"))

# put results in correct format
nr.samples <- dim(out$PeakTable)[2]
DM <- t(out$PeakTable[,6:nr.samples])
rt <- out$PeakTable[,"rt"]
mz <- out$PeakTable[,"mz"]
pcgroup <- out$PeakTable[,"pcgroup"]
adduct <- out$PeakTable[,"adduct"]
isotopes <- out$PeakTable[,"isotopes"]

# plot the PCA
png(paste0(opt$workdir, "/PCA.png"))
mypca <- PCA(scale(DM, scale=FALSE))
scoreplot(mypca)
dev.off()