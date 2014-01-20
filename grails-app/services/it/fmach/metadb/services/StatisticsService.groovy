package it.fmach.metadb.services

import it.fmach.metadb.isatab.model.FEMAssay;
import it.fmach.metadb.isatab.model.FEMGroup;
import it.fmach.metadb.isatab.model.FEMProject;

class StatisticsService {

    def groupRuns() {
		// load all groups
		def groups = FEMGroup.list()
		
		// build a list
		def stat = []
		groups.each{ group ->
			def nrRuns = 0
			
			// count number of runs
			def assays = FEMAssay.findByGroup(group)
			assays.each{ assay ->
				nrRuns += assay.randomizedRuns.size()
			}
			
			stat << [group.name, nrRuns]
		}
		return stat
    }
	
	
	def groupAssays() {
		// load all groups
		def groups = FEMGroup.list()
		
		// build a list
		def stat = []
		groups.each{ group ->
			def nrRuns = 0
			
			// count number of runs
			def nrAssays = FEMAssay.countByGroup(group)
			
			stat << [group.name, nrAssays]
		}
		return stat
	}
	
	def projectRuns() {
		// load all projects
		def projects = FEMProject.list()
		
		// build a list
		def stat = []
		projects.each{ project ->
			def nrRuns = 0
			
			// count number of runs
			def assays = FEMAssay.findByProject(project)
			assays.each{ assay ->
				nrRuns += assay.randomizedRuns.size()
			}
			
			stat << [project.name, nrRuns]
		}
		return stat
	}
	
	
	def projectAssays() {
		// load all projects
		def projects = FEMProject.list()
		
		// build a list
		def stat = []
		projects.each{ project ->
			def nrRuns = 0
			
			// count number of runs
			def nrAssays = FEMAssay.countByProject(project)
			
			stat << [project.name, nrAssays]
		}
		return stat
	}
	
	
}
