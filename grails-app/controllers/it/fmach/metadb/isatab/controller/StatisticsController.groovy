package it.fmach.metadb.isatab.controller

class StatisticsController {

	def statisticsService
	
    def group() {
		// per run
		session.groupStatRuns = statisticsService.groupRuns()
		session.groupColumnRuns = [['string', 'Group'], ['number', 'Runs']]
		
		// per assays
		session.groupStatAssays = statisticsService.groupAssays()
		session.groupColumnAssays = [['string', 'Group'], ['number', 'Assays']]
	}
	
	def project() {
		// per run
		session.projectStatRuns = statisticsService.projectRuns()
		session.projectColumnRuns = [['string', 'Project'], ['number', 'Runs']]
		
		// per assays
		session.projectStatAssays = statisticsService.projectAssays()
		session.projectColumnAssays = [['string', 'Project'], ['number', 'Assays']]
	}
}
