library(getopt)
library(metaMS)
data(FEMsettings)

#get options, using the spec as defined by the enclosed list.
#we read the options from the default: commandArgs(TRUE).
spec = matrix(c(
	'help' , 'h', 0, "logical",
	'instrument' , 'i', 1, "character",
	'polarity' , 'p', 2, "character",
	'minRt' , 'm', 2, "double",
	'maxRt' , 'x', 2, "double",
	'fileList', 'f', 1, "character",
	'setting', 's', 1, "character",
	'output', 'o', 1, "character",
	'database', 'd', 1, "character"
), byrow=TRUE, ncol=4);

opt = getopt(spec);

# if help was asked for print a friendly message
# and exit with a non-zero error code
if ( !is.null(opt$help) | is.null(opt$instrument) | is.null(opt$fileList) | is.null(opt$setting) | is.null(opt$output)) {
	writeLines("Usage: test.R [options]");                                                    
	writeLines("\t--help,h\tPrint usage options");
	writeLines("\t--fileList,f\tpath to file containing list of CDF files");                                                               
	writeLines("\t--instrument,i\tinstrument type (GC or LC)");
	writeLines("\t--setting,x\tpath to RData file with instrument settings");
	writeLines("\t--output,o\tpath to write output files");
	writeLines("\t--polarity,p\tpolarity (positive or negative)");
	writeLines("\t--database,d\tpath to database in RData");
	writeLines("\t--minRt,m\tmin retention time (minutes)");
	writeLines("\t--maxRt,x\tmax retention time (minutes)");
  q(status=1);
}

# load files
files <- read.csv(opt$fileList, header=FALSE)
files <- as.vector(files[,1])

# load settings
instr.setting <- switch(opt$setting,
               Synapt.NP = Synapt.NP,
               Synapt.RP = Synapt.RP,
               TSQXLS.GC = TSQXLS.GC)

# hardcode snthresh=4
# instr.setting@PeakPicking$snthresh <- 6

# load database
database = NULL
if(! is.null(opt$database)){
	loaded.obj <- load(opt$database)
	eval(parse(text = paste0("database <- ", loaded.obj[1]) ) )
}

#set some reasonable defaults for the options that are needed,
if ( is.null(opt$polarity ) ) { opt$polarity = "positive" }

# set retention times slices
rt.range <- NULL
if ( !is.null(opt$minRt) & !is.null(opt$maxRt) ) { rt.range <- c(opt$minRt, opt$maxRt) }

# run metaMS
if( opt$instrument == "GC" ){
	# run GC
	out <- runGC(files, settings = instr.setting, rtrange = rt.range, DB = database)
}else if(opt$instrument == "LC"){
	# run LC
	out <- runLC(files, settings = instr.setting, polarity = opt$polarity, rtrange = rt.range, DB = database)
}else{
	writeLines("illegal instrument type: use GC or LC")
	q(status=1)
}

save(out, file=paste0(opt$output, "/result.RData"))

# write a csv file of the results
write.csv(out$PeakTable, file=paste0(opt$output, "/PeakTable.csv"))
