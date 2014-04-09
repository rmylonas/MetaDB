library("getopt")
library("PCA")

#get options, using the spec as defined by the enclosed list.
#we read the options from the default: commandArgs(TRUE).
spec = matrix(c(
	'help' , 'h', 0, "logical",
	'sqrtScaling' , 's', 0, "locigal",
	'sumNorm' , 'n' , 0 , "locigal", 
	'workdir', 'w', 1, "character",
	'factor', 'f', 1, "character"
), byrow=TRUE, ncol=4);

opt = getopt(spec);

# if help was asked for print a friendly message
# and exit with a non-zero error code
if ( !is.null(opt$help) | is.null(opt$workdir)) {
	writeLines("Usage: test.R [options]");                                                    
	writeLines("\t--help,h\tPrint usage options");
	writeLines("\t--workdir,w\tpath to result.RData");
	writeLines("\t--factor,f\tby which factor you want to color the PCA");                                                               
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

# load factors
factors <- read.csv(paste0(opt$workdir, "/factors.csv"), header=TRUE)

# select the factor of interest
eval(parse(text = paste0("myfactor <- factors$", opt$factor ) ))

# replace emtpy by NA
myfactor[which(myfactor == '')] <- NA
myfactor <- factor(myfactor)

# look for QC, STDmix and blank
mytype <- rep(NA, times=nrow(DM))
temp.type <- grepl("QC|STDMIX|BLANK", toupper(factors$Sample.name), perl = TRUE)
for(i in 1:nrow(DM)){
	if(temp.type[i]) { mytype[i] <- as.character(factors$Sample.name[i]) }
}
mytype <- factor(mytype)
mytype.levels <- levels(mytype)
mytype.length <- length(mytype.levels)

# prepare the colors
mycol <- rep(1,times=nrow(DM))
mylevels <- levels(myfactor)

# prepare the labels
mylabels <- c(mytype.levels, mylevels)
mylabels.colors <- seq(1, length(mylabels))

# set colors
for(i in 1:nrow(DM)){
	if(is.na(mytype[i])) {
		# if it's a factor
		eval(parse(text = paste0("temp.factor <- factors[i,]$", opt$factor ) ))
		mycol[i] <- which(mylevels == (temp.factor)) + mytype.length
	}else{
		# if its a QC, STDmix or blank
		mycol[i] <- which(mytype.levels == mytype[i])
	}
}

# normalization and scaling
if(! is.null(opt$sumNorm)){ DM <- DM/rowSums(DM) }

if(! is.null(opt$sqrtScaling)){
	mypca <- PCA(scale(sqrt(DM), scale=FALSE))
}else{
	mypca <- PCA(scale(DM))
}

# plot the PCA
png(paste0(opt$workdir, "/1_PCA.png"), width=600, height=600)
scoreplot(mypca,  col=mycol)
legend("bottomright", mylabels, pch=rep(1, length(mylabels.colors)), col=mylabels.colors)
dev.off()

# plot the PCA
png(paste0(opt$workdir, "/2_PCA_pairplot.png"), width = 900, height = 900)
pairs(scores(mypca)[,1:4], col = mycol)
dev.off()


# plot changes in rowSums
png(paste0(opt$workdir, "/3_rowSums.png"), width=600, height=600)
plot(rowSums(DM), col=mycol, type="b")
dev.off()


png(paste0(opt$workdir, "/4_PCA_names.png"), width=600, height=600)
scoreplot(mypca,  col=mycol, show.names=TRUE)
dev.off()


png(paste0(opt$workdir, "/5_PCA_biplot.png"), width=600, height=600)
biplot(mypca, show.names="loadings")
dev.off()




